package com.tst.automation.opcua.project.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "tbl_opc_ua_alarm_category")
public class AlarmCategory {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    // 类别名称，
    private String name;
    // 类别描述，
    private String description;

    private Boolean isDeleted;

}
