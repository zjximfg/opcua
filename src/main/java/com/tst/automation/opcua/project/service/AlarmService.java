package com.tst.automation.opcua.project.service;

import com.tst.automation.opcua.project.pojo.Alarm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AlarmService {

    String createFullName(Alarm alarm);

    String createIdentifier(Alarm alarm);

    List<Alarm> getAlarmListByOpcUaConnectionId(Long opcUaConnectionId);

    void createAlarm(Alarm alarm);

    void updateAlarm(Alarm alarm);

    List<Alarm> getAlarmListOnlineByOpcUaConnectionId(Long opcUaConnectionId);

    Alarm getAlarmById(Long alarmId);

    List<Alarm> getAlarmList();
}
