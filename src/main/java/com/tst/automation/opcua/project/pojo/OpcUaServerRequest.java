package com.tst.automation.opcua.project.pojo;

import lombok.Data;

@Data
public class OpcUaServerRequest {

    private Long id;
    // 协议的类型
    private Long opcUaProtocolId;
    // 加密协议
    private String securityPolicyUri;

}
