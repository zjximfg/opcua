package com.tst.automation.opcua.project.mapper;

import com.tst.automation.opcua.project.pojo.AlarmState;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
@Component
public interface AlarmStateMapper extends Mapper<AlarmState> {
}
