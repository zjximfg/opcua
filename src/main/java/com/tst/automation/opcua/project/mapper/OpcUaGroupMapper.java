package com.tst.automation.opcua.project.mapper;

import com.tst.automation.opcua.project.pojo.OpcUaGroup;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
@Component
public interface OpcUaGroupMapper extends Mapper<OpcUaGroup> {
}
