package com.tst.automation.opcua.project.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "tbl_opc_ua_storage_period")
public class StoragePeriod {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private Long period;

    private String name;

    private String corn;
}
