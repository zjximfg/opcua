package com.tst.automation.opcua.sms.alarm.mapper;

import com.tst.automation.opcua.sms.alarm.pojo.Role;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
@Component
public interface RoleMapper extends Mapper<Role> {
}
