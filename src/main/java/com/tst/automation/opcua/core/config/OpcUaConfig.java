package com.tst.automation.opcua.core.config;

import lombok.Data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.KeyPair;
import java.security.cert.X509Certificate;

/**
 * opc UA 的配置实体类，读取application.yaml中的配置信息。转换成实体
 */
@Component
@Data
public class OpcUaConfig {

    @Value("${opc.ua.endpoint.base_URL}")
    private String endpointBaseUrl;

    @Value("${opc.ua.endpoint.computerName}")
    private String computerName;

    @Value("${opc.ua.endpoint.username}")
    private String username;

    @Value("${opc.ua.endpoint.password}")
    private String password;

    @Autowired
    private OpcUaClientConfig opcUaClientConfig;

    @Autowired
    private OpcUaKeyStoreConfig opcUaKeyStoreConfig;

    private X509Certificate certificate;

    private KeyPair keyPair;
}
