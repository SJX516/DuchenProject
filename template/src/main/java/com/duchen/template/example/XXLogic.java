package com.duchen.template.example;

import android.content.Context;
import android.os.Handler;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.duchen.template.component.LogicBase;
import com.duchen.template.component.request.error.StudyErrorListenerImp;
import com.duchen.template.concept.IViewModel;
import com.duchen.template.example.request.ExampleRequestManager;
import com.duchen.template.example.request.result.DataDto;
import com.duchen.template.example.request.result.LoadDataResult;
import com.duchen.template.example.ui.XXListBox;
import com.duchen.template.example.ui.model.YYItemData;
import com.duchen.template.example.ui.model.ZZItemData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class XXLogic extends LogicBase implements XXListBox.ViewModel {

    public static final int MSG_VIEWMODEL_UPDATE = 0x101;

    private List<IViewModel> mItems = new ArrayList<>();

    public XXLogic(Context context, Handler handler) {
        super(context, handler);
    }

    public void loadData() {
        ExampleRequestManager.getInstance().doLoadData(new Response.Listener<LoadDataResult>() {
            @Override
            public void onResponse(LoadDataResult loadDataResult) {
                generateItems(loadDataResult);
                notifyUi(MSG_VIEWMODEL_UPDATE);
            }
        }, new StudyErrorListenerImp("XXLogic") {
            @Override
            public void onErrorResponse(int sequence, String url, VolleyError error, boolean showToast) {
                super.onErrorResponse(sequence, url, error, showToast);
            }
        });
    }

    XXListBox.ViewModel getViewModel() {
        return this;
    }

    @Override
    public Collection<IViewModel> getItems() {
        return mItems;
    }

    private void generateItems(LoadDataResult loadDataResult) {
        mItems.clear();
        for (DataDto dataDto : loadDataResult.getDataList()) {
            switch (dataDto.getType()) {
                case DataDto.TYPE_YY_ITEM:
                    YYItemData yyItemData = new YYItemData();
                    yyItemData.setSelected(false);
                    yyItemData.setTitle(dataDto.getTitle());
                    yyItemData.setDescription(dataDto.getDescription());
                    if (mItems.size() == loadDataResult.getDataList().size() - 1) {
                        yyItemData.setShowSomething(false);
                    } else {
                        yyItemData.setShowSomething(true);
                    }
                    mItems.add(yyItemData);
                    break;
                case DataDto.TYPE_ZZ_ITEM:
                    ZZItemData zzItemData = new ZZItemData();
                    zzItemData.setImageUrl(dataDto.getImageUrl());
                    mItems.add(zzItemData);
                    break;
            }
        }
    }

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
        notifyUi(MSG_VIEWMODEL_UPDATE);
    }

    public void onClickZZItem(ZZItemData zzItemData) {

    }

    public void onClickInside(IViewModel viewModel) {

    }
}
