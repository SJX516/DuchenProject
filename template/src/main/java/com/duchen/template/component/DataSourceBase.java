package com.duchen.template.component;

import com.duchen.template.component.request.RequestManager;
import com.duchen.template.concept.IDataSource;
import com.duchen.template.utils.LogUtil;

import java.util.HashSet;
import java.util.List;

/**
 * 数据提供的基类
 */
public abstract class DataSourceBase implements IDataSource {

    private final static String TAG = "DataSourceBase";

    private HashSet<Integer> mReqIds = new HashSet<Integer>();
    private boolean mIsReleased = true;

    public DataSourceBase() {
        mIsReleased = false;
    }

    protected void addReqId(int id) {
        mReqIds.add(id);
    }

    protected void removeId(int id) {
        mReqIds.remove(id);
    }

    protected void addReqIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        mReqIds.addAll(ids);
    }

    protected void removeIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) return;
        mReqIds.remove(ids);
    }

    @Override
    public void release() {
        mIsReleased = true;
        LogUtil.d(TAG, "release");
        cancelAllRequest();
    }

    protected void cancelAllRequest() {
        for (Integer id : mReqIds) {
            RequestManager.getInstance().cancelRequest(id);
        }
    }
}
