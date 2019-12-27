package com.tst.automation.opcua.core.service;

import com.tst.automation.opcua.core.pojo.SecurityPolicyUri;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SecurityPolicyUriService {
    List<SecurityPolicyUri> getAllSecurityPolicyUri();
}
