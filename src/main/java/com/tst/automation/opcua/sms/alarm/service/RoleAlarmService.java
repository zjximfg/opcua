package com.tst.automation.opcua.sms.alarm.service;

import com.tst.automation.opcua.project.pojo.Alarm;
import com.tst.automation.opcua.sms.alarm.pojo.RoleAlarm;
import com.tst.automation.opcua.sms.alarm.vo.RoleAlarmUpdateRequestBody;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleAlarmService {
    List<RoleAlarm> getRoleAlarmListByRoleId(Long roleId);

    void updateRoleAlarmList(RoleAlarmUpdateRequestBody roleAlarmUpdateRequestBody);

    List<Alarm> getAlarmListByRoleId(Long roleId);
}
