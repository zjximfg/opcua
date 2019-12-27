package com.tst.automation.opcua.project.controller;

import com.tst.automation.opcua.project.pojo.ItemCategory;
import com.tst.automation.opcua.project.pojo.ItemObject;
import com.tst.automation.opcua.project.pojo.ItemType;
import com.tst.automation.opcua.project.pojo.OpcUaItem;
import com.tst.automation.opcua.project.service.ItemCategoryService;
import com.tst.automation.opcua.project.service.ItemObjectService;
import com.tst.automation.opcua.project.service.ItemTypeService;
import com.tst.automation.opcua.project.service.OpcUaItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
