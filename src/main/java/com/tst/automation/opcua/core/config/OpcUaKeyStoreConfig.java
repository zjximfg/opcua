package com.tst.automation.opcua.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "opc.ua.key-store")
public class OpcUaKeyStoreConfig {

    private String commonName;

    private String organization;

    private String organizationalUnit;

    private String localityName;

    private String stateName;

    private String countryCode;

    private String dnsName;

    private String ipAddress;

    private String clientAlias;

    private String password;
}
