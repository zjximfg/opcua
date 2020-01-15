package com.tst.automation.opcua.project.service.impl;

import com.tst.automation.opcua.project.mapper.AlarmStateMapper;
import com.tst.automation.opcua.project.pojo.Alarm;
import com.tst.automation.opcua.project.pojo.AlarmState;
import com.tst.automation.opcua.project.service.AlarmStateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class AlarmStateServiceImpl implements AlarmStateService {

    @Autowired
    private AlarmStateMapper alarmStateMapper;

    @Override
    public void dataPersistence(Alarm alarm, String stateString) {
        AlarmState alarmState = new AlarmState();
        alarmState.setId(UUID.randomUUID().toString());
        alarmState.setAlarm(alarm);
        alarmState.setAlarmId(alarm.getId());
        switch (stateString) {
            case "isComing":
                alarmState.setIsComing(true);
                alarmState.setIsLeaving(false);
                break;
            case "isLeaving":
                alarmState.setIsComing(false);
                alarmState.setIsLeaving(true);
                break;
            default:
                log.error("alarm 记录时，传入参数错误！");
                return;
        }
        alarmStateMapper.insertSelective(alarmState);
    }
}
