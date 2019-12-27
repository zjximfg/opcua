package com.tst.automation.opcua.project.service;

import com.tst.automation.opcua.project.pojo.ItemObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemObjectService {

    ItemObject getItemObjectById(Long itemObjectId);

    List<ItemObject> getItemObjectListByOpcUaNamespaceId(Long opcUaNamespaceId);
}
