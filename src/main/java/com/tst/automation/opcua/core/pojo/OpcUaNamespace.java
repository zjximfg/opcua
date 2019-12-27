package com.tst.automation.opcua.core.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "tbl_opc_ua_namespace")
public class OpcUaNamespace {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private Long protocolId;

    private Integer namespaceIndex;

    private String namespaceUri;
}
