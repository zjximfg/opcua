package com.tst.automation.opcua.project.service;

import com.tst.automation.opcua.project.pojo.OpcUaServer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OpcUaServerService {

    List<OpcUaServer> getAllActiveOpcUaServer();

    List<OpcUaServer> browseOpcUaServerListByProtocolId(Long opcUaProtocolId);

    OpcUaServer connect(Long opcUaServerId, List<OpcUaServer> currentOpcUaServerList);

    OpcUaServer getOpcUaServerActiveFirst();

    void updateOpcUaServer(OpcUaServer opcUaServer);

    void insertOpcUaServer(OpcUaServer opcUaServer);
}
