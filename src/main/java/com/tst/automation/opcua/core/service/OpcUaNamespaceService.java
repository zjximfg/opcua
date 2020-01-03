package com.tst.automation.opcua.core.service;

import com.tst.automation.opcua.core.pojo.OpcUaNamespace;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OpcUaNamespaceService {

    OpcUaNamespace getOpcUaNamespaceByProtocolIdAndIndex(Long opcUaProtocolId, Integer namespaceIndex);

    List<OpcUaNamespace> getOpcUaNamespaceListByProtocolId(Long protocolId);

    OpcUaNamespace getOpcUaNamespaceById(Long opcUaNamespaceId);
}
