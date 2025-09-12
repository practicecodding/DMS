package com.hamidul.dms.View.Manager;

import android.content.Context;
import android.widget.Toast;

public class ToastInstance {
    private static ToastInstance instance;
    private static Context ctx;
    private Toast toast;

    public static ToastInstance getInstance(Context context) {
        if (instance == null) {
            ctx = context;
            instance = new ToastInstance();
        }
        return instance;
    }

    public void setToast(String text) {
        if (toast != null) toast.cancel();
        toast = Toast.makeText(ctx, text, Toast.LENGTH_SHORT);
        toast.show();
    }

}
