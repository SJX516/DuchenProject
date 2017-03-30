package com.duchen.template.example.request.result;

import com.duchen.template.component.model.LegalModel;

public class Pagination implements LegalModel{

    private int pageIndex;
    private int pageSize;
    private int totalPageCount;

    public int getPageIndex() {
        return pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    @Override
    public boolean check() {
        return true;
    }
}
