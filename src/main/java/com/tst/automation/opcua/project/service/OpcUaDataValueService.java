package com.tst.automation.opcua.project.service;

import com.tst.automation.opcua.project.pojo.OpcUaDataValue;
import com.tst.automation.opcua.project.pojo.OpcUaServer;
import org.springframework.stereotype.Service;

@Service
public interface OpcUaDataValueService {

    OpcUaDataValue readOpcUaDataValue(OpcUaServer connectedServer, Integer namespaceIndex, String identifier, String nodeIdType);

    OpcUaDataValue monitorOpcUaDataValue(OpcUaServer connectedServer, Integer namespaceIndex, String identifier, String nodeIdType);

    OpcUaDataValue subscribeOpcUaDataValue(OpcUaServer connectedServer, Integer namespaceIndex, String identifier, String nodeIdType);
}
