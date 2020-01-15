package com.tst.automation.opcua.project.service.impl;

import com.tst.automation.opcua.core.service.OpcUaNamespaceService;
import com.tst.automation.opcua.core.service.OpcUaTask;
import com.tst.automation.opcua.project.mapper.OpcUaItemMapper;
import com.tst.automation.opcua.project.pojo.*;
import com.tst.automation.opcua.project.service.*;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    @Autowired
    private OpcUaItemStateService opcUaItemStateService;


    @Override
    public String createFullName(OpcUaItem opcUaItem) {
        // <opcUaConnection.opcUaNamespace.namespaceUri><opcUaConnection.name>.<itemObject.name>{dbNumber}.<address>,<itemType.name>{bitAddress/stringLength}{,quantity}
        // 1. 获得opcUaConnection
        opcUaItem.setOpcUaConnection(opcUaConnectionService.getOpcUaConnectionById(opcUaItem.getOpcUaConnectionId()));
        // 2. 获取opcUaConnection.opcUaNamespace
        opcUaItem.getOpcUaConnection().setOpcUaNamespace(opcUaNamespaceService.getOpcUaNamespaceById(opcUaItem.getOpcUaConnection().getOpcUaNamespaceId()));

        // 判断是否为空？
        if (!ObjectUtils.allNotNull(opcUaItem.getOpcUaConnection(), opcUaItem.getOpcUaConnection().getOpcUaNamespace())) return "";
        //
        // 组合字符串
        StringBuilder stringBuilder = new StringBuilder();
        // <opcUaConnection.opcUaNamespace.namespaceUri>
        stringBuilder.append(opcUaItem.getOpcUaConnection().getOpcUaNamespace().getNamespaceUri());

        stringBuilder.append(this.createIdentifier(opcUaItem));

        return stringBuilder.toString();
    }

    @Override
    public List<OpcUaItem> getOpcUaItemListByGroupId(Long opcUaGroupId) {
        OpcUaItem opcUaItem = new OpcUaItem();
        opcUaItem.setIsDeleted(false);
        opcUaItem.setOpcUaGroupId(opcUaGroupId);
        return opcUaItemMapper.select(opcUaItem);
    }

    @Override
    public void updateOpcUaItem(OpcUaItem opcUaItem) {
        String identifier = this.createIdentifier(opcUaItem);
        opcUaItem.setIdentifier(identifier);
        String fullName = this.createFullName(opcUaItem);
        System.out.println(fullName);
        opcUaItem.setFullName(fullName);
        opcUaItemMapper.updateByPrimaryKeySelective(opcUaItem);
    }

    @Override
    public void createOpcUaItem(OpcUaItem opcUaItem) {
        String identifier = this.createIdentifier(opcUaItem);
        opcUaItem.setIdentifier(identifier);
        String fullName = this.createFullName(opcUaItem);
        System.out.println(fullName);
        opcUaItem.setFullName(fullName);
        opcUaItem.setIsDeleted(false);
        opcUaItemMapper.insertSelective(opcUaItem);
        // 创建记录数据持久化的表
        opcUaItemStateService.createNewTable(opcUaItem.getId());
    }

    @Override
    public String createIdentifier(OpcUaItem opcUaItem) {
        opcUaItem.setOpcUaConnection(opcUaConnectionService.getOpcUaConnectionById(opcUaItem.getOpcUaConnectionId()));
        // 3. 获取itemObject
        opcUaItem.setItemObject(itemObjectService.getItemObjectById(opcUaItem.getItemObjectId()));
        // 4. 获取itemType
        opcUaItem.setItemType(itemTypeService.getItemTypeById(opcUaItem.getItemTypeId()));
        // 5. 获取itemCategory
        opcUaItem.setItemCategory(itemCategoryService.getItemCategoryById(opcUaItem.getItemCategoryId()));

        if (!ObjectUtils.allNotNull(opcUaItem.getItemObject(), opcUaItem.getItemType(), opcUaItem.getItemCategory())) return "";

        // 组合字符串
        StringBuilder stringBuilder = new StringBuilder();
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
    public List<OpcUaDataValue> getBufferByOpcUaItemId(Long opcUaItemId) {
        for (OpcUaServer opcUaServer : OpcUaTask.activeOpcUaServerList) {
            for (OpcUaConnection opcUaConnection : opcUaServer.getOpcUaConnectionList()) {
                for (OpcUaGroup opcUaGroup : opcUaConnection.getOpcUaGroupList()) {
                    for (OpcUaItem opcUaItem : opcUaGroup.getOpcUaItemList()) {
                        if (opcUaItem.getId().equals(opcUaItemId)) {
                            return opcUaItem.getOpcUaDataValueList();
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public OpcUaDataValue getOpcUaDataValueByOpcUaItemId(Long opcUaItemId) {
        for (OpcUaServer opcUaServer : OpcUaTask.activeOpcUaServerList) {
            for (OpcUaConnection opcUaConnection : opcUaServer.getOpcUaConnectionList()) {
                for (OpcUaGroup opcUaGroup : opcUaConnection.getOpcUaGroupList()) {
                    for (OpcUaItem opcUaItem : opcUaGroup.getOpcUaItemList()) {
                        if (opcUaItem.getId().equals(opcUaItemId)) {
                            return opcUaItem.getOpcUaDataValue();
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public List<OpcUaItem> getOpcUaItemListOnlineByGroupId(Long opcUaGroupId) {
        for (OpcUaServer opcUaServer : OpcUaTask.activeOpcUaServerList) {
            for (OpcUaConnection opcUaConnection : opcUaServer.getOpcUaConnectionList()) {
                for (OpcUaGroup opcUaGroup : opcUaConnection.getOpcUaGroupList()) {
                    if (opcUaGroup.getId().equals(opcUaGroupId)) {
                        return opcUaGroup.getOpcUaItemList();
                    }
                }
            }
        }
        return null;
    }

    @Override
    public List<ItemCurve> getItemCurveData(Long itemId) {
        List<OpcUaDataValue> opcUaDataValueList = this.getBufferByOpcUaItemId(itemId);
        List<ItemCurve> itemCurveList = new ArrayList<>();
        long i = 0;
        for (OpcUaDataValue opcUaDataValue : opcUaDataValueList) {
            ItemCurve itemCurve = new ItemCurve();
            itemCurve.setId(i);
            i++;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            itemCurve.setTime(simpleDateFormat.format(opcUaDataValue.getServerDateTime().getTime()));
            itemCurve.setValue(opcUaDataValue.getValue());
            itemCurveList.add(itemCurve);
        }
        return itemCurveList;
    }

    @Override
    public OpcUaItem getOpcUaItemById(Long opcUaItemId) {
        return opcUaItemMapper.selectByPrimaryKey(opcUaItemId);
    }


}
