package com.duchen.template.component.request;

import com.duchen.template.concept.model.LegalModel;
import com.google.gson.JsonElement;

public class BaseResponseData implements IRequest, LegalModel {

    public static final int RESPONSE_SUCCESS_CODE = 0;

    public JsonElement results;
    public int code;
    public String message;

    public Object data;


    private int mSequence = -1;
    private String mUrl = "";

    @Override
    public int getSequence() {
        return mSequence;
    }

    @Override
    public String getUrl() {
        return mUrl;
    }

    @Override
    public void setSequence(int sequence) {
        mSequence = sequence;
    }

    @Override
    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public boolean check() {
        return true;
    }

}
