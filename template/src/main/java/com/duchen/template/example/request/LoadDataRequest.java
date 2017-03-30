package com.duchen.template.example.request;

import com.android.volley.Response;
import com.duchen.template.component.request.RequestBase;
import com.duchen.template.component.request.error.ErrorListener;
import com.duchen.template.example.request.result.LoadDataResult;

import java.util.HashMap;
import java.util.Map;

public class LoadDataRequest extends RequestBase<LoadDataResult> {

    private int mPageIndex;
    private int mPageSize;

    public LoadDataRequest(int pageIndex, int pageSize, Response.Listener<LoadDataResult> listener, ErrorListener errorListener) {
        super("example/loadData", listener, errorListener);
        mPageIndex = pageIndex;
        mPageSize = pageSize;
    }

    @Override
    protected Map<String, String> getStudyPostParams() {
        Map<String, String> map = new HashMap<>();
        map.put("pageIndex", mPageIndex + "");
        map.put("pageSize", mPageSize + "");
        return map;
    }

    @Override
    protected boolean isResultAllowNull() {
        return true;
    }

}
