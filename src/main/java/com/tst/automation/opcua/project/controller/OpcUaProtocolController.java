package com.tst.automation.opcua.project.controller;

import com.tst.automation.opcua.core.pojo.OpcUaProtocol;
import com.tst.automation.opcua.core.service.OpcUaProtocolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("project/opcUaProtocol")
public class OpcUaProtocolController {

    @Autowired
    private OpcUaProtocolService opcUaProtocolService;

    @GetMapping("list")
    public ResponseEntity<List<OpcUaProtocol>> getAllOpcUaProtocolList() {
        return ResponseEntity.ok(opcUaProtocolService.getAllOpcUaProtocol());
    }
}
