package com.tst.automation.opcua.core.service;

import com.tst.automation.opcua.project.pojo.OpcUaConnection;
import com.tst.automation.opcua.project.pojo.OpcUaGroup;
import com.tst.automation.opcua.project.pojo.OpcUaItem;
import com.tst.automation.opcua.project.pojo.OpcUaServer;
import com.tst.automation.opcua.project.service.OpcUaItemStateService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class OpcUaDataPersistence implements Runnable {

    @Autowired
    private OpcUaItemStateService opcUaItemStateService;

    private Long groupId;

    @Override
    public void run() {
        // TODO 判断服务器是否连接正常
        // 获取对应的opcUaGroup
        for (OpcUaServer opcUaServer : OpcUaTask.activeOpcUaServerList) {
            for (OpcUaConnection opcUaConnection : opcUaServer.getOpcUaConnectionList()) {
                for (OpcUaGroup opcUaGroup : opcUaConnection.getOpcUaGroupList()) {
                    if (opcUaGroup.getId().equals(groupId)) {
                        for (OpcUaItem opcUaItem : opcUaGroup.getOpcUaItemList()) {
                            if (opcUaItem.getQuality().equals("good")) {
                                // 入库
                                opcUaItemStateService.dataPersistenceByOpcUaItem(opcUaItem);
                            }
                        }
                    }
                }
            }
        }

    }
}
