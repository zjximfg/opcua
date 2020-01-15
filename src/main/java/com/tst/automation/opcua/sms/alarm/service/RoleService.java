package com.tst.automation.opcua.sms.alarm.service;

import com.tst.automation.opcua.sms.alarm.pojo.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {

    List<Role> getRoleList();

    void deleteRole(Long id);

    void updateRole(Role role);

    void createRole(Role role);
}
