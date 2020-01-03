package com.tst.automation.opcua.core.mapper;

import com.tst.automation.opcua.core.pojo.OpcUaProtocol;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
@Component
public interface OpcUaProtocolMapper extends Mapper<OpcUaProtocol> {
}
