package com.tst.automation.opcua.sms.alarm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Table(name = "tbl_opc_ua_sms_user")
public class User {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private Long roleId;

    private String name;

    private Integer gender;   // 0 = 男， 1 = 女

    private String telephone;

    private String email;

    private String description;

    private Boolean isDeleted;

    @Transient
    @JsonIgnore
    private Role role;
}
