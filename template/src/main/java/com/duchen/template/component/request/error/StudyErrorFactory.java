package com.duchen.template.component.request.error;

import com.android.volley.VolleyError;
import com.google.gson.JsonElement;


public class StudyErrorFactory {

    public static VolleyError create(final int sequence, final String url, final int code, final String message, JsonElement results) {
        if (code == 0) return new VolleyError();
        VolleyError error = createError(sequence, url, code, message, results);
        if (error != null) {
            return error;
        }
        return new VolleyError();
    }

    private static VolleyError createError(int sequence, String url, int code, String message, JsonElement results) {
        return null;
    }

}
