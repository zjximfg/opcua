package com.tst.automation.opcua.project.service;

import com.tst.automation.opcua.project.pojo.OpcUaItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OpcUaItemService {

    String createFullName(OpcUaItem opcUaItem);

    List<OpcUaItem> getOpcUaItemListByGroupId(Long opcUaGroupId);
}
