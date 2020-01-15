package com.tst.automation.opcua.project.service.impl;

import com.tst.automation.opcua.project.mapper.AlarmLevelMapper;
import com.tst.automation.opcua.project.pojo.AlarmLevel;
import com.tst.automation.opcua.project.service.AlarmLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmLevelServiceImpl implements AlarmLevelService {

    @Autowired
    private AlarmLevelMapper alarmLevelMapper;

    @Override
    public List<AlarmLevel> getAlarmLevelList() {
        AlarmLevel alarmLevel = new AlarmLevel();
        alarmLevel.setIsDeleted(false);
        return alarmLevelMapper.select(alarmLevel);
    }

    @Override
    public void deleteAlarmLevel(Long id) {
        AlarmLevel alarmLevel = new AlarmLevel();
        alarmLevel.setId(id);
        alarmLevel.setIsDeleted(true);
        alarmLevelMapper.updateByPrimaryKeySelective(alarmLevel);
    }

    @Override
    public void updateAlarmLevel(AlarmLevel alarmLevel) {
        alarmLevel.setIsDeleted(false);
        alarmLevelMapper.updateByPrimaryKeySelective(alarmLevel);
    }

    @Override
    public void createAlarmLevel(AlarmLevel alarmLevel) {
        alarmLevel.setIsDeleted(false);
        alarmLevelMapper.insertSelective(alarmLevel);
    }
}
