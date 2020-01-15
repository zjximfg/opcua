package com.tst.automation.opcua.sms.alarm.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色 报警列表中间表，用于分配要发送的报警列表
 */
@Data
@Table(name = "tbl_opc_ua_sms_role_alarm")
public class RoleAlarm {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long Id;

    private Long roleId;

    private Long alarmId;
}
