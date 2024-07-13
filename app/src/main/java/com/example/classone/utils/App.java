package com.example.classone.utils;

import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MySPv.init(this);
    }
}
