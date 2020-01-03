package com.tst.automation.opcua.project.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface MyExtendMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
