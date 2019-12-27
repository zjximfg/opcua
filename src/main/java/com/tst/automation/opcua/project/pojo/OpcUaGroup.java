package com.tst.automation.opcua.project.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Table(name = "tbl_opc_ua_group")
public class OpcUaGroup {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private String groupName;

    private Long storagePeriodId;

    private Long opcUaConnectionId;

    private Boolean isDeleted;

    @Transient
    @JsonIgnore
    private StoragePeriod storagePeriod;

    @Transient
    @JsonIgnore
    private OpcUaConnection opcUaConnection;
}
