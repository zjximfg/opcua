package com.tst.automation.opcua.core.service;

import com.tst.automation.opcua.project.pojo.Alarm;
import com.tst.automation.opcua.project.pojo.OpcUaConnection;
import com.tst.automation.opcua.project.pojo.OpcUaServer;
import com.tst.automation.opcua.project.service.AlarmStateService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@Data

@Scope("prototype") // 多例
public class AlarmDataPersistence implements Runnable {

    @Autowired
    private AlarmStateService alarmStateService;

    private Long opcUaConnectionId;

    @Override
    public void run() {
        for (OpcUaServer opcUaServer : OpcUaTask.activeOpcUaServerList) {
            for (OpcUaConnection opcUaConnection : opcUaServer.getOpcUaConnectionList()) {
                if (opcUaConnection.getId().equals(opcUaConnectionId)) {
                    for (Alarm alarm : opcUaConnection.getAlarmList()) {
                        if (alarm.getQuality().equals("good")){
                            // 如果连接状态正常，
                            // coming 上升沿
                            if (alarm.getLastCycleValue().equals("false") && alarm.getCurrentValue().equals("true")) {
                                alarm.setComingTime(new Timestamp(System.currentTimeMillis()));
                                alarmStateService.dataPersistence(alarm, "isComing");
                            }
                            // leaving 下降沿
                            if (alarm.getLastCycleValue().equals("true") && alarm.getCurrentValue().equals("false")) {
                                alarm.setLeavingTime(new Timestamp(System.currentTimeMillis()));
                                alarmStateService.dataPersistence(alarm, "isLeaving");
                            }
                            // 保存上一周期状态
                            alarm.setLastCycleValue(alarm.getCurrentValue());

                        }
                    }
                }
            }
        }
    }
}
