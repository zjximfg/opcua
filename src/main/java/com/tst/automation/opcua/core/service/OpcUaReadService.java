package com.tst.automation.opcua.core.service;

import com.tst.automation.opcua.core.pojo.OpcUaNamespace;
import com.tst.automation.opcua.project.pojo.*;
import com.tst.automation.opcua.project.service.OpcUaDataValueService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Data
public class OpcUaReadService implements Runnable {

    @Autowired
    private OpcUaNamespaceService opcUaNamespaceService;
    @Autowired
    private OpcUaDataValueService opcUaDataValueService;

    @Override
    public void run() {
        for (OpcUaServer opcUaServer : OpcUaTask.activeOpcUaServerList) {
            for (OpcUaConnection opcUaConnection : opcUaServer.getOpcUaConnectionList()) {
                OpcUaNamespace opcUaNamespace = opcUaNamespaceService.getOpcUaNamespaceById(opcUaConnection.getOpcUaNamespaceId());
                for (OpcUaGroup opcUaGroup : opcUaConnection.getOpcUaGroupList()) {
                    for (OpcUaItem opcUaItem : opcUaGroup.getOpcUaItemList()) {
                        OpcUaDataValue opcUaDataValue = opcUaDataValueService.readOpcUaDataValue(opcUaServer, opcUaNamespace.getNamespaceIndex(), opcUaItem.getIdentifier(), "String");
                        // 赋值 currentValue
                        opcUaItem.setOpcUaDataValue(opcUaDataValue);
                        opcUaItem.setCurrentValue(opcUaDataValue.getValue());
                        opcUaItem.setQuality(opcUaDataValue.getQuality());
                    }
                }
            }
        }
    }
}
