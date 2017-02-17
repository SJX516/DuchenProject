package com.duchen.template.example.request;

import com.android.volley.Response;
import com.duchen.template.component.request.StudyRequestBase;
import com.duchen.template.component.request.error.StudyErrorListener;
import com.duchen.template.example.request.result.LoadDataResult;

import java.util.Map;

public class LoadDataRequest extends StudyRequestBase<LoadDataResult> {


    public LoadDataRequest(Response.Listener<LoadDataResult> listener, StudyErrorListener errorListener) {
        super("example/loadData", listener, errorListener);
    }

    @Override
    protected Map<String, String> getStudyPostParams() {
        return null;
    }

}
