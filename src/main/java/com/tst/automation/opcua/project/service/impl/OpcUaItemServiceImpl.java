package com.tst.automation.opcua.project.service.impl;

import com.tst.automation.opcua.core.service.OpcUaNamespaceService;
import com.tst.automation.opcua.project.mapper.OpcUaItemMapper;
import com.tst.automation.opcua.project.pojo.OpcUaItem;
import com.tst.automation.opcua.project.service.*;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpcUaItemServiceImpl implements OpcUaItemService {

    @Autowired
    private OpcUaNamespaceService opcUaNamespaceService;
    @Autowired
    private OpcUaConnectionService opcUaConnectionService;
    @Autowired
    private ItemObjectService itemObjectService;
    @Autowired
    private ItemTypeService itemTypeService;
    @Autowired
    private ItemCategoryService itemCategoryService;
    @Autowired
    private OpcUaItemMapper opcUaItemMapper;

    @Override
    public String createFullName(OpcUaItem opcUaItem) {
        // <opcUaConnection.opcUaNamespace.namespaceUri><opcUaConnection.name>.<itemObject.name>{dbNumber}.<address>,<itemType.name>{bitAddress/stringLength}{,quantity}
        // 1. 获得opcUaConnection
        opcUaItem.setOpcUaConnection(opcUaConnectionService.getOpcUaConnectionById(opcUaItem.getOpcUaConnectionId()));
        // 2. 获取opcUaConnection.opcUaNamespace
        opcUaItem.getOpcUaConnection().setOpcUaNamespace(opcUaNamespaceService.getOpcUaNamespaceById(opcUaItem.getOpcUaConnection().getOpcUaNamespaceId()));
        // 3. 获取itemObject
        opcUaItem.setItemObject(itemObjectService.getItemObjectById(opcUaItem.getItemObjectId()));
        // 4. 获取itemType
        opcUaItem.setItemType(itemTypeService.getItemTypeById(opcUaItem.getItemTypeId()));
        // 5. 获取itemCategory
        opcUaItem.setItemCategory(itemCategoryService.getItemCategoryById(opcUaItem.getItemCategoryId()));

        // 判断是否为空？
        if (!ObjectUtils.allNotNull(opcUaItem.getOpcUaConnection(), opcUaItem.getOpcUaConnection().getOpcUaNamespace(), opcUaItem.getItemObject(), opcUaItem.getItemType(), opcUaItem.getItemCategory())) return "";
        //
        // 组合字符串
        StringBuilder stringBuilder = new StringBuilder();
        // <opcUaConnection.opcUaNamespace.namespaceUri>
        stringBuilder.append(opcUaItem.getOpcUaConnection().getOpcUaNamespace().getNamespaceUri());
        // <opcUaConnection.name>
        stringBuilder.append(opcUaItem.getOpcUaConnection().getConnectionName());
        // .<itemObject.name>
        stringBuilder.append(".").append(opcUaItem.getItemObject().getName());
        // 判断是否itemObject.name ==  "db"， 如果是，添加{dbNumber}
        if (opcUaItem.getItemObject().getName().equals("db")) {
            stringBuilder.append(opcUaItem.getDbNumber());
        }
        // .<address>
        stringBuilder.append(".").append(opcUaItem.getAddress());
        // ,<itemType.s7name>
        stringBuilder.append(",").append(opcUaItem.getItemType().getS7Name());
        // 判断是否itemType.name == "x"， 如果是，添加{bitAddress}
        if (opcUaItem.getItemType().getS7Name().equals("x")) {
            stringBuilder.append(opcUaItem.getBitAddress());
        }
        // 判断是否itemType.name == "s"， 如果是，添加{stringLength}
        if (opcUaItem.getItemType().getS7Name().equals("s")) {
            stringBuilder.append(opcUaItem.getStringLength());
        }
        // 判断是否itemType.name ！== "tda"，如果是，添加{,quantity}
        if (!opcUaItem.getItemType().getS7Name().equals("tda") && opcUaItem.getIsArray()) {
            stringBuilder.append(",").append(opcUaItem.getQuantity());
        }
        return stringBuilder.toString();
    }

    @Override
    public List<OpcUaItem> getOpcUaItemListByGroupId(Long opcUaGroupId) {
        OpcUaItem opcUaItem = new OpcUaItem();
        opcUaItem.setIsDeleted(false);
        opcUaItem.setOpcUaGroupId(opcUaGroupId);
        return opcUaItemMapper.select(opcUaItem);
    }


}
