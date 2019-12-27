package com.tst.automation.opcua.project.controller;

import com.tst.automation.opcua.project.pojo.OpcUaServer;
import com.tst.automation.opcua.project.pojo.OpcUaServerRequest;
import com.tst.automation.opcua.project.service.OpcUaServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("project/opcUaServer")
public class OpcUaServerController {

    @Autowired
    private OpcUaServerService opcUaServerService;

    @GetMapping
    public ResponseEntity<OpcUaServer> getOpcUaServerActiveFirst() {
        return ResponseEntity.ok(opcUaServerService.getOpcUaServerActiveFirst());
    }

    @PutMapping
    public ResponseEntity<OpcUaServer> updateOpcUaServer(@RequestBody OpcUaServerRequest opcUaServerRequest) {
        System.out.println(opcUaServerRequest);
        OpcUaServer opcUaServer = new OpcUaServer();
        opcUaServer.setId(opcUaServerRequest.getId());
        opcUaServer.setOpcUaProtocolId(opcUaServerRequest.getOpcUaProtocolId());
        opcUaServer.setSecurityPolicyUri(opcUaServerRequest.getSecurityPolicyUri());
        if (opcUaServer.getId()== null || opcUaServer.getId() == 0) {
            opcUaServerService.insertOpcUaServer(opcUaServer);
        } else {
            opcUaServerService.updateOpcUaServer(opcUaServer);
        }
        return ResponseEntity.ok(null);
    }



}
