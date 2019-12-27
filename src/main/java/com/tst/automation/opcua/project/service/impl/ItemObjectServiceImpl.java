package com.tst.automation.opcua.project.service.impl;

import com.tst.automation.opcua.project.mapper.ItemObjectMapper;
import com.tst.automation.opcua.project.pojo.ItemObject;
import com.tst.automation.opcua.project.service.ItemObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemObjectServiceImpl implements ItemObjectService {

    @Autowired
    private ItemObjectMapper itemObjectMapper;

    @Override
    public ItemObject getItemObjectById(Long itemObjectId) {
        return itemObjectMapper.selectByPrimaryKey(itemObjectId);
    }

    @Override
    public List<ItemObject> getItemObjectListByOpcUaNamespaceId(Long opcUaNamespaceId) {
        ItemObject itemObject = new ItemObject();
        itemObject.setOpcUaNamespaceId(opcUaNamespaceId);
        return itemObjectMapper.select(itemObject);
    }
}
