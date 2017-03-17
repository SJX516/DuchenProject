package com.duchen.template.example.request.result;


import com.duchen.template.concept.model.LegalModel;

import java.util.List;


public class LoadDataResult implements LegalModel {

    private int pageIndex;
    private int pageSize;
    private int totalPageCount;

    private List<DataDto> dataList;

    public List<DataDto> getDataList() {
        return dataList;
    }

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
        if (dataList == null) {
            return false;
        }
        return true;
    }
}
