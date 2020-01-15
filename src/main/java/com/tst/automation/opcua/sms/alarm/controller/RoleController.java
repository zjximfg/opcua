package com.tst.automation.opcua.sms.alarm.controller;

import com.tst.automation.opcua.sms.alarm.pojo.Role;
import com.tst.automation.opcua.sms.alarm.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sms/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("list")
    public ResponseEntity<List<Role>> getRoleList(){
        List<Role> roleList = roleService.getRoleList();
        return ResponseEntity.ok(roleList);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteRole(@RequestBody Role role) {
        roleService.deleteRole(role.getId());
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateRole(@RequestBody Role role) {
        roleService.updateRole(role);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> createRole(@RequestBody Role role) {
        roleService.createRole(role);
        return ResponseEntity.ok().build();
    }
}
