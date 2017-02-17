package com.duchen.template.example.request.result;


import com.duchen.template.concept.model.LegalModel;

import java.util.List;


public class LoadDataResult implements LegalModel {

    private List<DataDto> dataList;

    public List<DataDto> getDataList() {
        return dataList;
    }

    @Override
    public boolean check() {
        if (dataList == null) {
            return false;
        }
        return true;
    }
}
