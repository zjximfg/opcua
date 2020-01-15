package com.tst.automation.opcua.sms.alarm.service.impl;

import com.tst.automation.opcua.sms.alarm.mapper.RoleMapper;
import com.tst.automation.opcua.sms.alarm.pojo.Role;
import com.tst.automation.opcua.sms.alarm.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getRoleList() {
        Role role = new Role();
        role.setIsDeleted(false);
        return roleMapper.select(role);
    }

    @Override
    public void deleteRole(Long id) {
        Role role = new Role();
        role.setId(id);
        role.setIsDeleted(true);
        roleMapper.select(role);
    }

    @Override
    public void updateRole(Role role) {
        role.setIsDeleted(false);
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public void createRole(Role role) {
        role.setIsDeleted(false);
        roleMapper.insertSelective(role);
    }
}
