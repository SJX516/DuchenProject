package com.duchen.template.example.datasource.impl;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.duchen.template.component.DataSourceBase;
import com.duchen.template.component.request.error.ErrorListenerImp;
import com.duchen.template.concept.IViewModel;
import com.duchen.template.example.datasource.XXDataSource;
import com.duchen.template.example.model.Container;
import com.duchen.template.example.model.impl.ContainerImpl;
import com.duchen.template.example.request.ExampleRequestManager;
import com.duchen.template.example.request.result.LoadDataResult;


public class XXDataSourceImpl extends DataSourceBase implements XXDataSource {

    private int mCurPageIndex = 0;
    private int mRequestPageSize = 20;
    private int mTotalPageCount = 1;

    private OnDataResultListener mListener;

    @Override
    public void setLoadDataListener(OnDataResultListener loadDataListener) {
        mListener = loadDataListener;
    }

    @Override
    public void loadData(boolean isRefresh) {
        if (isRefresh) {
            mCurPageIndex = 0;
            mTotalPageCount = 1;
        }
        if (canLoadMore()) {
            int id = ExampleRequestManager.getInstance().doLoadData(mCurPageIndex + 1, mRequestPageSize, new Response.Listener<LoadDataResult>() {
                @Override
                public void onResponse(LoadDataResult loadDataResult) {
                    if (loadDataResult == null) {
                        onLoadData(true, null);
                    } else {
                        Container container = new ContainerImpl(loadDataResult);
                        mTotalPageCount = container.getPagination().getTotalPageCount();
                        onLoadData(true, container);
                    }
                }
            }, new ErrorListenerImp("XXDataSourceImpl") {
                @Override
                public void onErrorResponse(int sequence, String url, VolleyError error, boolean showToast) {
                    super.onErrorResponse(sequence, url, error, showToast);
                    onLoadData(false, null);
                }
            });
            addReqId(id);
        }
    }

    private void onLoadData(boolean isSuccess, Container response) {
        if (isSuccess) {
            mCurPageIndex++;
        }
        if (mListener != null) {
            mListener.onLoadDataFinish(isSuccess, response);
        }
    }

    @Override
    public boolean canLoadMore() {
        return mCurPageIndex < mTotalPageCount;
    }

    @Override
    public void release() {
        super.release();
        mListener = null;
        mCurPageIndex = 0;
        mTotalPageCount = 1;
    }
}
