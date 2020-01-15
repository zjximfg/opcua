package com.tst.automation.opcua.project.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Timestamp;

@Data
@Table(name = "tbl_opc_ua_alarm_state")
public class AlarmState{
    @Id
    private String id;
    private Long alarmId;
    private Boolean isComing;
    private Boolean isLeaving;
    private Timestamp recordTime;

    @Transient
    @JsonIgnore
    private Alarm alarm;
}
