package com.tst.automation.opcua.core.service.impl;

import com.tst.automation.opcua.core.mapper.OpcUaProtocolMapper;
import com.tst.automation.opcua.core.pojo.OpcUaProtocol;
import com.tst.automation.opcua.core.service.OpcUaProtocolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpcUaProtocolServiceImpl implements OpcUaProtocolService {

    @Autowired
    private OpcUaProtocolMapper opcUaProtocolMapper;

    /**
     * 获取数据库中所有的协议类型 和 对应的端口号
     * @return 返回协议类型和端口号的封装对象
     */
    @Override
    public List<OpcUaProtocol> getAllOpcUaProtocol() {
        return opcUaProtocolMapper.selectAll();
    }

    /**
     * 根据id主键获取数据库中的 opc project protocol 对象
     * @param id 主键
     * @return opc project protocol 对象
     */
    @Override
    public OpcUaProtocol getOpcUaProtocolById(Long id) {
        return opcUaProtocolMapper.selectByPrimaryKey(id);
    }
}
