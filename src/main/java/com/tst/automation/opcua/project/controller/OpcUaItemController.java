package com.tst.automation.opcua.project.controller;

import com.tst.automation.opcua.project.pojo.*;
import com.tst.automation.opcua.project.service.ItemCategoryService;
import com.tst.automation.opcua.project.service.ItemObjectService;
import com.tst.automation.opcua.project.service.ItemTypeService;
import com.tst.automation.opcua.project.service.OpcUaItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("project/opcUaItem")
public class OpcUaItemController {

    @Autowired
    private OpcUaItemService opcUaItemService;
    @Autowired
    private ItemCategoryService itemCategoryService;
    @Autowired
    private ItemObjectService itemObjectService;
    @Autowired
    private ItemTypeService itemTypeService;

    @GetMapping("list")
    public ResponseEntity<List<OpcUaItem>> getOpcUaItemListByGroupId(@RequestParam("opcUaGroupId") Long opcUaGroupId) {
        List<OpcUaItem> opcUaItemList = opcUaItemService.getOpcUaItemListByGroupId(opcUaGroupId);
        return ResponseEntity.ok(opcUaItemList);
    }

    @GetMapping("itemCategory/list")
    public ResponseEntity<List<ItemCategory>> getItemCategoryListByOpcUaNamespaceId(@RequestParam("opcUaNamespaceId") Long opcUaNamespaceId) {
        List<ItemCategory> itemCategoryList = itemCategoryService.getItemCategoryListByOpcUaNamespaceId(opcUaNamespaceId);
        return ResponseEntity.ok(itemCategoryList);
    }

    @GetMapping("itemObject/list")
    public ResponseEntity<List<ItemObject>> getItemObjectListByOpcUaNamespaceId(@RequestParam("opcUaNamespaceId") Long opcUaNamespaceId) {
        List<ItemObject> itemObjectList = itemObjectService.getItemObjectListByOpcUaNamespaceId(opcUaNamespaceId);
        return ResponseEntity.ok(itemObjectList);
    }

    @GetMapping("itemType/list")
    public ResponseEntity<List<ItemType>> getItemTypeListByOpcUaNamespaceId(@RequestParam("opcUaNamespaceId") Long opcUaNamespaceId) {
        List<ItemType> itemTypeList = itemTypeService.getItemTypeListByOpcUaNamespaceId(opcUaNamespaceId);
        return ResponseEntity.ok(itemTypeList);
    }

    @PutMapping
    public ResponseEntity<Void> updateOpcUaItem(@RequestBody OpcUaItem opcUaItem) {
        opcUaItemService.updateOpcUaItem(opcUaItem);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> createOpcUaItem(@RequestBody OpcUaItem opcUaItem) {
        opcUaItemService.createOpcUaItem(opcUaItem);
        return ResponseEntity.ok().build();
    }

    @GetMapping("opcUaDataValue/list")
    public ResponseEntity<List<OpcUaDataValue>> getBufferByOpcUaItemId(@RequestParam("opcUaItemId") Long opcUaItemId) {
        List<OpcUaDataValue> opcUaDataValueList = opcUaItemService.getBufferByOpcUaItemId(opcUaItemId);
        return ResponseEntity.ok(opcUaDataValueList);
    }

    @GetMapping("opcUaDataValue")
    public ResponseEntity<OpcUaDataValue> getOpcUaDataValueByOpcUaItemId(@RequestParam("opcUaItemId") Long opcUaItemId) {
        OpcUaDataValue opcUaDataValue = opcUaItemService.getOpcUaDataValueByOpcUaItemId(opcUaItemId);
        return ResponseEntity.ok(opcUaDataValue);
    }

    @GetMapping("list/online")
    public ResponseEntity<List<OpcUaItem>> getOpcUaItemListOnlineByGroupId(@RequestParam("opcUaGroupId") Long opcUaGroupId) {
        List<OpcUaItem> opcUaItemList = opcUaItemService.getOpcUaItemListOnlineByGroupId(opcUaGroupId);
        return ResponseEntity.ok(opcUaItemList);
    }
}
