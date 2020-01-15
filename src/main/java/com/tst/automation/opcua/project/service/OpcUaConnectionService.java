package com.tst.automation.opcua.project.service;

import com.tst.automation.opcua.core.pojo.OpcUaNamespace;
import com.tst.automation.opcua.project.pojo.OpcUaConnection;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OpcUaConnectionService {

    List<OpcUaNamespace> getOpcUaNamespaceListByProtocolId(Long protocolId);

    List<OpcUaConnection> getOpcUaConnectionList(Long opcUaServerId);

    void deleteOpcUaConnection(Long opcUaConnectionId);

    void updateOpcUaConnection(OpcUaConnection opcUaConnection);

    void createOpcUaConnection(OpcUaConnection opcUaConnection);

    OpcUaConnection getOpcUaConnectionById(Long id);

    List<OpcUaConnection> getAllOpcUaConnection();
}
