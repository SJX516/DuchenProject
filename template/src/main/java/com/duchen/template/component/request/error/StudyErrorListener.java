package com.duchen.template.component.request.error;

import com.android.volley.VolleyError;

public interface StudyErrorListener {

    void onErrorResponse(int sequence, String url, VolleyError error, boolean showToast);

}
