package com.tst.automation.opcua.project.controller;

import com.tst.automation.opcua.project.pojo.AlarmCategory;
import com.tst.automation.opcua.project.service.AlarmCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("project/alarm/alarmCategory")
public class AlarmCategoryController {

    @Autowired
    private AlarmCategoryService alarmCategoryService;

    @GetMapping("list")
    public ResponseEntity<List<AlarmCategory>> getAlarmCategoryList() {
        List<AlarmCategory> alarmCategoryList = alarmCategoryService.getAlarmCategoryList();
        return ResponseEntity.ok(alarmCategoryList);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAlarmCategory(@RequestBody AlarmCategory alarmCategory) {
        alarmCategoryService.deleteAlarmCategory(alarmCategory.getId());
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateAlarmCategory(@RequestBody AlarmCategory alarmCategory) {
        alarmCategoryService.updateAlarmCategory(alarmCategory);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> createAlarmCategory(@RequestBody AlarmCategory alarmCategory) {
        alarmCategoryService.createAlarmCategory(alarmCategory);
        return ResponseEntity.ok().build();
    }
}
