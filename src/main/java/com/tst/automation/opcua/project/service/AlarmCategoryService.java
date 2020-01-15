package com.tst.automation.opcua.project.service;

import com.tst.automation.opcua.project.pojo.AlarmCategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AlarmCategoryService {
    List<AlarmCategory> getAlarmCategoryList();

    void deleteAlarmCategory(Long id);

    void updateAlarmCategory(AlarmCategory alarmCategory);

    void createAlarmCategory(AlarmCategory alarmCategory);
}
