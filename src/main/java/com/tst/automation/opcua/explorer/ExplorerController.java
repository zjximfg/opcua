package com.tst.automation.opcua.explorer;

import com.tst.automation.opcua.core.pojo.OpcUaProtocol;
import com.tst.automation.opcua.core.service.OpcUaProtocolService;
import com.tst.automation.opcua.project.pojo.OpcUaDataValue;
import com.tst.automation.opcua.project.pojo.OpcUaNode;
import com.tst.automation.opcua.project.pojo.OpcUaServer;
import com.tst.automation.opcua.project.service.OpcUaDataValueService;
import com.tst.automation.opcua.project.service.OpcUaNodeService;
import com.tst.automation.opcua.project.service.OpcUaServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("explorer")
public class ExplorerController {

    @Autowired
    private OpcUaProtocolService opcUaProtocolService;

    // 用户缓存已经连接的OpcUaServer
    private static List<OpcUaServer> currentOpcUaServerList;

    @Autowired
    private OpcUaServerService opcUaServerService;

    @Autowired
    private OpcUaNodeService opcUaNodeService;

    @Autowired
    private OpcUaDataValueService opcUaDataValueService;

    @GetMapping("opcUaProtocol/list")
    public ResponseEntity<List<OpcUaProtocol>> getAllOpcUaProtocol() {
        List<OpcUaProtocol> allOpcUaProtocol = opcUaProtocolService.getAllOpcUaProtocol();
        return ResponseEntity.ok(allOpcUaProtocol);
    }

    @GetMapping("opcUaServer/list")
    public ResponseEntity<List<OpcUaServer>> getOpcUaServerListByProtocolId(@RequestParam("opcUaProtocolId") Long opcUaProtocolId) {
        System.out.println(opcUaProtocolId);
        List<OpcUaServer> opcUaServerList = opcUaServerService.browseOpcUaServerListByProtocolId(opcUaProtocolId);
        ExplorerController.currentOpcUaServerList = opcUaServerList;
        return ResponseEntity.ok(opcUaServerList);
    }

    @GetMapping("opcUaNode/rootList")
    public ResponseEntity<List<OpcUaNode>> getOpcUaRootNodeListByServer(@RequestParam("opcUaServerId") Long opcUaServerId) {
        if (ExplorerController.currentOpcUaServerList == null) return null;
        // 连接服务器 并 返回连接好的opcUaServer
        OpcUaServer connectedServer = opcUaServerService.connect(opcUaServerId, ExplorerController.currentOpcUaServerList);
        // browser 根node
        List<OpcUaNode> opcUaNodes = opcUaNodeService.browseNodesByParentNodeId(connectedServer, 0, "0", "Numeric");
        return ResponseEntity.ok(opcUaNodes);
    }

    @GetMapping("opcUaNode/list")
    public ResponseEntity<List<OpcUaNode>> getOpcUaNodeListByNodeId(@RequestParam("opcUaServerId") Long opcUaServerId,
                                                                    @RequestParam("namespaceIndex") Integer namespaceIndex,
                                                                    @RequestParam("identifier") String identifier,
                                                                    @RequestParam("nodeIdType") String nodeIdType) {
        if (ExplorerController.currentOpcUaServerList == null) return null;
        // 连接服务器 并 返回连接好的opcUaServer
        OpcUaServer connectedServer = opcUaServerService.connect(opcUaServerId, ExplorerController.currentOpcUaServerList);
        List<OpcUaNode> opcUaNodes = opcUaNodeService.browseNodesByParentNodeId(connectedServer, namespaceIndex, identifier, nodeIdType);
        return ResponseEntity.ok(opcUaNodes);
    }

    @GetMapping("opcUaDataValue/read")
    public ResponseEntity<OpcUaDataValue> readOpcUaVariable(@RequestParam("opcUaServerId") Long opcUaServerId,
                                                       @RequestParam("namespaceIndex") Integer namespaceIndex,
                                                       @RequestParam("identifier") String identifier,
                                                       @RequestParam("nodeIdType") String nodeIdType) {
        if (ExplorerController.currentOpcUaServerList == null) return null;
        OpcUaServer connectedServer = opcUaServerService.connect(opcUaServerId, ExplorerController.currentOpcUaServerList);

        OpcUaDataValue opcUaDataValue = opcUaDataValueService.readOpcUaDataValue(connectedServer, namespaceIndex, identifier, nodeIdType);
        return ResponseEntity.ok(opcUaDataValue);
    }

    @GetMapping("opcUaDataValue/monitor")
    public ResponseEntity<OpcUaDataValue> monitorOpcUaDataValue(@RequestParam("opcUaServerId") Long opcUaServerId,
                                                            @RequestParam("namespaceIndex") Integer namespaceIndex,
                                                            @RequestParam("identifier") String identifier,
                                                            @RequestParam("nodeIdType") String nodeIdType) {
        if (ExplorerController.currentOpcUaServerList == null) return null;
        OpcUaServer connectedServer = opcUaServerService.connect(opcUaServerId, ExplorerController.currentOpcUaServerList);

        OpcUaDataValue opcUaDataValue = opcUaDataValueService.monitorOpcUaDataValue(connectedServer, namespaceIndex, identifier, nodeIdType);
        return ResponseEntity.ok(opcUaDataValue);
    }

    @GetMapping("opcUaDataValue/subscribe")
    public ResponseEntity<OpcUaDataValue> subscribeOpcUaDataValue(@RequestParam("opcUaServerId") Long opcUaServerId,
                                                                  @RequestParam("namespaceIndex") Integer namespaceIndex,
                                                                  @RequestParam("identifier") String identifier,
                                                                  @RequestParam("nodeIdType") String nodeIdType) {
        if (ExplorerController.currentOpcUaServerList == null) return null;
        OpcUaServer connectedServer = opcUaServerService.connect(opcUaServerId, ExplorerController.currentOpcUaServerList);

        OpcUaDataValue opcUaDataValue = opcUaDataValueService.subscribeOpcUaDataValue(connectedServer, namespaceIndex, identifier, nodeIdType);
        return ResponseEntity.ok(opcUaDataValue);
    }
}
