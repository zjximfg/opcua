package com.tst.automation.opcua.project.controller;

import com.tst.automation.opcua.project.pojo.AlarmLevel;
import com.tst.automation.opcua.project.service.AlarmLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("project/alarm/alarmLevel")
public class AlarmLevelController {

    @Autowired
    private AlarmLevelService alarmLevelService;

    @GetMapping("list")
    public ResponseEntity<List<AlarmLevel>> getAlarmLevelList() {
        List<AlarmLevel> alarmLevelList = alarmLevelService.getAlarmLevelList();
        return ResponseEntity.ok(alarmLevelList);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAlarmLevel(@RequestBody AlarmLevel alarmLevel) {
        alarmLevelService.deleteAlarmLevel(alarmLevel.getId());
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateAlarmLevel(@RequestBody AlarmLevel alarmLevel) {
        alarmLevelService.updateAlarmLevel(alarmLevel);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> createAlarmLevel(@RequestBody AlarmLevel alarmLevel) {
        alarmLevelService.createAlarmLevel(alarmLevel);
        return ResponseEntity.ok().build();
    }
}
