package com.duchen.template.example.request.result;


import com.duchen.template.component.model.LegalModel;
import com.duchen.template.utils.datahelper.DataCheckUtil;

import java.util.List;


public class LoadDataResult implements LegalModel {

    private Pagination pagination;

    private long id;
    private String name;
    private String desc;
    private List<DataDto> dataList;

    public List<DataDto> getDataList() {
        return dataList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public boolean check() {
        return DataCheckUtil.check(dataList) && DataCheckUtil.check(pagination);
    }
}
