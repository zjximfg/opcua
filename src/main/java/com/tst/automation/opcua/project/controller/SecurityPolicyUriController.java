package com.tst.automation.opcua.project.controller;

import com.tst.automation.opcua.core.pojo.SecurityPolicyUri;
import com.tst.automation.opcua.core.service.SecurityPolicyUriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("project/securityPolicyUri")
public class SecurityPolicyUriController {

    @Autowired
    private SecurityPolicyUriService securityPolicyUriService;

    @GetMapping("list")
    public ResponseEntity<List<SecurityPolicyUri>> getAllSecurityPolicyUriList() {
        return ResponseEntity.ok(securityPolicyUriService.getAllSecurityPolicyUri());
    }
}
