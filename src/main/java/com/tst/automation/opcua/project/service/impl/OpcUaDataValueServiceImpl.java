package com.tst.automation.opcua.project.service.impl;

import com.tst.automation.opcua.core.service.OpcUaTask;
import com.tst.automation.opcua.project.pojo.OpcUaDataValue;
import com.tst.automation.opcua.project.pojo.OpcUaServer;
import com.tst.automation.opcua.project.service.OpcUaDataValueService;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpcUaDataValueServiceImpl implements OpcUaDataValueService {

    @Autowired
    private OpcUaTask opcUaTask;

    private static List<OpcUaDataValue> opcUaDataValueBuffer;

    @Override
    public OpcUaDataValue readOpcUaDataValue(OpcUaServer connectedServer, Integer namespaceIndex, String identifier, String nodeIdType) {

        try {
            NodeId nodeId = null;
            if (nodeIdType.equals("Numeric")) {
                nodeId = new NodeId(Unsigned.ushort(0), Unsigned.uint(identifier));
            }
            if (nodeIdType.equals("String")) {
                nodeId = new NodeId(namespaceIndex, identifier);
            }
            if (nodeId != null){
                DataValue dataValue = opcUaTask.readValue(connectedServer.getOpcUaClient(), nodeId);
                return new OpcUaDataValue(dataValue);
            }
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public OpcUaDataValue monitorOpcUaDataValue(OpcUaServer connectedServer, Integer namespaceIndex, String identifier, String nodeIdType) {
        try {
            NodeId nodeId = null;
            if (nodeIdType.equals("Numeric")) {
                nodeId = new NodeId(Unsigned.ushort(0), Unsigned.uint(identifier));
            }
            if (nodeIdType.equals("String")) {
                nodeId = new NodeId(namespaceIndex, identifier);
            }
            if (nodeId != null){
                DataValue dataValue = null;
                opcUaTask.createSubscription(connectedServer.getOpcUaClient(), nodeId, dataValue);
                //return new OpcUaDataValue(dataValue);
            }
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public OpcUaDataValue subscribeOpcUaDataValue(OpcUaServer connectedServer, Integer namespaceIndex, String identifier, String nodeIdType) {
        try {
            NodeId nodeId = null;
            if (nodeIdType.equals("Numeric")) {
                nodeId = new NodeId(Unsigned.ushort(0), Unsigned.uint(identifier));
            }
            if (nodeIdType.equals("String")) {
                nodeId = new NodeId(namespaceIndex, identifier);
            }
            if (nodeId != null){
               // DataValue dataValue = opcUaTask.subscribeValue(connectedServer.getOpcUaClient(), nodeId);
                //return new OpcUaDataValue(dataValue);
            }
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
