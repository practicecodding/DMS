package com.hamidul.dms.Theme;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

public class MyTheme extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }
}
