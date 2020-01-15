package com.tst.automation.opcua.history.service;

import com.tst.automation.opcua.common.vo.PageResponse;
import com.tst.automation.opcua.history.vo.AlarmHistory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public interface AlarmHistoryService {
    PageResponse<AlarmHistory> getAlarmHistoryPage(Integer currentPage, Integer pageSize, Long alarmId, String alarmState, Timestamp startTime, Timestamp endTime);
}
