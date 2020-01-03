package com.tst.automation.opcua.core.mapper;

import com.tst.automation.opcua.core.pojo.SecurityPolicyUri;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
@Component
public interface SecurityPolicyUriMapper extends Mapper<SecurityPolicyUri> {
}
