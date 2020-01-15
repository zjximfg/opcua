package com.tst.automation.opcua.project.service;

import com.tst.automation.opcua.project.pojo.ItemCurve;
import com.tst.automation.opcua.project.pojo.OpcUaDataValue;
import com.tst.automation.opcua.project.pojo.OpcUaItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OpcUaItemService {

    String createFullName(OpcUaItem opcUaItem);

    List<OpcUaItem> getOpcUaItemListByGroupId(Long opcUaGroupId);

    void updateOpcUaItem(OpcUaItem opcUaItem);

    void createOpcUaItem(OpcUaItem opcUaItem);

    String createIdentifier(OpcUaItem opcUaItem);

    List<OpcUaDataValue> getBufferByOpcUaItemId(Long opcUaItemId);

    OpcUaDataValue getOpcUaDataValueByOpcUaItemId(Long opcUaItemId);

    List<OpcUaItem> getOpcUaItemListOnlineByGroupId(Long opcUaGroupId);

    List<ItemCurve> getItemCurveData(Long itemId);

    OpcUaItem getOpcUaItemById(Long opcUaItemId);
}
