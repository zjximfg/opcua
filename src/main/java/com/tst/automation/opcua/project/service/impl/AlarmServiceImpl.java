package com.tst.automation.opcua.project.service.impl;

import com.tst.automation.opcua.core.service.OpcUaNamespaceService;
import com.tst.automation.opcua.core.service.OpcUaTask;
import com.tst.automation.opcua.project.mapper.AlarmMapper;
import com.tst.automation.opcua.project.pojo.Alarm;
import com.tst.automation.opcua.project.pojo.OpcUaConnection;
import com.tst.automation.opcua.project.pojo.OpcUaServer;
import com.tst.automation.opcua.project.service.*;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmServiceImpl implements AlarmService {

    @Autowired
    private OpcUaConnectionService opcUaConnectionService;
    @Autowired
    private ItemCategoryService itemCategoryService;
    @Autowired
    private ItemObjectService itemObjectService;
    @Autowired
    private OpcUaNamespaceService opcUaNamespaceService;
    @Autowired
    private AlarmMapper alarmMapper;

    @Override
    public String createFullName(Alarm alarm) {

        alarm.setOpcUaConnection(opcUaConnectionService.getOpcUaConnectionById(alarm.getOpcUaConnectionId()));
        // 2. 获取opcUaConnection.opcUaNamespace
        alarm.getOpcUaConnection().setOpcUaNamespace(opcUaNamespaceService.getOpcUaNamespaceById(alarm.getOpcUaConnection().getOpcUaNamespaceId()));

        // 判断是否为空？
        if (!ObjectUtils.allNotNull(alarm.getOpcUaConnection(), alarm.getOpcUaConnection().getOpcUaNamespace())) return "";
        //
        // 组合字符串
        StringBuilder stringBuilder = new StringBuilder();
        // <opcUaConnection.opcUaNamespace.namespaceUri>
        stringBuilder.append(alarm.getOpcUaConnection().getOpcUaNamespace().getNamespaceUri());

        stringBuilder.append(this.createIdentifier(alarm));

        return stringBuilder.toString();
    }

    @Override
    public String createIdentifier(Alarm alarm) {

        alarm.setOpcUaConnection(opcUaConnectionService.getOpcUaConnectionById(alarm.getOpcUaConnectionId()));

        // 5. 获取itemCategory
        alarm.setItemCategory(itemCategoryService.getItemCategoryById(alarm.getItemCategoryId()));
        // 3. 获取itemObject
        alarm.setItemObject(itemObjectService.getItemObjectById(alarm.getItemObjectId()));
        // 4. 获取itemType

        if (!ObjectUtils.allNotNull(alarm.getOpcUaConnection(), alarm.getItemObject(), alarm.getItemCategory())) return "";

        // 组合字符串
        StringBuilder stringBuilder = new StringBuilder();
        // <opcUaConnection.name>
        stringBuilder.append(alarm.getOpcUaConnection().getConnectionName());
        // .<itemObject.name>
        stringBuilder.append(".").append(alarm.getItemObject().getName());
        // 判断是否itemObject.name ==  "db"， 如果是，添加{dbNumber}
        if (alarm.getItemObject().getName().equals("db")) {
            stringBuilder.append(alarm.getDbNumber());
        }
        // .<address>
        stringBuilder.append(".").append(alarm.getAddress());
        // ,<itemType.s7name>
        stringBuilder.append(",").append("x");
        stringBuilder.append(alarm.getBitAddress());

        return stringBuilder.toString();

    }

    @Override
    public List<Alarm> getAlarmListByOpcUaConnectionId(Long opcUaConnectionId) {
        Alarm alarm = new Alarm();
        alarm.setOpcUaConnectionId(opcUaConnectionId);
        alarm.setIsDeleted(false);
        return alarmMapper.select(alarm);
    }

    @Override
    public void createAlarm(Alarm alarm) {
        alarm.setIsDeleted(false);
        alarm.setItemCategoryId(1L);
        String identifier = this.createIdentifier(alarm);
        alarm.setIdentifier(identifier);
        String fullName = this.createFullName(alarm);
        alarm.setFullName(fullName);
        alarmMapper.insertSelective(alarm);
    }

    @Override
    public void updateAlarm(Alarm alarm) {
        String identifier = this.createIdentifier(alarm);
        alarm.setIdentifier(identifier);
        String fullName = this.createFullName(alarm);
        alarm.setFullName(fullName);
        alarmMapper.updateByPrimaryKeySelective(alarm);
    }

    @Override
    public List<Alarm> getAlarmListOnlineByOpcUaConnectionId(Long opcUaConnectionId) {
        for (OpcUaServer opcUaServer : OpcUaTask.activeOpcUaServerList) {
            for (OpcUaConnection opcUaConnection : opcUaServer.getOpcUaConnectionList()) {
                if (opcUaConnection.getId().equals(opcUaConnectionId)) {
                    return opcUaConnection.getAlarmList();
                }
            }
        }
        return null;
    }

    @Override
    public Alarm getAlarmById(Long alarmId) {
        return alarmMapper.selectByPrimaryKey(alarmId);
    }

    @Override
    public List<Alarm> getAlarmList() {
        Alarm alarm = new Alarm();
        alarm.setIsDeleted(false);
        return alarmMapper.select(alarm);
    }
}
