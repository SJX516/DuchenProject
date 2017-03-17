package com.duchen.template.example;

import android.content.Context;
import android.os.Handler;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.duchen.template.component.LogicBase;
import com.duchen.template.component.request.error.ErrorListenerImp;
import com.duchen.template.concept.IViewModel;
import com.duchen.template.example.request.ExampleRequestManager;
import com.duchen.template.example.request.result.DataDto;
import com.duchen.template.example.request.result.LoadDataResult;
import com.duchen.template.example.ui.XXListBox;
import com.duchen.template.example.ui.model.YYItemData;
import com.duchen.template.example.ui.model.ZZItemData;
import com.duchen.template.utils.datahelper.DataCheckUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class XXLogic extends LogicBase implements XXListBox.ViewModel {

    public static final int MSG_LOAD_DATA_SUCCESS = 0x101;
    public static final int MSG_LOAD_MORE_FAILED = 0x102;
    public static final int MSG_LOAD_DATA_NO_CONTENT = 0x103;
    public static final int MSG_LOAD_DATA_ERROR = 0x104;

    private int mCurPageIndex = 0;
    private int mRequestPageSize = 20;
    private int mTotalPageCount = 1;

    private List<IViewModel> mItems = new ArrayList<>();

    public XXLogic(Context context, Handler handler) {
        super(context, handler);
    }

    public void loadData(boolean isRefresh) {
        if (isRefresh) {
            mCurPageIndex = 0;
            mTotalPageCount = 1;
            mItems.clear();
        }
        if (canLoadMore()) {
            ExampleRequestManager.getInstance().doLoadData(mCurPageIndex + 1, mRequestPageSize, new Response.Listener<LoadDataResult>() {
                @Override
                public void onResponse(LoadDataResult loadDataResult) {
                    mTotalPageCount = loadDataResult.getTotalPageCount();
                    List<IViewModel> response = buildItems(loadDataResult);
                    onLoadDataResult(true , response);
                }
            }, new ErrorListenerImp("XXLogic") {
                @Override
                public void onErrorResponse(int sequence, String url, VolleyError error, boolean showToast) {
                    super.onErrorResponse(sequence, url, error, showToast);
                    onLoadDataResult(false, null);
                }
            });
        }
    }

    XXListBox.ViewModel getViewModel() {
        return this;
    }

    @Override
    public Collection<IViewModel> getItems() {
        return mItems;
    }

    @Override
    public boolean canLoadMore() {
        return mCurPageIndex < mTotalPageCount;
    }

    @Override
    public boolean canPullToRefresh() {
        return true;
    }

    private void onLoadDataResult(boolean isSuccess, List<IViewModel> response) {
        if (isSuccess) {
            if (!DataCheckUtil.checkListDataUsable(response)) {
                if (!DataCheckUtil.checkListDataUsable(mItems)) {
                    notifyUi(MSG_LOAD_DATA_NO_CONTENT);
                } else {
                    notifyUi(MSG_LOAD_MORE_FAILED);
                }
            } else {
                mCurPageIndex++;
                mItems.addAll(response);
                notifyUi(MSG_LOAD_DATA_SUCCESS);
            }
        } else {
            if (!DataCheckUtil.checkListDataUsable(mItems)) {
                notifyUi(MSG_LOAD_DATA_ERROR);
            } else {
                notifyUi(MSG_LOAD_MORE_FAILED);
            }
        }
    }

    private List<IViewModel> buildItems(LoadDataResult loadDataResult) {
        List<IViewModel> list = new ArrayList<>();
        for (DataDto dataDto : loadDataResult.getDataList()) {
            switch (dataDto.getType()) {
                case DataDto.TYPE_YY_ITEM:
                    YYItemData yyItemData = new YYItemData();
                    yyItemData.setSelected(false);
                    yyItemData.setTitle(dataDto.getTitle());
                    yyItemData.setDescription(dataDto.getDescription());
                    if (list.size() == loadDataResult.getDataList().size() - 1) {
                        yyItemData.setShowSomething(false);
                    } else {
                        yyItemData.setShowSomething(true);
                    }
                    list.add(yyItemData);
                    break;
                case DataDto.TYPE_ZZ_ITEM:
                    ZZItemData zzItemData = new ZZItemData();
                    zzItemData.setImageUrl(dataDto.getImageUrl());
                    list.add(zzItemData);
                    break;
            }
        }
        return list;
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
        notifyUi(MSG_LOAD_DATA_SUCCESS);
    }

    public void onClickZZItem(ZZItemData zzItemData) {

    }

    public void onClickInside(IViewModel viewModel) {

    }
}
