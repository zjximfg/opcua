package com.tst.automation.opcua.project.pojo;

import com.tst.automation.opcua.core.pojo.OpcUaProtocol;
import lombok.Data;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Data
@Table(name = "tbl_opc_ua_server")
public class OpcUaServer {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String endpointUrl;     // 不需要前端修改，后端自动生成

    private String serverName;      // 不需要前端修改，后端自动生成

    private String fullName;        // 不需要前端修改，后端自动生成

    // 协议的类型
    private Long opcUaProtocolId;
    // 加密协议
    private String securityPolicyUri;

    // 以下数据需要连接服务器获得

    private String securityMode;    // 可以为空
    private String supportedFile;   // 可以为空
    private String authenticationTypes; // 可以为空
    // 显示服务器的
    private String productUri;      // 可以为空
    private String applicationUri;  // 可以为空
    private String applicationName; // 可以为空
    private Boolean isActive;
    private Boolean isDeleted;

    @Transient
    private OpcUaProtocol opcUaProtocol;
    @Transient
    private OpcUaClient opcUaClient;
    @Transient
    private EndpointDescription endpointDescription;
    @Transient
    private List<OpcUaConnection> opcUaConnectionList;



    /**
     * 创建server时，生成endpoint URL
     *
     * @param baseUrl           从配置中读取的 baseUrl
     * @param computerName      电脑名称，或者远程服务器IP
     * @param opcUaProtocolPort 协议对应的端口， 如 S7 对应的 55101
     */
    public void createEndpointUrl(String baseUrl, String computerName, Integer opcUaProtocolPort) {
        this.endpointUrl = baseUrl + computerName + ":" + opcUaProtocolPort.toString();
    }

    public void createServerFullName() {
        this.setFullName(this.getEndpointUrl() + "[" + this.getServerName() + "]" +  this.getSecurityPolicyUri().replaceFirst("http://opcfoundation.org/UA/SecurityPolicy", ""));
    }
}
