package com.tst.automation.opcua.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "opc.ua.client")
public class OpcUaClientConfig {

    private String applicationName;

    private String applicationUri;

    private String certificatePath;

    private String keyPairPath;
}
