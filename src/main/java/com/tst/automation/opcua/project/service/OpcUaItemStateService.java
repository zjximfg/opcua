package com.tst.automation.opcua.project.service;

import com.tst.automation.opcua.project.pojo.OpcUaItem;
import org.springframework.stereotype.Service;

@Service
public interface OpcUaItemStateService {

    void createNewTable(Long id);

    void dataPersistenceByOpcUaItem(OpcUaItem opcUaItem);
}
