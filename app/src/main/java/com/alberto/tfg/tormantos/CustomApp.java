package com.alberto.tfg.tormantos;

import android.app.Application;
import android.util.Log;

public class CustomApp extends Application{

    private static final String TAG = "CustomApp";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "CustomApp onCreate()");


    }
}
