package com.tst.automation.opcua.core.service;

import com.tst.automation.opcua.project.pojo.*;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Data
public class OpcUaReadBufferService implements Runnable{

    @Override
    public void run() {
        for (OpcUaServer opcUaServer : OpcUaTask.activeOpcUaServerList) {
            for (OpcUaConnection opcUaConnection : opcUaServer.getOpcUaConnectionList()) {
                for (OpcUaGroup opcUaGroup : opcUaConnection.getOpcUaGroupList()) {
                    for (OpcUaItem opcUaItem : opcUaGroup.getOpcUaItemList()) {
                        OpcUaDataValue opcUaDataValue = opcUaItem.getOpcUaDataValue();
                        // 赋值 currentValue
                        // 获取缓存
                        if (opcUaItem.getOpcUaDataValueList() == null ) {
                            opcUaItem.setOpcUaDataValueList(new ArrayList<>());
                        }
                        if (opcUaItem.getOpcUaDataValueList().size() > 60) {
                            opcUaItem.getOpcUaDataValueList().remove(0);
                        }
                        opcUaItem.getOpcUaDataValueList().add(opcUaDataValue);
                    }
                }
            }
        }

    }
}
