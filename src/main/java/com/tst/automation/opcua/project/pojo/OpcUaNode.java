package com.tst.automation.opcua.project.pojo;

import lombok.Data;
import org.eclipse.milo.opcua.stack.core.types.builtin.*;
import org.eclipse.milo.opcua.sdk.client.api.nodes.Node;
import org.eclipse.milo.opcua.stack.core.types.enumerated.NodeClass;


@Data
public class OpcUaNode {

    private Long id;

    private NodeId nodeId;                  // 通过node.getNodeId().get()获得

    private String identifier;              // 等于 nodeId 中的 identifier

    private Integer namespaceIndex;         // nodeId 中的 namespaceIndex

    private String nodeIdType;              // Numeric or String

    private String nodeIdString;            // 用于前端显示

    private QualifiedName qualifiedName;    // browseName 通过node.getBrowseName().get()获得

    private String browseName;              // 用于前端的显示

    private Node node;

    private NodeClass nodeClass;            // Variable or Object 等枚举， 可以直接 toString()

    private Long opcUaServerId;             // 用于存储opc服务器信息。父类

//    private Long opcUaProtocolId;           // 用于存储协议信息，用于Variable id的创建。

    private String namespaceUri;

    private String variableId;

    private String dataType;

    //private OpcUaServer opcUaServer;
    // ************************************
    // 以下当nodeClass 为 "Variable" 时 有效
    private OpcUaDataValue opcUaDataValue;


    public OpcUaNode(Node node, OpcUaServer opcUaServer) {
        this.node = node;
        try {
            this.qualifiedName = node.getBrowseName().get();
            this.nodeId = node.getNodeId().get();
            this.nodeClass = node.getNodeClass().get();
//            if (nodeClass.equals(NodeClass.Variable)) {
//                this.dataType = ((OpcUaVariableNode)node).getTypeDefinition().get().toString();
//                ((UaVariableNode)node).getVariableComponent().get()
//                System.out.println(dataType);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        identifier = nodeId.getIdentifier().toString();
        namespaceIndex = nodeId.getNamespaceIndex().intValue();
        nodeIdType = nodeId.getType().toString();
        nodeIdString = nodeId.toParseableString();
        browseName = qualifiedName.getName();
        this.opcUaServerId = opcUaServer.getId();

    }



    public void setVariableId(String namespaceUri) {
        this.namespaceUri = namespaceUri;
        this.variableId = namespaceUri + identifier;
    }

}
