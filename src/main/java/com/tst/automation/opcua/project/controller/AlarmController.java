package com.tst.automation.opcua.project.controller;

import com.tst.automation.opcua.project.pojo.Alarm;
import com.tst.automation.opcua.project.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("project/alarm")
public class AlarmController {

    @Autowired
    private AlarmService alarmService;

    @GetMapping("list")
    public ResponseEntity<List<Alarm>> getAlarmListByOpcUaConnectionId(@RequestParam("opcUaConnectionId") Long opcUaConnectionId) {
        List<Alarm> alarmList = alarmService.getAlarmListByOpcUaConnectionId(opcUaConnectionId);
        return ResponseEntity.ok(alarmList);
    }

    @GetMapping("list/all")
    public ResponseEntity<List<Alarm>> getAlarmList() {
        List<Alarm> alarmList = alarmService.getAlarmList();
        return ResponseEntity.ok(alarmList);
    }

    @GetMapping("list/online")
    public ResponseEntity<List<Alarm>> getAlarmListOnlineByOpcUaConnectionId(@RequestParam("opcUaConnectionId") Long opcUaConnectionId) {
        List<Alarm> alarmList = alarmService.getAlarmListOnlineByOpcUaConnectionId(opcUaConnectionId);
        return ResponseEntity.ok(alarmList);
    }

    @PostMapping
    public ResponseEntity<Void> createAlarm(@RequestBody Alarm alarm) {
        alarmService.createAlarm(alarm);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateAlarm(@RequestBody Alarm alarm) {
        alarmService.updateAlarm(alarm);
        return ResponseEntity.ok().build();
    }
}
