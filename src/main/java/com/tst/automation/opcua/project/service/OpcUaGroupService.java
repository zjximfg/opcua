package com.tst.automation.opcua.project.service;

import com.tst.automation.opcua.project.pojo.OpcUaGroup;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OpcUaGroupService {

    List<OpcUaGroup> getOpcUaGroupByConnectionId(Long opcUaConnectionId);

    void deleteOpcUaGroup(Long id);

    void updateOpcUaGroup(OpcUaGroup opcUaGroup);

    void createOpcUaGroup(OpcUaGroup opcUaGroup);

    List<OpcUaGroup> getAllOpcUaGroup();
}
