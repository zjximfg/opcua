package com.tst.automation.opcua.core.service.impl;

import com.tst.automation.opcua.core.mapper.OpcUaNamespaceMapper;
import com.tst.automation.opcua.core.pojo.OpcUaNamespace;
import com.tst.automation.opcua.core.service.OpcUaNamespaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpcUaNamespaceServiceImpl implements OpcUaNamespaceService {

    @Autowired
    private OpcUaNamespaceMapper opcUaNamespaceMapper;

    @Override
    public OpcUaNamespace getOpcUaNamespaceByProtocolIdAndIndex(Long opcUaProtocolId, Integer namespaceIndex) {
        OpcUaNamespace opcUaNamespace = new OpcUaNamespace();
        opcUaNamespace.setProtocolId(opcUaProtocolId);
        opcUaNamespace.setNamespaceIndex(namespaceIndex);
        return opcUaNamespaceMapper.selectOne(opcUaNamespace);
    }

    @Override
    public List<OpcUaNamespace> getOpcUaNamespaceListByProtocolId(Long protocolId) {
        OpcUaNamespace opcUaNamespace = new OpcUaNamespace();
        opcUaNamespace.setProtocolId(protocolId);
        return opcUaNamespaceMapper.select(opcUaNamespace);
    }

    @Override
    public OpcUaNamespace getOpcUaNamespaceById(Long opcUaNamespaceId) {
        return opcUaNamespaceMapper.selectByPrimaryKey(opcUaNamespaceId);
    }
}
