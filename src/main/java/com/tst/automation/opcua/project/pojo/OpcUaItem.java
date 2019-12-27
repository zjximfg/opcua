package com.tst.automation.opcua.project.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Table(name = "tbl_opc_ua_item")
public class OpcUaItem {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private Long itemCategoryId;        // 类别，S7Basic， S7Timer， S7Counter

    private String fullName;            // <opcUaConnection.opcUaNamespace.namespaceUri><opcUaConnection.name>.<itemObject.name>{dbNumber}.<address>,<itemType.name>{bitAddress/stringLength}{,quantity}

    private Long opcUaConnectionId;  //包括 1. 命名空间对象 其中包括uri， 2. connectionName

    private Long opcUaGroupId;

    private Long itemObjectId;  // 对象类型， 如 db， m， q， i， 等，根据namespace不同而不同。

    private Integer dbNumber;   // 仅当itemObject.name ==  "DB"时 有效

    private Integer address;    // 从 0 - 65534

    private Long itemTypeId;        // 包括

    private Integer bitAddress;     // 位（布尔）除区域内的字节偏移量外，还必须在字节中指定 <位地址>。值范围是 0 到 7  仅当 itemType.name == "x"时有效

    private Integer stringLength;   // 仅当 itemType.name == "s"时有效， 必须指定为字符串保留的 <stringlength>。值范围是 1 到 254写入时还可写入较短的字符串，使传送的数据长度始终为保留的字符串长度（字节）加 2个字节。 不必要的字节用值 0 填充。读写字符串和字符串数组在内部映射到读写字节数组。字符串必须以 S7 上的有效值初始化。

    private Integer quantity = 1;   // 长度，数量, 默认值为1

    private String description;     // 描述， 用于显示变量的意义

    private Boolean isArray = false; // 数组标识位，如果为true则需要添加长度， 并且返回值为[]

    private Boolean isDeleted = false;      // 删除标识位


    @Transient
    @JsonIgnore
    private OpcUaConnection opcUaConnection;

    @Transient
    @JsonIgnore
    private OpcUaGroup opcUaGroup;

    @Transient
    @JsonIgnore
    private ItemObject itemObject;

    @Transient
    @JsonIgnore
    private ItemType itemType;

    @Transient
    @JsonIgnore
    private ItemCategory itemCategory;

    @Transient
    private String currentValue;
}
