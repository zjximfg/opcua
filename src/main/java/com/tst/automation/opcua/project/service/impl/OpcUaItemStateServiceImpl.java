package com.tst.automation.opcua.project.service.impl;

import com.tst.automation.opcua.project.mapper.OpcUaItemStateMapper;
import com.tst.automation.opcua.project.pojo.OpcUaItem;
import com.tst.automation.opcua.project.pojo.OpcUaItemState;
import com.tst.automation.opcua.project.service.OpcUaItemStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OpcUaItemStateServiceImpl implements OpcUaItemStateService {

    @Autowired
    private OpcUaItemStateMapper opcUaItemStateMapper;

    public final static String BASE_TABLE_NAME = "tbl_opc_ua_item_state_";

    @Override
    public void createNewTable(Long id) {
        // 生成数据库的表名
        String tableName = BASE_TABLE_NAME + id;
        opcUaItemStateMapper.createNewTable(tableName);
    }

    @Override
    public void dataPersistenceByOpcUaItem(OpcUaItem opcUaItem) {
        // 生成数据库的表名
        String tableName = BASE_TABLE_NAME + opcUaItem.getId();
        // 创建opcUaItem对应的opcUaItemState
        OpcUaItemState opcUaItemState = new OpcUaItemState();
        opcUaItemState.setId(UUID.randomUUID().toString());
        opcUaItemState.setItemId(opcUaItem.getId());
        opcUaItemState.setItemValue(opcUaItem.getCurrentValue());
        opcUaItemState.setOpcUaItem(opcUaItem);
        opcUaItemState.setTableName(tableName);
        System.out.println("入库数据：");
        System.out.println(opcUaItemState);
        try {
            opcUaItemStateMapper.insertSelective(opcUaItemState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
