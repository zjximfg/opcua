package com.tst.automation.opcua.project.mapper;

import com.tst.automation.opcua.project.pojo.AlarmLevel;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@Component
@org.apache.ibatis.annotations.Mapper
public interface AlarmLevelMapper extends Mapper<AlarmLevel> {
}
