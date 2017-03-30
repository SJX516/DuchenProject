package com.duchen.template.example.model.impl;

import com.duchen.template.example.model.Container;
import com.duchen.template.example.model.Item;
import com.duchen.template.example.request.result.DataDto;
import com.duchen.template.example.request.result.LoadDataResult;
import com.duchen.template.example.request.result.Pagination;

import java.util.ArrayList;
import java.util.List;

public class ContainerImpl implements Container {

    private long mId;
    private String mDesc;
    private String mName;
    private List<Item> mItemList;
    private Pagination mPagination;


    public ContainerImpl(LoadDataResult loadDataResult) {
        mPagination = loadDataResult.getPagination();
        mId = loadDataResult.getId();
        mDesc = loadDataResult.getDesc();
        mName = loadDataResult.getName();
        mItemList = buildItemList(loadDataResult.getDataList());
    }

    private List<Item> buildItemList(List<DataDto> dataDtos) {
        List<Item> itemList = new ArrayList<>();
        for (DataDto dataDto : dataDtos) {
            Item item = new ItemImpl(dataDto);
            itemList.add(item);
        }
        return itemList;
    }

    @Override
    public Pagination getPagination() {
        return mPagination;
    }

    @Override
    public long getId() {
        return mId;
    }

    @Override
    public String getDesc() {
        return mDesc;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public List<Item> getItems() {
        return mItemList;
    }
}
