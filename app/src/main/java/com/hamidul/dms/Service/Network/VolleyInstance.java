package com.hamidul.dms.Service.Network;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyInstance {
    private static VolleyInstance volleyInstance;
    private static Context ctx;
    private RequestQueue requestQueue;

    private VolleyInstance(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized VolleyInstance getVolleyInstance(Context context) {
        if (volleyInstance == null) {
            volleyInstance = new VolleyInstance(context);
        }
        return volleyInstance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        request.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(request);
    }

}
