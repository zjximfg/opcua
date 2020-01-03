package com.tst.automation.opcua.project.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tst.automation.opcua.core.pojo.OpcUaNamespace;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Data
@Entity
@Table(name = "tbl_opc_ua_connection")
public class OpcUaConnection {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private Long opcUaNamespaceId;

    private String connectionName;  // <连接名称> 用于变量

    private Long opcUaServerId; // 父类

    private Boolean isDeleted;

    @Transient
    @JsonIgnore
    private OpcUaNamespace opcUaNamespace;

    @Transient
    @JsonIgnore
    private OpcUaServer opcUaServer;

    @Transient
    @JsonIgnore
    private List<OpcUaGroup> opcUaGroupList;

}
