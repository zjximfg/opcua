package com.tst.automation.opcua.history.service;

import com.tst.automation.opcua.common.vo.PageResponse;
import com.tst.automation.opcua.history.vo.OpcUaItemHistory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public interface OpcUaItemHistoryService {
    PageResponse<OpcUaItemHistory> getOpcUaItemHistoryPage(Integer currentPage, Integer pageSize, Long opcUaItemId, Timestamp startTime, Timestamp endTime);
}
