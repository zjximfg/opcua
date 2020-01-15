package com.tst.automation.opcua.history.controller;

import com.tst.automation.opcua.common.vo.PageResponse;
import com.tst.automation.opcua.history.service.AlarmHistoryService;
import com.tst.automation.opcua.history.vo.AlarmHistory;
import com.tst.automation.opcua.project.pojo.Alarm;
import com.tst.automation.opcua.project.pojo.OpcUaConnection;
import com.tst.automation.opcua.project.service.AlarmService;
import com.tst.automation.opcua.project.service.OpcUaConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("history/alarmHistory")
public class AlarmHistoryController {

    @Autowired
    private OpcUaConnectionService opcUaConnectionService;
    @Autowired
    private AlarmService alarmService;
    @Autowired
    private AlarmHistoryService alarmHistoryService;

    @GetMapping("opcUaConnection/list")
    public ResponseEntity<List<OpcUaConnection>> getAllOpcUaConnection() {
        List<OpcUaConnection> opcUaConnectionList = opcUaConnectionService.getAllOpcUaConnection();
        return ResponseEntity.ok(opcUaConnectionList);
    }

    @GetMapping("alarm/list")
    public ResponseEntity<List<Alarm>> getAlarmListByConnectionId(@RequestParam("opcUaConnectionId") Long opcUaConnectionId) {
        List<Alarm> alarmList = alarmService.getAlarmListByOpcUaConnectionId(opcUaConnectionId);
        return ResponseEntity.ok(alarmList);
    }

    @GetMapping("page")
    public ResponseEntity<PageResponse<AlarmHistory>> getAlarmHistoryPage(@RequestParam("currentPage") Integer currentPage,
                                                                          @RequestParam("pageSize") Integer pageSize,
                                                                          @RequestParam(name = "alarmId", required = false) Long alarmId,
                                                                          @RequestParam("alarmState") String alarmState,
                                                                          @RequestParam(name = "startTime") Timestamp startTime,
                                                                          @RequestParam("endTime") Timestamp endTime) {
        PageResponse<AlarmHistory> alarmHistoryPage = alarmHistoryService.getAlarmHistoryPage(currentPage, pageSize, alarmId, alarmState, startTime, endTime);
        return ResponseEntity.ok(alarmHistoryPage);
    }
}
