package com.tst.automation.opcua.project.service;

import com.tst.automation.opcua.project.pojo.ItemCategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemCategoryService {

    ItemCategory getItemCategoryById(Long itemCategoryId);

    List<ItemCategory> getItemCategoryListByOpcUaNamespaceId(Long opcUaNamespaceId);
}
