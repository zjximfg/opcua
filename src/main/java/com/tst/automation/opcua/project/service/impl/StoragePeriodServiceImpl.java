package com.tst.automation.opcua.project.service.impl;

import com.tst.automation.opcua.project.mapper.StoragePeriodMapper;
import com.tst.automation.opcua.project.pojo.StoragePeriod;
import com.tst.automation.opcua.project.service.StoragePeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoragePeriodServiceImpl implements StoragePeriodService {

    @Autowired
    private StoragePeriodMapper storagePeriodMapper;

    @Override
    public List<StoragePeriod> getAllStoragePeriodList() {

        return storagePeriodMapper.selectAll();
    }
}
