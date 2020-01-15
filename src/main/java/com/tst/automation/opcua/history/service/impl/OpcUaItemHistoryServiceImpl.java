package com.tst.automation.opcua.history.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tst.automation.opcua.common.vo.PageResponse;
import com.tst.automation.opcua.history.service.OpcUaItemHistoryService;
import com.tst.automation.opcua.history.vo.OpcUaItemHistory;
import com.tst.automation.opcua.project.mapper.OpcUaItemStateMapper;
import com.tst.automation.opcua.project.pojo.OpcUaItem;
import com.tst.automation.opcua.project.pojo.OpcUaItemState;
import com.tst.automation.opcua.project.service.OpcUaItemService;
import com.tst.automation.opcua.project.service.impl.OpcUaItemStateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class OpcUaItemHistoryServiceImpl implements OpcUaItemHistoryService {

    @Autowired
    private OpcUaItemService opcUaItemService;
    @Autowired
    private OpcUaItemStateMapper opcUaItemStateMapper;

    @Override
    public PageResponse<OpcUaItemHistory> getOpcUaItemHistoryPage(Integer currentPage, Integer pageSize, Long opcUaItemId, Timestamp startTime, Timestamp endTime) {
        // 获取item
        OpcUaItem opcUaItem = opcUaItemService.getOpcUaItemById(opcUaItemId);

        PageHelper.startPage(currentPage, pageSize);
        Condition condition = new Condition(OpcUaItemState.class);
        condition.setTableName(OpcUaItemStateServiceImpl.BASE_TABLE_NAME + opcUaItemId);
        condition.createCriteria().andBetween("recordTime", startTime, endTime);
        condition.setOrderByClause("record_time" + " DESC");

        Page<OpcUaItemState> itemStatePage = (Page<OpcUaItemState>)opcUaItemStateMapper.selectByExample(condition);

        List<OpcUaItemState> opcUaItemStateList = itemStatePage.getResult();

        // 整理成对应的vo
        List<OpcUaItemHistory> opcUaItemHistoryList = new ArrayList<>();
        for (OpcUaItemState opcUaItemState : opcUaItemStateList) {
            OpcUaItemHistory opcUaItemHistory = new OpcUaItemHistory();
            opcUaItemHistory.setId(opcUaItemState.getId());
            opcUaItemHistory.setItemId(opcUaItemId);
            opcUaItemHistory.setItemName(opcUaItem.getFullName());
            opcUaItemHistory.setDescription(opcUaItem.getDescription());
            opcUaItemHistory.setItemValue(opcUaItemState.getItemValue());
            opcUaItemHistory.setRecordTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(opcUaItemState.getRecordTime()));
            opcUaItemHistoryList.add(opcUaItemHistory);
        }
        return new PageResponse<>(itemStatePage.getTotal(), opcUaItemHistoryList);
    }
}
