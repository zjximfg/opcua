package com.tst.automation.opcua.history.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.tst.automation.opcua.common.vo.PageResponse;
import com.tst.automation.opcua.core.service.OpcUaTask;
import com.tst.automation.opcua.history.service.AlarmHistoryService;
import com.tst.automation.opcua.history.vo.AlarmHistory;
import com.tst.automation.opcua.project.mapper.AlarmStateMapper;
import com.tst.automation.opcua.project.pojo.Alarm;
import com.tst.automation.opcua.project.pojo.AlarmState;
import com.tst.automation.opcua.project.pojo.OpcUaConnection;
import com.tst.automation.opcua.project.pojo.OpcUaServer;
import com.tst.automation.opcua.project.service.AlarmService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlarmHistoryServiceImpl implements AlarmHistoryService {

    @Autowired
    private AlarmService alarmService;
    @Autowired
    private AlarmStateMapper alarmStateMapper;

    @Override
    public PageResponse<AlarmHistory> getAlarmHistoryPage(Integer currentPage, Integer pageSize, Long alarmId, String alarmState, Timestamp startTime, Timestamp endTime) {
        // 分页
        PageHelper.startPage(currentPage, pageSize);
        Condition condition = new Condition(AlarmState.class);

        switch (alarmState) {
            case "Current":
                // 当前存在的报警, 仅此选项中alarmId无效
                return this.getCurrentAlarmList(currentPage, pageSize);
            case "Leaving":
                // isLeaving 为 true
                condition.createCriteria().andEqualTo("isLeaving", true).andBetween("recordTime", startTime, endTime);
                break;
            case "Coming":
                // isComing 为 true
                condition.createCriteria().andEqualTo("isComing", true).andBetween("recordTime", startTime, endTime);
                break;
            case "Both":
                // 仅，alarmId = alarmId
                condition.createCriteria().andBetween("recordTime", startTime, endTime);
                break;
        }

        if (ObjectUtils.allNotNull(alarmId) && !alarmId.equals(0L)) {
            condition.createCriteria().andEqualTo("alarmId", alarmId);
        }

        condition.setOrderByClause("record_time" + " DESC");

        Page<AlarmState> alarmStatePage = (Page<AlarmState>) alarmStateMapper.selectByExample(condition);

        List<AlarmState> alarmStateList = alarmStatePage.getResult();

        List<AlarmHistory> alarmHistoryList = new ArrayList<>();

        if (ObjectUtils.allNotNull(alarmStateList)) {
            // 封装 AlarmHistory
            for (AlarmState state : alarmStateList) {
                // 获取Alarm 数据
                Alarm alarm = alarmService.getAlarmById(state.getAlarmId());
                AlarmHistory alarmHistory = new AlarmHistory();
                alarmHistory.setAlarmId(alarmId);
                alarmHistory.setAlarmName(alarm.getFullName());
                alarmHistory.setDescription(alarm.getDescription());
                alarmHistory.setId(state.getId());
                alarmHistory.setIsComing(state.getIsComing());
                alarmHistory.setIsLeaving(state.getIsLeaving());
                alarmHistory.setRecordTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(state.getRecordTime()));
                alarmHistoryList.add(alarmHistory);
            }
        }

        return new PageResponse<>(alarmStatePage.getTotal(), alarmHistoryList);
    }

    private PageResponse<AlarmHistory> getCurrentAlarmList(Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<AlarmHistory> alarmHistoryList = new ArrayList<>();
        for (OpcUaServer opcUaServer : OpcUaTask.activeOpcUaServerList) {
            for (OpcUaConnection opcUaConnection : opcUaServer.getOpcUaConnectionList()) {
                for (Alarm alarm : opcUaConnection.getAlarmList()) {
                    if (alarm.getCurrentValue().equals("true")) {
                        AlarmHistory alarmHistory = new AlarmHistory();
                        alarmHistory.setId(alarm.getId().toString());
                        alarmHistory.setAlarmId(alarm.getId());
                        alarmHistory.setDescription(alarm.getDescription());
                        alarmHistory.setAlarmName(alarm.getFullName());
                        alarmHistory.setIsLeaving(false);
                        alarmHistory.setIsComing(true);
                        alarmHistory.setRecordTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(alarm.getComingTime()));
                        alarmHistoryList.add(alarmHistory);
                    }
                }
            }
        }

        long total = (alarmHistoryList.size() / pageSize) + ((alarmHistoryList.size() % pageSize > 0) ? 1 : 0);
        List<AlarmHistory> alarmHistories = new ArrayList<>();

        for (int i = 0, len = (int) total - 1; i <= len; i++) {
            int fromIndex = i * pageSize;
            int toIndex = ((i == len) ? alarmHistoryList.size() : ((i + 1) * pageSize));
            alarmHistories = alarmHistoryList.subList(fromIndex, toIndex);
        }

        return new PageResponse<>(total, alarmHistories);
    }

}
