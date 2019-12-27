package com.tst.automation.opcua.project.controller;

import com.tst.automation.opcua.core.pojo.OpcUaNamespace;
import com.tst.automation.opcua.project.pojo.OpcUaConnection;
import com.tst.automation.opcua.project.service.OpcUaConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("project/opcUaConnection")
public class OpcUaConnectionController {

    @Autowired
    private OpcUaConnectionService opcUaConnectionService;

    @GetMapping("opcUaNamespace/list")
    public ResponseEntity<List<OpcUaNamespace>> getOpcUaNamespaceListByProtocolId(@RequestParam("protocolId") Long protocolId) {
        List<OpcUaNamespace> opcUaNamespaceList = opcUaConnectionService.getOpcUaNamespaceListByProtocolId(protocolId);
        return ResponseEntity.ok(opcUaNamespaceList);
    }

    @GetMapping("list")
    public ResponseEntity<List<OpcUaConnection>> getOpcUaConnectionList(@RequestParam("opcUaServerId") Long opcUaServerId) {
        List<OpcUaConnection> opcUaConnectionList = opcUaConnectionService.getOpcUaConnectionList(opcUaServerId);
        return ResponseEntity.ok(opcUaConnectionList);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteOpcUaConnection(@RequestBody OpcUaConnection opcUaConnection) {
        opcUaConnectionService.deleteOpcUaConnection(opcUaConnection.getId());
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateOpcUaConnection(@RequestBody OpcUaConnection opcUaConnection) {
        opcUaConnectionService.updateOpcUaConnection(opcUaConnection);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> createOpcUaConnection(@RequestBody OpcUaConnection opcUaConnection) {
        opcUaConnectionService.createOpcUaConnection(opcUaConnection);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<OpcUaConnection> getOpcUaConnectionById(@RequestParam("id") Long id) {
        OpcUaConnection opcUaConnection = opcUaConnectionService.getOpcUaConnectionById(id);
        return ResponseEntity.ok(opcUaConnection);
    }
}
