package com.duchen.template.component.request.error;

import com.android.volley.VolleyError;
import com.google.gson.JsonElement;

public class ErrorBase extends VolleyError {

    private int mSequence = -1;
    private String mUrl = "";
    private int mErrorCode;
    private JsonElement results;

    public ErrorBase(int sequence, String url, String exceptionMessage, int errorCode, JsonElement results) {
        super(exceptionMessage);
        setSequence(sequence);
        setUrl(url);
        setErrorCode(errorCode);
        this.results = results;
    }

    public int getSequence() {
        return mSequence;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setSequence(int sequence) {
        mSequence = sequence;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public int getErrorCode() {
        return mErrorCode;
    }

    public void setErrorCode(int errorCode) {
        this.mErrorCode = errorCode;
    }

    public JsonElement getJsonResults() {
        return results;
    }
}
