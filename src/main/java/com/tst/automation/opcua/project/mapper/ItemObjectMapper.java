package com.tst.automation.opcua.project.mapper;

import com.tst.automation.opcua.project.pojo.ItemObject;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
@Component
public interface ItemObjectMapper extends Mapper<ItemObject> {
}
