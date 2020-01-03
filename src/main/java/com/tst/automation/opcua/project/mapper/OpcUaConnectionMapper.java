package com.tst.automation.opcua.project.mapper;

import com.tst.automation.opcua.project.pojo.OpcUaConnection;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
@Component
public interface OpcUaConnectionMapper extends Mapper<OpcUaConnection> {
}
