package com.tst.automation.opcua.core.service;

import com.tst.automation.opcua.core.certificate.CertificateLoader;
import com.tst.automation.opcua.core.config.OpcUaConfig;
import com.tst.automation.opcua.project.pojo.OpcUaServer;
import com.tst.automation.opcua.project.service.OpcUaServerService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfig;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfigBuilder;
import org.eclipse.milo.opcua.sdk.client.api.identity.UsernameProvider;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaMonitoredItem;
import org.eclipse.milo.opcua.sdk.client.api.subscriptions.UaSubscription;
import org.eclipse.milo.opcua.stack.client.UaTcpStackClient;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.security.SecurityPolicy;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.LocalizedText;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MonitoringMode;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.eclipse.milo.opcua.sdk.client.api.nodes.Node;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoringParameters;
import org.eclipse.milo.opcua.stack.core.types.structured.ReadValueId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.security.KeyPair;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import static org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

@Service
@Data
@Slf4j
public class OpcUaTask implements Runnable {

    @Autowired
    private OpcUaConfig opcUaConfig;
    @Autowired
    private OpcUaProtocolService opcUaProtocolService;
    @Autowired
    private OpcUaServerService opcUaServerService;
    @Autowired
    private CertificateLoader certificateLoader;

    // 激活的服务器列表
    private List<OpcUaServer> activeOpcUaServerList;


