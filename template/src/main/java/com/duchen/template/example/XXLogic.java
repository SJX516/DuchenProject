package com.duchen.template.example;

import android.content.Context;
import android.os.Handler;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.duchen.template.component.LogicBase;
import com.duchen.template.component.request.error.StudyErrorListener;
import com.duchen.template.component.request.error.StudyErrorListenerImp;
import com.duchen.template.concept.IViewModel;
import com.duchen.template.example.request.ExampleRequestManager;
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

    public void onClickYYItem(YYItemData yyItemData) {

    }

    public void onClickZZItem(ZZItemData zzItemData) {

    }

    public void onClickInside(IViewModel viewModel) {

    }


}
