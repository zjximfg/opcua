package com.tst.automation.opcua.project.service.impl;

import com.tst.automation.opcua.core.service.OpcUaNamespaceService;
import com.tst.automation.opcua.core.service.OpcUaTask;
import com.tst.automation.opcua.project.pojo.OpcUaNode;
import com.tst.automation.opcua.project.pojo.OpcUaServer;
import com.tst.automation.opcua.project.service.OpcUaNodeService;
import org.eclipse.milo.opcua.sdk.client.api.nodes.Node;
import org.eclipse.milo.opcua.stack.core.types.enumerated.NodeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OpcUaNodeServiceImpl implements OpcUaNodeService {

    @Autowired
    private OpcUaTask opcUaTask;
    @Autowired
    private OpcUaNamespaceService opcUaNamespaceService;

    @Override
    public List<OpcUaNode> browseNodesByParentNodeId(OpcUaServer opcUaServer, Integer namespaceIndex, String parentIdentifier, String nodeIdType) {
        List<OpcUaNode> opcUaNodes = new ArrayList<>();
        try {
            List<Node> nodes = opcUaTask.browseNode(opcUaServer.getOpcUaClient(), namespaceIndex, parentIdentifier, nodeIdType);

            // 通过获得nodes 返回所有的opcUaNodes
            for (Node node : nodes) {
                OpcUaNode opcUaNode = new OpcUaNode(node, opcUaServer);
                if (opcUaNode.getNodeClass().equals(NodeClass.Variable)) {
                    // 如果是变量需要得到相对应的 namespaceURI 如 "S7:", 然后生成variableId
                    String uri = opcUaNamespaceService.getOpcUaNamespaceByProtocolIdAndIndex(opcUaServer.getOpcUaProtocolId(), opcUaNode.getNamespaceIndex()).getNamespaceUri();

                    opcUaNode.setVariableId(uri);
                }
                opcUaNodes.add(opcUaNode);
            }
            // nodes 去重复
            return this.removeNodeDuplicate(opcUaNodes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<OpcUaNode> removeNodeDuplicate(List<OpcUaNode> opcUaNodes) {
        for (int i = 0; i < opcUaNodes.size() - 1; i++) {
            for (int j = opcUaNodes.size() - 1; j > i; j--) {
                if (opcUaNodes.get(j).getIdentifier().equals(opcUaNodes.get(i).getIdentifier())) {
                    opcUaNodes.remove(j);
                }
            }
        }
        return opcUaNodes;
    }
}
