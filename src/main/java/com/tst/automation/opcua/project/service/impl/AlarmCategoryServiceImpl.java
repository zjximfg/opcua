package com.tst.automation.opcua.project.service.impl;

import com.tst.automation.opcua.project.mapper.AlarmCategoryMapper;
import com.tst.automation.opcua.project.pojo.AlarmCategory;
import com.tst.automation.opcua.project.service.AlarmCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmCategoryServiceImpl implements AlarmCategoryService {

    @Autowired
    private AlarmCategoryMapper alarmCategoryMapper;

    @Override
    public List<AlarmCategory> getAlarmCategoryList() {
        AlarmCategory alarmCategory = new AlarmCategory();
        alarmCategory.setIsDeleted(false);
        return alarmCategoryMapper.select(alarmCategory);
    }

    @Override
    public void deleteAlarmCategory(Long id) {
        AlarmCategory alarmCategory = new AlarmCategory();
        alarmCategory.setId(id);
        alarmCategory.setIsDeleted(true);
        alarmCategoryMapper.updateByPrimaryKeySelective(alarmCategory);
    }

    @Override
    public void updateAlarmCategory(AlarmCategory alarmCategory) {
        alarmCategory.setIsDeleted(false);
        alarmCategoryMapper.updateByPrimaryKeySelective(alarmCategory);
    }

    @Override
    public void createAlarmCategory(AlarmCategory alarmCategory) {
        alarmCategory.setIsDeleted(false);
        alarmCategoryMapper.insertSelective(alarmCategory);
    }
}
