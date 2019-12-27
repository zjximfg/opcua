package com.tst.automation.opcua.project.service.impl;

import com.tst.automation.opcua.project.mapper.OpcUaGroupMapper;
import com.tst.automation.opcua.project.pojo.OpcUaGroup;
import com.tst.automation.opcua.project.service.OpcUaGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpcUaGroupServiceImpl implements OpcUaGroupService {

    @Autowired
    private OpcUaGroupMapper opcUaGroupMapper;

    @Override
    public List<OpcUaGroup> getOpcUaGroupByConnectionId(Long opcUaConnectionId) {
        OpcUaGroup opcUaGroup = new OpcUaGroup();
        opcUaGroup.setIsDeleted(false);
        opcUaGroup.setOpcUaConnectionId(opcUaConnectionId);
        return opcUaGroupMapper.select(opcUaGroup);
    }

    @Override
    public void deleteOpcUaGroup(Long id) {
        OpcUaGroup opcUaGroup = new OpcUaGroup();
        opcUaGroup.setId(id);
        opcUaGroup.setIsDeleted(true);
        opcUaGroupMapper.updateByPrimaryKeySelective(opcUaGroup);
    }

    @Override
    public void updateOpcUaGroup(OpcUaGroup opcUaGroup) {
        opcUaGroup.setIsDeleted(false);
        opcUaGroupMapper.updateByPrimaryKeySelective(opcUaGroup);
    }

    @Override
    public void createOpcUaGroup(OpcUaGroup opcUaGroup) {
        opcUaGroup.setIsDeleted(false);
        opcUaGroupMapper.insertSelective(opcUaGroup);
    }
}
