package com.duchen.template.example.request;

import com.android.volley.Response;
import com.duchen.template.component.request.RequestBase;
import com.duchen.template.component.request.error.ErrorListener;
import com.duchen.template.example.request.result.LoadDataResult;

import java.util.Map;

public class LoadDataRequest extends RequestBase<LoadDataResult> {


    public LoadDataRequest(Response.Listener<LoadDataResult> listener, ErrorListener errorListener) {
        super("example/loadData", listener, errorListener);
    }

    @Override
    protected Map<String, String> getStudyPostParams() {
        return null;
    }

}
