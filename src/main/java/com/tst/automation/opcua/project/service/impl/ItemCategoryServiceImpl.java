package com.tst.automation.opcua.project.service.impl;

import com.tst.automation.opcua.project.mapper.ItemCategoryMapper;
import com.tst.automation.opcua.project.pojo.ItemCategory;
import com.tst.automation.opcua.project.service.ItemCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemCategoryServiceImpl implements ItemCategoryService {

    @Autowired
    private ItemCategoryMapper itemCategoryMapper;

    @Override
    public ItemCategory getItemCategoryById(Long itemCategoryId) {
        return itemCategoryMapper.selectByPrimaryKey(itemCategoryId);
    }

    @Override
    public List<ItemCategory> getItemCategoryListByOpcUaNamespaceId(Long opcUaNamespaceId) {
        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setOpcUaNamespaceId(opcUaNamespaceId);
        return itemCategoryMapper.select(itemCategory);
    }
}
