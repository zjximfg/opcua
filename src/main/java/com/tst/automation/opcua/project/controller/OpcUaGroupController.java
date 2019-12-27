package com.tst.automation.opcua.project.controller;

import com.tst.automation.opcua.project.pojo.OpcUaGroup;
import com.tst.automation.opcua.project.pojo.StoragePeriod;
import com.tst.automation.opcua.project.service.OpcUaGroupService;
import com.tst.automation.opcua.project.service.StoragePeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("project/opcUaGroup")
public class OpcUaGroupController {

    @Autowired
    private StoragePeriodService storagePeriodService;
    @Autowired
    private OpcUaGroupService opcUaGroupService;


    @GetMapping("storagePeriod/list")
    public ResponseEntity<List<StoragePeriod>> getAllStoragePeriodList() {
        List<StoragePeriod> storagePeriodList = storagePeriodService.getAllStoragePeriodList();
        return ResponseEntity.ok(storagePeriodList);
    }

    @GetMapping("list")
    public ResponseEntity<List<OpcUaGroup>> getOpcUaGroupByConnectionId(@RequestParam("opcUaConnectionId") Long opcUaConnectionId) {
        List<OpcUaGroup> opcUaGroupList = opcUaGroupService.getOpcUaGroupByConnectionId(opcUaConnectionId);
        return ResponseEntity.ok(opcUaGroupList);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteOpcUaGroup(@RequestBody OpcUaGroup opcUaGroup) {
        opcUaGroupService.deleteOpcUaGroup(opcUaGroup.getId());
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateOpcUaGroup(@RequestBody OpcUaGroup opcUaGroup) {
        opcUaGroupService.updateOpcUaGroup(opcUaGroup);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> createOpcUaGroup(@RequestBody OpcUaGroup opcUaGroup) {
        opcUaGroupService.createOpcUaGroup(opcUaGroup);
        return ResponseEntity.ok().build();
    }
}
