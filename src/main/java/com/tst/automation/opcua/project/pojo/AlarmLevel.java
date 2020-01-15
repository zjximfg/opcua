package com.tst.automation.opcua.project.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "tbl_opc_ua_alarm_level")
public class AlarmLevel {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    // 级别名称
    private String name;
    // 级别描述
    private String description;

    private Boolean isDeleted;
}
