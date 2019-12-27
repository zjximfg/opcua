package com.tst.automation.opcua.core.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "tbl_opc_ua_protocol")
public class OpcUaProtocol {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    // 协议名称 如： S7
    private String protocolName;

//    // 对应的url
//    private String namespaceUrl;

    // 协议对应的端口号 如：55101
    private Integer port;
}
