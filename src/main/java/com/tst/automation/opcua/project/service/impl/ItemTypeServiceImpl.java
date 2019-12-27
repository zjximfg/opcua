package com.tst.automation.opcua.project.service.impl;

import com.tst.automation.opcua.project.mapper.ItemTypeMapper;
import com.tst.automation.opcua.project.pojo.ItemType;
import com.tst.automation.opcua.project.service.ItemTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemTypeServiceImpl implements ItemTypeService {

    @Autowired
    private ItemTypeMapper itemTypeMapper;

    @Override
    public ItemType getItemTypeById(Long itemTypeId) {
        return itemTypeMapper.selectByPrimaryKey(itemTypeId);
    }

    @Override
    public List<ItemType> getItemTypeListByOpcUaNamespaceId(Long opcUaNamespaceId) {
        ItemType itemType = new ItemType();
        itemType.setOpcUaNamespaceId(opcUaNamespaceId);
        return itemTypeMapper.select(itemType);
    }
}
