package com.tst.automation.opcua.history.controller;

import com.tst.automation.opcua.common.vo.PageResponse;
import com.tst.automation.opcua.history.service.OpcUaItemHistoryService;
import com.tst.automation.opcua.history.vo.OpcUaItemHistory;
import com.tst.automation.opcua.project.pojo.OpcUaConnection;
import com.tst.automation.opcua.project.pojo.OpcUaGroup;
import com.tst.automation.opcua.project.pojo.OpcUaItem;
import com.tst.automation.opcua.project.service.OpcUaConnectionService;
import com.tst.automation.opcua.project.service.OpcUaGroupService;
import com.tst.automation.opcua.project.service.OpcUaItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("history/itemHistory")
public class OpcUaItemHistoryController {

    @Autowired
    private OpcUaConnectionService opcUaConnectionService;
    @Autowired
    private OpcUaGroupService opcUaGroupService;
    @Autowired
    private OpcUaItemService opcUaItemService;
    @Autowired
    private OpcUaItemHistoryService opcUaItemHistoryService;

    @GetMapping("opcUaConnection/list")
    public ResponseEntity<List<OpcUaConnection>> getAllOpcUaConnection() {
        List<OpcUaConnection> opcUaConnectionList = opcUaConnectionService.getAllOpcUaConnection();
        return ResponseEntity.ok(opcUaConnectionList);
    }

    @GetMapping("opcUaGroup/list")
    public ResponseEntity<List<OpcUaGroup>> getAllOpcUaGroup() {
        List<OpcUaGroup> opcUaGroupList = opcUaGroupService.getAllOpcUaGroup();
        return ResponseEntity.ok(opcUaGroupList);
    }

    @GetMapping("opcUaItem/list")
    public ResponseEntity<List<OpcUaItem>> getOpcUaItemByGroupId(@RequestParam("opcUaGroupId") Long opcUaGroupId) {
        List<OpcUaItem> opcUaItemList = opcUaItemService.getOpcUaItemListByGroupId(opcUaGroupId);
        return ResponseEntity.ok(opcUaItemList);
    }

    @GetMapping("page")
    public ResponseEntity<PageResponse<OpcUaItemHistory>> getOpcUaItemHistoryPage(@RequestParam("currentPage") Integer currentPage,
                                                                                  @RequestParam("pageSize") Integer pageSize,
                                                                                  @RequestParam("itemId") Long opcUaItemId,
                                                                                  @RequestParam(name = "startTime") Timestamp startTime,
                                                                                  @RequestParam("endTime") Timestamp endTime) {
        PageResponse<OpcUaItemHistory> pageResponse = opcUaItemHistoryService.getOpcUaItemHistoryPage(currentPage, pageSize, opcUaItemId, startTime, endTime);
        return ResponseEntity.ok(pageResponse);
    }
}