    /**
     * 在新的线程中运行opc核心任务
     */
    @Override
    public void run() {
        try {
            // 初始化opc 任务
            this.initialization();
            for (OpcUaServer opcUaServer : activeOpcUaServerList) {
                this.browseNode(opcUaServer.getOpcUaClient(), 0,"0", "Numeric");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void initialization() throws Exception {
        // 1. 查找数据库中所有的已经存在并激活的服务器列表
        //this.activeOpcUaServerList = opcUaServerService.getAllActiveOpcUaServer();
        // TEST
        // System.out.println(opcUaServerService.getAllSecurityPolicyUriList());
        this.activeOpcUaServerList = this.testServer();
        // 2. 建立安全证书
        // 2.1 获取文件
        this.createCertificate();
        // 3. 通过opcUaServer获得终端
        for (OpcUaServer opcUaServer : this.activeOpcUaServerList) {
            opcUaServer.setEndpointDescription(this.getActiveEndpointDescriptionByOpcUaServer(opcUaServer));
            // log日志
            log.info(opcUaServer.getEndpointDescription().toString());
            // 3. 获取对应的opcUaClient
            opcUaServer.setOpcUaClient(this.getOpcUaClientByOpcUaServer(opcUaServer));
        }
        log.info("开始连接OPC server...");
    }


    public void createCertificate() {
        // 2.1 获取文件
        File file = new File(opcUaConfig.getOpcUaClientConfig().getCertificatePath());
        // 2.2 生成并读取证书
        X509Certificate certificate = certificateLoader.getCertificate(file, opcUaConfig);
        KeyPair keyPair = certificateLoader.getKeyPair(file, opcUaConfig);
        opcUaConfig.setCertificate(certificate);
        opcUaConfig.setKeyPair(keyPair);
    }

    public List<Node> browseNode(OpcUaClient client, Integer namespaceIndex, String identifier, String nodeIdType) throws ExecutionException, InterruptedException {
        //开启连接

        log.info("开始连接OPC server...");
        client.connect().get();
        log.info("OPC server 连接完成...");

        List<Node> nodes = new ArrayList<>();
        if (identifier.equals("0")) {
            log.info("开始浏览Node...");
            nodes = client.getAddressSpace().browse(Identifiers.RootFolder).get();
        } else {
            if (nodeIdType.equals("Numeric")) {
                nodes = client.getAddressSpace().browse(new NodeId(Unsigned.ushort(0), uint(identifier))).get();
            }
            if (nodeIdType.equals("String")) {
                System.out.println(namespaceIndex);
                nodes = client.getAddressSpace().browse(new NodeId(namespaceIndex, identifier)).get();
            }
        }
        return nodes;

    }

    public OpcUaClient getOpcUaClientByOpcUaServer(OpcUaServer opcUaServer) {
        // 创建配置类
        OpcUaClientConfigBuilder builder = OpcUaClientConfig.builder();
        builder.setApplicationName(LocalizedText.english(opcUaConfig.getOpcUaClientConfig().getApplicationName()));
        builder.setApplicationUri(opcUaConfig.getOpcUaClientConfig().getApplicationUri());
        builder.setCertificate(opcUaConfig.getCertificate());
        builder.setKeyPair(opcUaConfig.getKeyPair());
        builder.setEndpoint(opcUaServer.getEndpointDescription());
        builder.setIdentityProvider(new UsernameProvider(opcUaConfig.getUsername(), opcUaConfig.getPassword()));
        OpcUaClientConfig opcUaClientConfig = builder.build();
        return new OpcUaClient(opcUaClientConfig);
    }

    /**
     * 通过 当前激活的 服务器获取对应的endpoint
     *
     * @param activeOpcUaServer 当前激活的服务器
     * @return 第一个满足条件的对应的endpoint (包括协议，包括加密模式，包括电脑ip)
     */
    public EndpointDescription getActiveEndpointDescriptionByOpcUaServer(OpcUaServer activeOpcUaServer) throws Exception {

        // 获取全部加密模式的endpoint
        EndpointDescription[] endpointDescriptions = this.getEndpointDescriptions(activeOpcUaServer.getEndpointUrl());
        // 过滤掉对应的加密模式
        Stream<EndpointDescription> stream = Arrays.stream(endpointDescriptions);
        return stream.filter(endpointDescription -> {
            return endpointDescription.getSecurityPolicyUri().equals(activeOpcUaServer.getSecurityPolicyUri());
        }).findFirst().orElseThrow(() -> new Exception("该服务器" + activeOpcUaServer.getEndpointUrl() + "/" + activeOpcUaServer.getSecurityPolicyUri() + "没有找到对应的Endpoint"));
    }


    public EndpointDescription[] getEndpointDescriptions(String endpointUrl) throws ExecutionException, InterruptedException {
        // 通过 endpoint url 查找所有的终端endpoint
        return UaTcpStackClient.getEndpoints(endpointUrl).get();
    }

    /**
     * 通过 endpointBaseUrl，computerName，port
     *
     * @param endpointBaseUrl base URL [opc.tcp://]
     * @param computerName    opc server 的IP 或 电脑名称
     * @param port            端口号，与协议绑定，可以从数据库中查的
     * @return 终端的 数组
     * @throws ExecutionException   执行异常
     * @throws InterruptedException 中断异常
     */
    public EndpointDescription[] getEndpointDescriptions(String endpointBaseUrl, String computerName, Integer port) throws ExecutionException, InterruptedException {
        // 创建endpointUrl
        OpcUaServer opcUaServer = new OpcUaServer();
        opcUaServer.createEndpointUrl(endpointBaseUrl, computerName, port);
        // 通过 endpoint url 查找所有的终端endpoint
        return UaTcpStackClient.getEndpoints(opcUaServer.getEndpointUrl()).get();
    }


    /**
     * 读取 变量的值
     * @param client
     * @param nodeId
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public DataValue readValue(OpcUaClient client, NodeId nodeId) throws ExecutionException, InterruptedException {
        //        //创建连接
        client.connect().get();

        DataValue value = client.readValue(0.0, TimestampsToReturn.Both, nodeId).get();

//        authenticationTypes:
//        Auth
        System.out.println(value.getValue().getValue());
        return value;
    }

//    public DataValue subscribeValue(OpcUaClient opcUaClient, NodeId nodeId) throws ExecutionException, InterruptedException {
//        opcUaClient.connect().get();
//
//    }

    /**
     * onChange
     * @param client
     * @param nodeId
     * @param dataValue
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void createSubscription(OpcUaClient client, NodeId nodeId, DataValue dataValue) throws ExecutionException, InterruptedException {

        //创建连接
        client.connect().get();

        //创建发布间隔1000ms的订阅对象
        UaSubscription subscription = client.getSubscriptionManager().createSubscription(100.0).get();

        //创建订阅的变量
        ReadValueId readValueId = new ReadValueId(nodeId, AttributeId.Value.uid(),null,null);

        //创建监控的参数
        MonitoringParameters parameters = new MonitoringParameters(
                uint(1),
                1000.0,     // sampling interval
                null,       // filter, null means use default
                uint(10),   // queue size
                true        // discard oldest
        );

        //创建监控项请求
        //该请求最后用于创建订阅。
        MonitoredItemCreateRequest request = new MonitoredItemCreateRequest(readValueId, MonitoringMode.Reporting, parameters);

        List<MonitoredItemCreateRequest> requests = new ArrayList<>();
        requests.add(request);

        //创建监控项，并且注册变量值改变时候的回调函数。
        List<UaMonitoredItem> items = subscription.createMonitoredItems(
                TimestampsToReturn.Both,
                requests,
                (item,id)->{
                    item.setValueConsumer((a, value)->{
                        System.out.println("value :"+value.getValue().getValue());
                        //final dataValue = value;
                    });
                }
        ).get();
    }


    public List<OpcUaServer> testServer() {
        String endpointUrl = opcUaConfig.getEndpointBaseUrl() + "localhost:" + opcUaProtocolService.getOpcUaProtocolById(1L).getPort();
        OpcUaServer opcUaServer = new OpcUaServer();
        opcUaServer.setIsActive(true);
        opcUaServer.setEndpointUrl(endpointUrl);
        opcUaServer.setSecurityPolicyUri(SecurityPolicy.Basic128Rsa15.getSecurityPolicyUri());
        List<OpcUaServer> list = new ArrayList<>();
        list.add(opcUaServer);
        return list;
    }



}
