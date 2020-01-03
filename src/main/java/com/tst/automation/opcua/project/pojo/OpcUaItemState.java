package com.tst.automation.opcua.project.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import tk.mybatis.mapper.entity.IDynamicTableName;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Timestamp;

@Data
@Table(name = "conf_default")
public class OpcUaItemState implements IDynamicTableName {

    @Transient
    private String tableName;

    @Override
    public String getDynamicTableName() {
        return this.tableName;
    }

    @Id
    private String id;
    private Long itemId;
    private String itemValue;
    private Timestamp recordTime;

    @Transient
    @JsonIgnore
    private OpcUaItem opcUaItem;
}
