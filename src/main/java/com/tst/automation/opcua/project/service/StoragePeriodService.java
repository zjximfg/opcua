package com.tst.automation.opcua.project.service;

import com.tst.automation.opcua.project.pojo.StoragePeriod;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StoragePeriodService {

    List<StoragePeriod> getAllStoragePeriodList();
}
