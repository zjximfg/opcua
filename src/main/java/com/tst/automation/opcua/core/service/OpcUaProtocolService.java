package com.tst.automation.opcua.core.service;

import com.tst.automation.opcua.core.pojo.OpcUaProtocol;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OpcUaProtocolService {

    List<OpcUaProtocol> getAllOpcUaProtocol();

    OpcUaProtocol getOpcUaProtocolById(Long id);
}
