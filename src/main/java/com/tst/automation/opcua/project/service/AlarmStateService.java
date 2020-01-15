package com.tst.automation.opcua.project.service;

import com.tst.automation.opcua.project.pojo.Alarm;
import org.springframework.stereotype.Service;

@Service
public interface AlarmStateService {
    void dataPersistence(Alarm alarm, String stateString);
}
