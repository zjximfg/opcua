package com.tst.automation.opcua.project.service.impl;

import com.tst.automation.opcua.core.config.OpcUaConfig;
import com.tst.automation.opcua.core.pojo.OpcUaProtocol;
import com.tst.automation.opcua.core.service.OpcUaProtocolService;
import com.tst.automation.opcua.core.service.OpcUaTask;
import com.tst.automation.opcua.project.mapper.OpcUaServerMapper;
import com.tst.automation.opcua.project.pojo.OpcUaServer;
import com.tst.automation.opcua.project.service.OpcUaServerService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OpcUaServerServiceImpl implements OpcUaServerService {

    @Autowired
    private OpcUaServerMapper opcUaServerMapper;
    @Autowired
    private OpcUaProtocolService opcUaProtocolService;
    @Autowired
    private OpcUaTask opcUaTask;
    @Autowired
    private OpcUaConfig opcUaConfig;


    @Override
    public List<OpcUaServer> getAllActiveOpcUaServer() {
        // 1. 查询所有的已经激活的opc project server
        OpcUaServer opcUaServer = new OpcUaServer();
        opcUaServer.setIsActive(true);
        opcUaServer.setIsDeleted(false);
        List<OpcUaServer> list = opcUaServerMapper.select(opcUaServer);
        // 2. 根据protocol id 添加所有的protocol 属性（@Transient）
        for (OpcUaServer uaServer : list) {
            OpcUaProtocol opcUaProtocolById = opcUaProtocolService.getOpcUaProtocolById(uaServer.getOpcUaProtocolId());

            uaServer.setOpcUaProtocol(opcUaProtocolById);
        }
        return list;
    }

//    @Override
//    public List<String> getAllSecurityPolicyUriList() {
//        List<String> securityPolicyUri = new ArrayList<>();
//        SecurityPolicy[] values = SecurityPolicy.values();
//        for (SecurityPolicy value : values) {
//            securityPolicyUri.add(value.getSecurityPolicyUri());
//        }
//        return securityPolicyUri;
//    }

    @Override
    public List<OpcUaServer> browseOpcUaServerListByProtocolId(Long opcUaProtocolId) {

        // 获得端口
        OpcUaProtocol opcUaProtocolById = opcUaProtocolService.getOpcUaProtocolById(opcUaProtocolId);
        // 获得终端
        try {
            EndpointDescription[] endpointDescriptions = opcUaTask.getEndpointDescriptions(opcUaConfig.getEndpointBaseUrl(), opcUaConfig.getComputerName(), opcUaProtocolById.getPort());
            // 整理成 opcServer 对象
            List<OpcUaServer> opcUaServerList = new ArrayList<>();
            int index = 0;
            for (EndpointDescription endpointDescription : endpointDescriptions) {

                index++;
                OpcUaServer opcUaServer = getOpcUaServerByEndpointDescription(endpointDescription);
                opcUaServer.setId((long) index);
                opcUaServer.setOpcUaProtocolId(opcUaProtocolId);
                opcUaServer.setOpcUaProtocol(opcUaProtocolById);
                opcUaServerList.add(opcUaServer);
            }
            return opcUaServerList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public OpcUaServer connect(Long opcUaServerId, List<OpcUaServer> currentOpcUaServerList) {
        for (OpcUaServer opcUaServer : currentOpcUaServerList) {
            if (opcUaServer.getId().equals(opcUaServerId)) {
                try {
                    // 如果存在opcUaClient说明已经连接过了
                    if (opcUaServer.getOpcUaClient() != null) {
                        log.info("已经连接过该服务器了...");
                        return opcUaServer;
                    }
                    opcUaServer.setEndpointDescription(opcUaTask.getActiveEndpointDescriptionByOpcUaServer(opcUaServer));
                    opcUaServer.setOpcUaClient(opcUaTask.getOpcUaClientByOpcUaServer(opcUaServer));
                    return opcUaServer;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(opcUaServer);
            }
        }
        return null;
    }

    @Override
    public OpcUaServer getOpcUaServerActiveFirst() {
        // 1. 查询所有的已经激活的opc project server
        OpcUaServer opcUaServer = new OpcUaServer();
        opcUaServer.setIsActive(true);
        opcUaServer.setIsDeleted(false);
        OpcUaServer opcUaServerSelected = opcUaServerMapper.selectOne(opcUaServer);
        if (opcUaServerSelected == null) {
            return null;
        }
        // 2. 根据protocol id 添加所有的protocol 属性（@Transient）
        OpcUaProtocol opcUaProtocol = opcUaProtocolService.getOpcUaProtocolById(opcUaServerSelected.getOpcUaProtocolId());
        opcUaServerSelected.setOpcUaProtocol(opcUaProtocol);
        return opcUaServerSelected;
    }

    @Override
    public void updateOpcUaServer(OpcUaServer opcUaServer) {
        OpcUaProtocol opcUaProtocol = opcUaProtocolService.getOpcUaProtocolById(opcUaServer.getOpcUaProtocolId());
        opcUaServer.createEndpointUrl(opcUaConfig.getEndpointBaseUrl(), opcUaConfig.getComputerName(), opcUaProtocol.getPort());
        opcUaServer.setServerName(opcUaProtocol.getProtocolName());
        opcUaServer.createServerFullName();
        try {
            // 尝试连接服务器, 获得激活的endpointDescription
            OpcUaServer opcUaServer1 = this.getOpcUaServerOnlineData(opcUaServer);
            opcUaServerMapper.updateByPrimaryKeySelective(opcUaServer1);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        opcUaServerMapper.updateByPrimaryKeySelective(opcUaServer);
    }

    @Override
    public void insertOpcUaServer(OpcUaServer opcUaServer) {
        OpcUaProtocol opcUaProtocol = opcUaProtocolService.getOpcUaProtocolById(opcUaServer.getOpcUaProtocolId());
        opcUaServer.createEndpointUrl(opcUaConfig.getEndpointBaseUrl(), opcUaConfig.getComputerName(), opcUaProtocol.getPort());
        opcUaServer.setIsActive(true);
        opcUaServer.setIsDeleted(false);
        opcUaServer.setServerName(opcUaProtocol.getProtocolName());
        opcUaServer.createServerFullName();

        try {
            // 尝试连接服务器, 获得激活的endpointDescription
            OpcUaServer opcUaServer1 = this.getOpcUaServerOnlineData(opcUaServer);
            opcUaServerMapper.insert(opcUaServer1);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 插入到数据库opcUaServer
        opcUaServerMapper.insert(opcUaServer);

    }

    private OpcUaServer getOpcUaServerOnlineData(OpcUaServer opcUaServer) throws Exception {
        EndpointDescription activeEndpointDescriptionByOpcUaServer = opcUaTask.getActiveEndpointDescriptionByOpcUaServer(opcUaServer);
        OpcUaServer opcUaServer1 = this.getOpcUaServerByEndpointDescription(activeEndpointDescriptionByOpcUaServer);
        opcUaServer1.setId(opcUaServer.getId());
        opcUaServer1.setOpcUaProtocolId(opcUaServer.getOpcUaProtocolId());
        opcUaServer1.setOpcUaProtocol(opcUaServer.getOpcUaProtocol());
        opcUaServer1.setIsActive(true);
        opcUaServer1.setIsDeleted(false);
        System.out.println(opcUaServer1);
        return opcUaServer1;
    }

    private OpcUaServer getOpcUaServerByEndpointDescription(EndpointDescription endpointDescription) {
        OpcUaServer opcUaServer = new OpcUaServer();
        opcUaServer.setEndpointUrl(endpointDescription.getEndpointUrl());
        opcUaServer.setServerName(endpointDescription.getServer().getApplicationName().getText()); // 与application name相同
        opcUaServer.setSecurityPolicyUri(endpointDescription.getSecurityPolicyUri());
        opcUaServer.setSecurityMode(endpointDescription.getSecurityMode().toString());
        opcUaServer.setSupportedFile(endpointDescription.getTransportProfileUri());
        opcUaServer.setAuthenticationTypes(endpointDescription.getUserIdentityTokens()[0].getTokenType().toString());
        opcUaServer.setProductUri(endpointDescription.getServer().getProductUri()); // Application URI
        opcUaServer.setApplicationUri(endpointDescription.getServer().getApplicationUri());
        opcUaServer.setApplicationName(endpointDescription.getServer().getApplicationName().getText());
        // 生成full name 用于前端显示
        opcUaServer.createServerFullName();

        return opcUaServer;
    }
}
