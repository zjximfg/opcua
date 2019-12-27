package com.tst.automation.opcua.core.certificate;

import com.tst.automation.opcua.core.config.OpcUaConfig;
import org.springframework.stereotype.Component;

import java.io.File;
import java.security.KeyPair;
import java.security.cert.X509Certificate;

@Component
public interface CertificateLoader {

    X509Certificate getCertificate(File certificateFile, OpcUaConfig opcUaConfig);

    KeyPair getKeyPair(File keyPairFile, OpcUaConfig opcUaConfig);
}
