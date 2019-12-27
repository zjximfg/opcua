package com.tst.automation.opcua.project.service;

import com.tst.automation.opcua.project.pojo.OpcUaNode;
import com.tst.automation.opcua.project.pojo.OpcUaServer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OpcUaNodeService {

    List<OpcUaNode> browseNodesByParentNodeId(OpcUaServer opcUaServer, Integer namespaceIndex, String parentNodeId, String nodeIdType);

}
