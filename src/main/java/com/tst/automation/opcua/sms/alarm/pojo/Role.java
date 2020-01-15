package com.tst.automation.opcua.sms.alarm.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Data
@Table(name = "tbl_opc_ua_sms_role")
public class Role {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private String name;

    private String description;

    private Boolean isDeleted;

    @Transient
    @JsonIgnore
    private List<User> userList;
}
