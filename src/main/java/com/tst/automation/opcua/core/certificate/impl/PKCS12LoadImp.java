package com.tst.automation.opcua.core.certificate.impl;

import com.tst.automation.opcua.core.certificate.CertificateLoader;
import com.tst.automation.opcua.core.config.OpcUaConfig;
import org.eclipse.milo.opcua.stack.core.util.SelfSignedCertificateBuilder;
import org.eclipse.milo.opcua.stack.core.util.SelfSignedCertificateGenerator;
import org.springframework.stereotype.Component;

import java.io.*;
import java.security.*;
import java.security.cert.X509Certificate;

@Component
public class PKCS12LoadImp implements CertificateLoader {

    private X509Certificate certificate;

    private KeyPair keyPair;

    private void load(File certificateFile, OpcUaConfig opcUaConfig) {
        System.out.println(opcUaConfig);
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");

            if (!certificateFile.exists()) {

                KeyPair keyPair = SelfSignedCertificateGenerator.generateRsaKeyPair(2048);

                // 如果安全证书不存在则生成安全文件
                this.createCertificate(keyStore, keyPair, opcUaConfig);

                // 配置keyStore
                keyStore.setKeyEntry(opcUaConfig.getOpcUaKeyStoreConfig().getClientAlias(), keyPair.getPrivate(), opcUaConfig.getOpcUaKeyStoreConfig().getPassword().toCharArray(), new X509Certificate[]{certificate});
                // 创建证书文件并加载证书文件
                try (OutputStream out = new FileOutputStream(certificateFile)) {
                    keyStore.store(out, opcUaConfig.getOpcUaKeyStoreConfig().getPassword().toCharArray());
                }
            } else {
                try (InputStream in = new FileInputStream(certificateFile)) {
                    keyStore.load(in, opcUaConfig.getOpcUaKeyStoreConfig().getPassword().toCharArray());
                }
            }

            Key serverPrivateKey = keyStore.getKey(opcUaConfig.getOpcUaKeyStoreConfig().getClientAlias(), opcUaConfig.getOpcUaKeyStoreConfig().getPassword().toCharArray());
            if (serverPrivateKey instanceof PrivateKey) {
                certificate = (X509Certificate) keyStore.getCertificate(opcUaConfig.getOpcUaKeyStoreConfig().getClientAlias());
                PublicKey serverPublicKey = certificate.getPublicKey();
                keyPair = new KeyPair(serverPublicKey, (PrivateKey) serverPrivateKey);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createCertificate(KeyStore keyStore, KeyPair keyPair, OpcUaConfig opcUaConfig) throws Exception {
        keyStore.load(null, opcUaConfig.getOpcUaKeyStoreConfig().getPassword().toCharArray());


        SelfSignedCertificateBuilder builder = new SelfSignedCertificateBuilder(keyPair)
                .setCommonName(opcUaConfig.getOpcUaKeyStoreConfig().getCommonName())
                .setOrganization(opcUaConfig.getOpcUaKeyStoreConfig().getOrganization())
                .setOrganizationalUnit(opcUaConfig.getOpcUaKeyStoreConfig().getOrganizationalUnit())
                .setLocalityName(opcUaConfig.getOpcUaKeyStoreConfig().getLocalityName())
                .setStateName(opcUaConfig.getOpcUaKeyStoreConfig().getStateName())
                .setCountryCode(opcUaConfig.getOpcUaKeyStoreConfig().getCountryCode())
                .setApplicationUri(opcUaConfig.getOpcUaClientConfig().getApplicationUri())
                .addDnsName(opcUaConfig.getOpcUaKeyStoreConfig().getDnsName())
                .addIpAddress(opcUaConfig.getOpcUaKeyStoreConfig().getIpAddress());

        certificate = builder.build();
    }

    @Override
    public X509Certificate getCertificate(File certificateFile, OpcUaConfig opcUaConfig) {
        if (certificate == null) {
            load(certificateFile, opcUaConfig);
        }
        return certificate;
    }


    // 不使用
    @Override
    public KeyPair getKeyPair(File keyPairFile, OpcUaConfig opcUaConfig) {
        if(keyPair !=null)
            load(keyPairFile, opcUaConfig);
        return keyPair;
    }
}
