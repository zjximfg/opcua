package com.tst.automation.opcua.sms.alarm.vo;

import lombok.Data;

import java.util.List;

@Data
public class RoleAlarmUpdateRequestBody {

    private Long roleId;

    private List<Long> alarmIds;
}
