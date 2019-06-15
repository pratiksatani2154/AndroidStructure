package com.theone.demopermission.utills;

import android.app.Application;
import android.support.multidex.MultiDex;
import android.support.v4.content.ContextCompat;

public class DemoApplicationClass extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }

}
