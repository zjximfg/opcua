package com.tst.automation.opcua.sms.alarm.service.impl;

import com.tst.automation.opcua.project.pojo.Alarm;
import com.tst.automation.opcua.project.service.AlarmService;
import com.tst.automation.opcua.sms.alarm.mapper.RoleAlarmMapper;
import com.tst.automation.opcua.sms.alarm.pojo.RoleAlarm;
import com.tst.automation.opcua.sms.alarm.service.RoleAlarmService;
import com.tst.automation.opcua.sms.alarm.vo.RoleAlarmUpdateRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleAlarmServiceImpl implements RoleAlarmService {

    @Autowired
    private RoleAlarmMapper roleAlarmMapper;

    @Autowired
    private AlarmService alarmService;

    @Override
    public List<RoleAlarm> getRoleAlarmListByRoleId(Long roleId) {
        RoleAlarm roleAlarm = new RoleAlarm();
        roleAlarm.setRoleId(roleId);
        return roleAlarmMapper.select(roleAlarm);
    }

    @Override
    public void updateRoleAlarmList(RoleAlarmUpdateRequestBody roleAlarmUpdateRequestBody) {
        // 先通过roleId获取原先 alarm id 数组
        RoleAlarm roleAlarm1 = new RoleAlarm();
        List<RoleAlarm> old = roleAlarmMapper.select(roleAlarm1);
        Set<Long> oldAlarmIds = new HashSet<>();
        for (RoleAlarm roleAlarm : old) {
            oldAlarmIds.add(roleAlarm.getAlarmId());
        }
        // 获取新的alarm id 数组
        Set<Long> newAlarmIds = new HashSet<>(roleAlarmUpdateRequestBody.getAlarmIds());

        // 原先有的，现在没有的。删除操作。
        oldAlarmIds.removeAll(newAlarmIds);

        for (Long oldAlarmId : oldAlarmIds) {
            RoleAlarm roleAlarm = new RoleAlarm();
            roleAlarm.setRoleId(roleAlarmUpdateRequestBody.getRoleId());
            roleAlarm.setAlarmId(oldAlarmId);
            roleAlarmMapper.delete(roleAlarm);
        }
        // 现在有的， 原先没有的，添加造作
        oldAlarmIds = new HashSet<>();
        for (RoleAlarm roleAlarm : old) {
            oldAlarmIds.add(roleAlarm.getAlarmId());
        }
        newAlarmIds.removeAll(oldAlarmIds);

        for (Long newAlarmId : newAlarmIds) {
            RoleAlarm roleAlarm = new RoleAlarm();
            roleAlarm.setRoleId(roleAlarmUpdateRequestBody.getRoleId());
            roleAlarm.setAlarmId(newAlarmId);
            roleAlarmMapper.insertSelective(roleAlarm);
        }
    }

    @Override
    public List<Alarm> getAlarmListByRoleId(Long roleId) {
        List<RoleAlarm> roleAlarmList = getRoleAlarmListByRoleId(roleId);
        List<Alarm> alarmList = new ArrayList<>();
        for (RoleAlarm roleAlarm : roleAlarmList) {
            Alarm alarm = alarmService.getAlarmById(roleAlarm.getAlarmId());
            alarmList.add(alarm);
        }
        return alarmList;
    }
}
