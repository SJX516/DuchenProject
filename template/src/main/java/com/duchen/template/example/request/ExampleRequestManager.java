package com.duchen.template.example.request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.duchen.template.component.request.RequestManager;
import com.duchen.template.component.request.error.StudyErrorListener;
import com.duchen.template.example.request.result.LoadDataResult;

public class ExampleRequestManager {

    private static ExampleRequestManager sInstance;

    public static synchronized ExampleRequestManager getInstance() {
        if (sInstance == null) {
            sInstance = new ExampleRequestManager();
        }
        return sInstance;
    }

    private int postRequest(Request<?> request) {
        return RequestManager.getInstance().postRequest(request);
    }

    public int doLoadData(Response.Listener<LoadDataResult> listener, StudyErrorListener errorListener) {
        LoadDataRequest request = new LoadDataRequest(listener, errorListener);
        return postRequest(request);
    }
}
