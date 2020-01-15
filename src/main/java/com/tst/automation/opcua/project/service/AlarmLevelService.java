package com.tst.automation.opcua.project.service;

import com.tst.automation.opcua.project.pojo.AlarmLevel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AlarmLevelService {

    List<AlarmLevel> getAlarmLevelList();

    void deleteAlarmLevel(Long id);

    void updateAlarmLevel(AlarmLevel alarmLevel);

    void createAlarmLevel(AlarmLevel alarmLevel);
}
