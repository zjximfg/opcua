package com.tst.automation.opcua.core.service;

import com.tst.automation.opcua.core.pojo.OpcUaNamespace;
import com.tst.automation.opcua.project.pojo.Alarm;
import com.tst.automation.opcua.project.pojo.OpcUaConnection;
import com.tst.automation.opcua.project.pojo.OpcUaDataValue;
import com.tst.automation.opcua.project.pojo.OpcUaServer;
import com.tst.automation.opcua.project.service.OpcUaDataValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlarmReadService implements Runnable {

    @Autowired
    private OpcUaDataValueService opcUaDataValueService;
    @Autowired
    private OpcUaNamespaceService opcUaNamespaceService;

    @Override
    public void run() {

        for (OpcUaServer opcUaServer : OpcUaTask.activeOpcUaServerList) {
            for (OpcUaConnection opcUaConnection : opcUaServer.getOpcUaConnectionList()) {
                OpcUaNamespace opcUaNamespace = opcUaNamespaceService.getOpcUaNamespaceById(opcUaConnection.getOpcUaNamespaceId());
                for (Alarm alarm : opcUaConnection.getAlarmList()) {
                    OpcUaDataValue opcUaDataValue = opcUaDataValueService.readOpcUaDataValue(opcUaServer, opcUaNamespace.getNamespaceIndex(), alarm.getIdentifier(), "String");
                    alarm.setOpcUaDataValue(opcUaDataValue);
//                    alarm.setLastCycleValue(alarm.getCurrentValue());
                    alarm.setCurrentValue(opcUaDataValue.getValue());
                    alarm.setQuality(opcUaDataValue.getQuality());
                }
            }
        }
    }
}
