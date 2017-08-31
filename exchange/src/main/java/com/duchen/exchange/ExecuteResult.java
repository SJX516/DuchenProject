package com.duchen.exchange;


import org.json.JSONException;
import org.json.JSONObject;


public class ExecuteResult {
    public static final int CODE_SUCCESS = 0x0000;
    public static final int CODE_ERROR = 0x0001;
    protected static final int CODE_INVALID = 0X0003;
    protected static final int CODE_CANNOT_BIND_REMOTE = 0X0004;

    private int code;
    private String msg;
    private String data;

    private ExecuteResult(Builder builder) {
        this.code = builder.mCode;
        this.msg = builder.mMsg;
        this.data = builder.mData;
    }

    public String getData() {
        if (data != null) {
            return data;
        }
        return "";
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("code", code);
            jsonObject.put("msg", msg);
            jsonObject.put("data", data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public static class Builder {
        private int mCode;
        private String mMsg;
        private String mData;

        public Builder() {
            mCode = CODE_ERROR;
            mMsg = "";
            mData = null;
        }

        protected Builder json(String resultString) {
            try {
                JSONObject jsonObject = new JSONObject(resultString);
                this.mCode = jsonObject.getInt("code");
                this.mMsg = jsonObject.getString("msg");
                this.mData = jsonObject.getString("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return this;
        }

        public Builder code(int code) {
            this.mCode = code;
            return this;
        }

        public Builder msg(String msg) {
            this.mMsg = msg;
            return this;
        }

        public Builder data(String data) {
            this.mData = data;
            return this;
        }

        public ExecuteResult build() {
            return new ExecuteResult(this);
        }
    }
}
