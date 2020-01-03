package com.tst.automation.opcua.project.mapper;

import com.tst.automation.opcua.project.pojo.OpcUaItemState;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface OpcUaItemStateMapper extends MyExtendMapper<OpcUaItemState> {
    // xml 查询表是否存在
    Integer existTable(@Param("tableName")String tableName);
    // xml 创建新表方法
    void createNewTable(@Param("tableName")String tableName);
}
