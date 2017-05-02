package com.duchen.template.component.request;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RequestQueue.RequestFilter;
import com.android.volley.Response;
import com.android.volley.toolbox.FileRequest;
import com.android.volley.toolbox.Volley;
import com.duchen.template.component.ApplicationBase;
import com.duchen.template.utils.LogUtil;

import java.io.File;

public class RequestManager {

    private final static String TAG = "RequestManager";
    private static RequestManager sRequestManager;

    public static RequestManager getInstance() {
        if (sRequestManager == null) {
            sRequestManager = new RequestManager();
        }
        if (sRequestManager.mRequestQueue != null && sRequestManager.mIsQueueStopped) {
            sRequestManager.mRequestQueue.start();
            sRequestManager.mIsQueueStopped = false;
        }
        return sRequestManager;
    }

    public static RequestManager getInstance(Context context) {
        if (sRequestManager == null) {
            sRequestManager = new RequestManager(context);
        }
        if (sRequestManager.mRequestQueue != null && sRequestManager.mIsQueueStopped) {
            sRequestManager.mRequestQueue.start();
            sRequestManager.mIsQueueStopped = false;
        }
        return sRequestManager;
    }

    private boolean mIsQueueStopped = false;
    private RequestQueue mRequestQueue = null;

    private RequestManager() {
        mRequestQueue = Volley.newRequestQueue(ApplicationBase.getInstance());
        mRequestQueue.start();
        mIsQueueStopped = false;
    }

    private RequestManager(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.start();
        mIsQueueStopped = false;
    }

    public int postRequest(Request<?> request) {
        return mRequestQueue.add(request).getSequence();
    }

    public void cancelRequest(final int id) {
        if (id <= 0) return;
        mRequestQueue.cancelAll(new RequestFilter() {

            @Override
            public boolean apply(Request<?> request) {
                return request.getSequence() == id;
            }
        });
    }

    public int downloadFile(String url, String filePath, Response.Listener<File> listener, Response.ErrorListener errorListener) {
        FileRequest request = new FileRequest(url, filePath, listener, errorListener);
        request.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0F));
        request.setShouldCache(false);
        return postRequest(request);
    }

    public void cancelAll() {
        LogUtil.d(TAG, "cancelAll");
        mRequestQueue.stop();
        mRequestQueue.cancelAll(new RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
        mIsQueueStopped = true;
    }

}
