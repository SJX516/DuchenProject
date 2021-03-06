package com.duchen.exchange;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExchangeResponse {
    private static final int TIME_OUT = 30 * 1000;

    private long mTimeOut = 0;
    private boolean mHasGet = false;
    boolean mIsAsync = true;

    int mCode = -1;
    String mMessage = "";
    String mData;

    String mResultString;

    Future<String> mAsyncResponse;

    public ExchangeResponse() {
        this(TIME_OUT);
    }

    public ExchangeResponse(long timeout) {
        if (timeout > TIME_OUT * 2 || timeout < 0) {
            timeout = TIME_OUT;
        }
        mTimeOut = timeout;
    }

    public boolean isAsync() {
        return mIsAsync;
    }

    public String get() throws Exception {
        if (mIsAsync) {
            mResultString = mAsyncResponse.get(mTimeOut, TimeUnit.MILLISECONDS);
            parseResult();
        } else {
            parseResult();
        }
        return mResultString;
    }

    private void parseResult() {
        if (!mHasGet) {
            try {
                JSONObject jsonObject = new JSONObject(mResultString);
                this.mCode = jsonObject.optInt("code");
                this.mMessage = jsonObject.optString("msg");
                this.mData = jsonObject.optString("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mHasGet = true;
        }
    }

    public int getCode() throws Exception {
        if (!mHasGet) {
            get();
        }
        return mCode;
    }

    public String getMessage() throws Exception {
        if (!mHasGet) {
            get();
        }
        return mMessage;
    }

    public String getData() throws Exception {
        if (!mHasGet) {
            get();
        }
        return mData;
    }
}
