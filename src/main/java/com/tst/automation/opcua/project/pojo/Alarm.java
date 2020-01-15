package com.tst.automation.opcua.project.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Timestamp;

@Data
@Table(name = "tbl_opc_ua_alarm")
public class Alarm {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private Long itemCategoryId = 1L;        // 类别，S7Basic， S7Timer， S7Counter  // 目前仅支持S7 通讯

    private String identifier;

    private String fullName;            // <opcUaConnection.opcUaNamespace.namespaceUri><opcUaConnection.name>.<itemObject.name>{dbNumber}.<address>,<itemType.name>{bitAddress/stringLength}{,quantity}

    private Long opcUaConnectionId;  //包括 1. 命名空间对象 其中包括uri， 2. connectionName

    private Long itemObjectId;  // 对象类型， 如 db， m， q， i， 等，根据namespace不同而不同。

    private Integer dbNumber;   // 仅当itemObject.name ==  "DB"时 有效

    private Integer address;    // 从 0 - 65534

    private Integer bitAddress;     // 位（布尔）除区域内的字节偏移量外，还必须在字节中指定 <位地址>。值范围是 0 到 7  仅当 itemType.name == "x"时有效

    private String description;     // 描述， 用于显示变量的意义

    private Long alarmCategoryId;      // 报警类别

    private Long alarmLevelId;          // 报警级别

    private Boolean isDeleted = false;      // 删除标识位


    @Transient
    @JsonIgnore
    private OpcUaConnection opcUaConnection;

    @Transient
    @JsonIgnore
    private ItemObject itemObject;

    @Transient
    @JsonIgnore
    private ItemCategory itemCategory;

    @Transient
    private String currentValue;

    @Transient
    private Timestamp comingTime;

    @Transient
    private Timestamp leavingTime;

    @Transient
    private String lastCycleValue = "false";

    @Transient
    private String quality;


    @Transient
    @JsonIgnore
    private AlarmCategory alarmCategory;

    @Transient
    @JsonIgnore
    private AlarmLevel alarmLevel;

    @Transient
    @JsonIgnore
    private OpcUaDataValue opcUaDataValue;

}
