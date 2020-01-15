package com.tst.automation.opcua.history.vo;

import lombok.Data;

@Data
public class AlarmHistory {

    private String id;
    private Long alarmId;
    private String alarmName;
    private String description;
    private Boolean isComing;
    private Boolean isLeaving;
    private String recordTime;
}
