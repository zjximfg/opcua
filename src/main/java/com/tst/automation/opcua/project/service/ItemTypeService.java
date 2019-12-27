package com.tst.automation.opcua.project.service;

import com.tst.automation.opcua.project.pojo.ItemType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemTypeService {

    ItemType getItemTypeById(Long itemTypeId);

    List<ItemType> getItemTypeListByOpcUaNamespaceId(Long itemCategoryId);
}
