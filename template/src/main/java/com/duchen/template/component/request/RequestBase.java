package com.duchen.template.component.request;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.duchen.template.component.ApplicationBase;
import com.duchen.template.component.request.error.ErrorFactory;
import com.duchen.template.component.request.error.ErrorListener;
import com.duchen.template.component.model.LegalModelParser;
import com.duchen.template.utils.DLog;
import com.duchen.template.utils.ManifestUtil;
import com.duchen.template.utils.PlatformUtil;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public abstract class RequestBase<T> extends Request<ResponseDataBase> {

    private final static String TAG = "StudyRequestBase";

    static final String USER_AGENT_VERSION = "app-version";
    static final String USER_AGENT_KEY_IMEI = "imei";

    public final static int TIMEOUT_1S = 1*1000;
    public final static int TIMEOUT_3S = 3*1000;
    public final static int TIMEOUT_10S = 10*1000;
    public final static int TIMEOUT_15S = 15*1000;

    private final static int MAX_NUM_RETRY = 1;

    public String mUrl;
    public LegalModelParser mParser;
    private Listener<T> mListener;
    private ErrorListener mErrorListener;
    private long mRequestStartTime = 0L;

    public RequestBase(String url, Listener<T> listener, ErrorListener errorListener) {
        this(url, listener, errorListener, TIMEOUT_10S);
    }

    public RequestBase(String url, Listener<T> listener, ErrorListener errorListener, int timeout) {
        this(url, listener, errorListener, timeout, MAX_NUM_RETRY);
    }

    public RequestBase(String url, Listener<T> listener, ErrorListener errorListener, int timeout, int
            maxNumRetry) {
        super(Method.POST, RequestUrl.getUrl(url), null);
        setRetryPolicy(new DefaultRetryPolicy(timeout, maxNumRetry, 1f));
        mParser = new LegalModelParser();
        mUrl = url;
        mListener = listener;
        mErrorListener = errorListener;
        generalQueryUrl();
    }

    @Override
    protected Response<ResponseDataBase> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        DLog.d("response", parsed);
        ResponseDataBase brd = mParser.fromJson(parsed, ResponseDataBase.class);
        if (brd != null) {
            brd.setSequence(getSequence());
            brd.setUrl(mUrl);
            Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            try {
                brd.data = mParser.fromJson(brd.results, type);
            } catch (Error e) {
                DLog.e(TAG, e.getMessage());
                DLog.e(TAG, "Request type = " + this.getClass().getName() + ", error message = " + e.getMessage() +
                        ", response = " + parsed);
            }

            if (brd.code != 0) {
                return Response.error(ErrorFactory.create(getSequence(), mUrl, brd.code, brd.message, brd
                        .results));
            } else {
                if (brd.data == null && !isResultAllowNull()) {
                    return Response.error(new VolleyError("服务器返回数据为空"));
                } else {
                    return Response.success(brd, HttpHeaderParser.parseCacheHeaders(response));
                }
            }
        } else {
            return Response.error(new VolleyError("服务器返回数据为空"));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void deliverResponse(ResponseDataBase response) {

        if (mListener != null) {
            mListener.onResponse((T) response.data);
            mListener = null;
            mErrorListener = null;
        }
    }

    @Override
    public void deliverError(VolleyError error) {
        if (mErrorListener != null) {
            mErrorListener.onErrorResponse(getSequence(), mUrl, error, false);
            mListener = null;
            mErrorListener = null;
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        headers.put(USER_AGENT_VERSION, ManifestUtil.getApplicationVersionName(ApplicationBase.getInstance()));
        headers.put(USER_AGENT_KEY_IMEI, PlatformUtil.getPhoneIMEI(ApplicationBase.getInstance()));
        return headers;
    }

    //用于存储通用的请求参数
    public static class DefaultParams {

    }

    @Override
    public Map<String, String> getParams() throws AuthFailureError {
        mRequestStartTime = System.currentTimeMillis();
        Map<String, String> map = new HashMap<String, String>();
        //可以添加默认参数
        Map<String, String> studyParams = getStudyPostParams();
        if (studyParams != null) {
            map.putAll(studyParams);
        }
        return map;
    }

    protected abstract Map<String, String> getStudyPostParams();

    //如果服务器返回的 results 可以没有数据的话,这里一定要返回true,这样才能调用正确的回调
    protected abstract boolean isResultAllowNull();

}
