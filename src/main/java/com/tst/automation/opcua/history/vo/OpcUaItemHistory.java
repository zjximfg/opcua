package com.tst.automation.opcua.history.vo;

import lombok.Data;

@Data
public class OpcUaItemHistory {

    private String id;    // state.id
    private Long itemId;    // state.itemId;
    private String itemName; // item.fullName;
    private String description; // item.description
    private String itemValue;   // state.itemValue
    private String recordTime; // state.recordTime;

}
