package com.tst.automation.opcua.sms.alarm.controller;

import com.tst.automation.opcua.project.pojo.Alarm;
import com.tst.automation.opcua.project.service.AlarmService;
import com.tst.automation.opcua.sms.alarm.pojo.RoleAlarm;
import com.tst.automation.opcua.sms.alarm.service.RoleAlarmService;
import com.tst.automation.opcua.sms.alarm.vo.RoleAlarmUpdateRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sms/roleAlarm")
public class RoleAlarmController {

    @Autowired
    private RoleAlarmService roleAlarmService;
    @Autowired
    private AlarmService alarmService;

    @GetMapping("list")
    public ResponseEntity<List<RoleAlarm>> getRoleAlarmListById(@RequestParam("roleId") Long roleId) {
        List<RoleAlarm> roleAlarmList = roleAlarmService.getRoleAlarmListByRoleId(roleId);
        return ResponseEntity.ok(roleAlarmList);
    }

    @PutMapping
    public ResponseEntity<Void> updateRoleAlarmList(@RequestBody RoleAlarmUpdateRequestBody roleAlarmUpdateRequestBody) {
        roleAlarmService.updateRoleAlarmList(roleAlarmUpdateRequestBody);
        return ResponseEntity.ok().build();
    }

    @GetMapping("alarm/list")
    public ResponseEntity<List<Alarm>> getAlarmListByRoleId(@RequestParam("roleId") Long roleId) {
        List<Alarm> alarmList = roleAlarmService.getAlarmListByRoleId(roleId);
        return ResponseEntity.ok(alarmList);
    }

    @GetMapping("alarm/all")
    public ResponseEntity<List<Alarm>> getAllAlarmList() {
        List<Alarm> alarmList = alarmService.getAlarmList();
        return ResponseEntity.ok(alarmList);
    }
}
