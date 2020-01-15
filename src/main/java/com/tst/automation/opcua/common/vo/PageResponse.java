package com.tst.automation.opcua.common.vo;

import lombok.Data;

import java.util.List;

@Data
public class PageResponse<T> {
    private Long total;
    private List<T> list;

    public PageResponse(Long total, List<T> list) {
        this.total = total;
        this.list = list;
    }
}
