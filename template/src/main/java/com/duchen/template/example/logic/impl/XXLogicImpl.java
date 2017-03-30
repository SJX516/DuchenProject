package com.duchen.template.example.logic.impl;

import android.content.Context;
import android.os.Handler;

import com.duchen.template.component.LogicBase;
import com.duchen.template.concept.IViewModel;
import com.duchen.template.example.datasource.XXDataSource;
import com.duchen.template.example.datasource.impl.XXDataSourceImpl;
import com.duchen.template.example.logic.XXLogic;
import com.duchen.template.example.model.Container;
import com.duchen.template.example.model.Item;
import com.duchen.template.example.request.result.DataDto;
import com.duchen.template.example.request.result.LoadDataResult;
import com.duchen.template.example.box.XXListBox;
import com.duchen.template.example.box.model.YYItemData;
import com.duchen.template.example.box.model.ZZItemData;
import com.duchen.template.utils.datahelper.DataCheckUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class XXLogicImpl extends LogicBase implements XXLogic, XXListBox.ViewModel, XXDataSource.OnDataResultListener {

    private XXDataSource mDataSource;
    private List<IViewModel> mItems = new ArrayList<>();
    private Container mLatestContainer;

    public XXLogicImpl(Context context, Handler handler) {
        super(context, handler);
        mDataSource = new XXDataSourceImpl();
        mDataSource.setLoadDataListener(this);
    }

    @Override
    public void loadData(boolean isRefresh) {
        if (isRefresh) {
            mItems.clear();
        }
        mDataSource.loadData(isRefresh);
    }

    @Override
    public void onLoadDataFinish(boolean isSuccess, Container container) {
        if (isSuccess) {
            if (container == null) {
                if (mItems.isEmpty()) {
                    notifyUi(MSG_LOAD_DATA_NO_CONTENT);
                } else {
                    notifyUi(MSG_LOAD_MORE_FAILED);
                }
            } else {
                mLatestContainer = container;
                mItems.addAll(buildItems(container));
                notifyUi(MSG_LOAD_DATA_SUCCESS);
            }
        } else {
            if (mItems.isEmpty()) {
                notifyUi(MSG_LOAD_DATA_ERROR);
            } else {
                notifyUi(MSG_LOAD_MORE_FAILED);
            }
        }

    }

    public XXListBox.ViewModel getViewModel() {
        return this;
    }

    @Override
    public Collection<IViewModel> getItems() {
        return mItems;
    }

    @Override
    public boolean canLoadMore() {
        return mDataSource.canLoadMore();
    }

    @Override
    public boolean canPullToRefresh() {
        return true;
    }

    private List<IViewModel> buildItems(Container container) {
        List<IViewModel> list = new ArrayList<>();
        for (Item item : container.getItems()) {
            switch (item.getItemType()) {
                case TYPE_YY_ITEM:
                    YYItemData yyItemData = new YYItemData();
                    yyItemData.setSelected(false);
                    yyItemData.setTitle(item.getTitle());
                    yyItemData.setDescription(item.getDesc());
                    if (list.size() == container.getItems().size() - 1) {
                        yyItemData.setShowSomething(false);
                    } else {
                        yyItemData.setShowSomething(true);
                    }
                    list.add(yyItemData);
                    break;
                case TYPE_ZZ_ITEM:
                    ZZItemData zzItemData = new ZZItemData();
                    zzItemData.setImageUrl(item.getImgUrl());
                    list.add(zzItemData);
                    break;
            }
        }
        return list;
    }

    @Override
    public void onClickYYItem(YYItemData yyItemData) {
        for (IViewModel model : mItems) {
            if (model instanceof YYItemData) {
                YYItemData itemData = (YYItemData) model;
                if (itemData.equals(yyItemData)) {
                    itemData.setSelected(true);
                } else {
                    itemData.setSelected(false);
                }
            }
        }
        notifyUi(MSG_LOAD_DATA_SUCCESS);
    }

    @Override
    public void onClickZZItem(ZZItemData zzItemData) {

    }

    @Override
    public void onClickInside(IViewModel viewModel) {

    }

    @Override
    public void release() {
        super.release();
        mDataSource.release();
    }
}
