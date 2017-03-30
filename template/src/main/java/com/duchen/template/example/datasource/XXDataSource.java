package com.duchen.template.example.datasource;

import com.duchen.template.concept.IDataSource;
import com.duchen.template.example.model.Container;

public interface XXDataSource extends IDataSource {

    interface OnDataResultListener {
        void onLoadDataFinish(boolean isSuccess, Container container);
    }

    void setLoadDataListener(OnDataResultListener loadDataListener);

    void loadData(boolean isRefresh);

    boolean canLoadMore();

}
