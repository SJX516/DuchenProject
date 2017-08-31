package com.duchen.exchange;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;


public class ExchangeRequest {

    private String sender;
    private String receiver;
    private int action;
    private String params;
    private String extra;

    private ExchangeRequest(Builder builder) {
        this.sender = builder.sender;
        this.receiver = builder.receiver;
        this.action = builder.action;
        this.params = builder.params;
        this.extra = builder.extra;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public int getAction() {
        return action;
    }

    public String getParams() {
        if (!TextUtils.isEmpty(params)) {
            return params;
        }
        return "";
    }

    public String getExtra() {
        if (!TextUtils.isEmpty(extra)) {
            return extra;
        }
        return "";
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    private JSONObject toJsonObject() {
        JSONObject args = new JSONObject();
        try {
            args.put("sender", sender);
            args.put("receiver", receiver);
            args.put("action", action);
            if (params != null) {
                args.put("params", params);
            }
            if (extra != null) {
                args.put("extra", extra);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return args;
    }

    public String toFullMsg() {
        JSONObject msg = new JSONObject();
        try {
            msg.put("args", toJsonObject());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return msg.toString();
    }

    public static class Builder {
        private String receiver;
        private String sender;
        private int action;
        private String params;
        private String extra;

        public Builder() {
            sender = "";
            receiver = "";
            action = -1;
            params = null;
        }

        protected Builder json(String requestJson) {
            try {
                JSONObject jsonObject = new JSONObject(requestJson);
                JSONObject args = jsonObject.getJSONObject("args");
                if (args != null) {
                    this.sender = args.getString("sender");
                    this.receiver = args.getString("receiver");
                    this.action = args.getInt("action");
                    this.params = args.getString("params");
                    this.extra = args.getString("extra");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return this;
        }

        public Builder receiver(String receiver) {
            this.receiver = receiver;
            return this;
        }

        public Builder action(int action) {
            this.action = action;
            return this;
        }

        public Builder params(String params) {
            this.params = params;
            return this;
        }

        public Builder extra(String extra) {
            this.extra = extra;
            return this;
        }

        public ExchangeRequest build() {
            return new ExchangeRequest(this);
        }
    }
}
