package com.Ex.CarGame_part2.utils;

import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MySPv.init(this);
    }
}
