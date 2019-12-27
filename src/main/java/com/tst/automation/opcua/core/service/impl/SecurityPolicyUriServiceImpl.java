package com.tst.automation.opcua.core.service.impl;

import com.tst.automation.opcua.core.mapper.SecurityPolicyUriMapper;
import com.tst.automation.opcua.core.pojo.SecurityPolicyUri;
import com.tst.automation.opcua.core.service.SecurityPolicyUriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityPolicyUriServiceImpl implements SecurityPolicyUriService {

    @Autowired
    private SecurityPolicyUriMapper securityPolicyUriMapper;

    @Override
    public List<SecurityPolicyUri> getAllSecurityPolicyUri() {
        return securityPolicyUriMapper.selectAll();
    }
}
