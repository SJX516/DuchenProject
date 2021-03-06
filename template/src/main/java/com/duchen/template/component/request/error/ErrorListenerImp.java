package com.duchen.template.component.request.error;

import com.android.volley.NetworkError;
import com.android.volley.VolleyError;
import com.duchen.template.utils.ToastUtil;

public class ErrorListenerImp implements ErrorListener {

    private String TAG = "StudyErrorListenerImp";

    public ErrorListenerImp(String tag) {
        TAG = tag;
    }

    @Override
    public void onErrorResponse(int sequence, String url, VolleyError error, boolean showToast) {

        if (!showToast) {
            return;
        }
        String msg = "服务器错误，请稍后重试";
        if (error instanceof NetworkError) {
            msg = "网络错误，请检查网络连接";
        }
        ToastUtil.showToast(msg);
    }

}
