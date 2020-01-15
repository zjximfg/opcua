package com.tst.automation.opcua.project.service.impl;

import com.tst.automation.opcua.core.pojo.OpcUaNamespace;
import com.tst.automation.opcua.core.service.OpcUaNamespaceService;
import com.tst.automation.opcua.project.mapper.OpcUaConnectionMapper;
import com.tst.automation.opcua.project.pojo.OpcUaConnection;
import com.tst.automation.opcua.project.service.OpcUaConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpcUaConnectionServiceImpl implements OpcUaConnectionService {

    @Autowired
    private OpcUaNamespaceService opcUaNamespaceService;

    @Autowired
    private OpcUaConnectionMapper opcUaConnectionMapper;

    @Override
    public List<OpcUaNamespace> getOpcUaNamespaceListByProtocolId(Long protocolId) {
        return opcUaNamespaceService.getOpcUaNamespaceListByProtocolId(protocolId);
    }

    @Override
    public List<OpcUaConnection> getOpcUaConnectionList(Long opcUaServerId) {
        OpcUaConnection opcUaConnection = new OpcUaConnection();
        opcUaConnection.setOpcUaServerId(opcUaServerId);
        opcUaConnection.setIsDeleted(false);
        return opcUaConnectionMapper.select(opcUaConnection);
    }

    @Override
    public void deleteOpcUaConnection(Long opcUaConnectionId) {
        OpcUaConnection opcUaConnection = new OpcUaConnection();
        opcUaConnection.setId(opcUaConnectionId);
        opcUaConnection.setIsDeleted(true);
        opcUaConnectionMapper.updateByPrimaryKeySelective(opcUaConnection);
    }

    @Override
    public void updateOpcUaConnection(OpcUaConnection opcUaConnection) {
        opcUaConnection.setIsDeleted(false);
        opcUaConnectionMapper.updateByPrimaryKeySelective(opcUaConnection);
    }

    @Override
    public void createOpcUaConnection(OpcUaConnection opcUaConnection) {
        opcUaConnection.setIsDeleted(false);
        opcUaConnectionMapper.insertSelective(opcUaConnection);
    }

    @Override
    public OpcUaConnection getOpcUaConnectionById(Long id) {
        return opcUaConnectionMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<OpcUaConnection> getAllOpcUaConnection() {
        OpcUaConnection opcUaConnection = new OpcUaConnection();
        opcUaConnection.setIsDeleted(false);
        return opcUaConnectionMapper.select(opcUaConnection);
    }
}
