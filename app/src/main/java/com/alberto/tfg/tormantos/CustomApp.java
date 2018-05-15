package com.alberto.tfg.tormantos;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.alberto.tfg.tormantos.service.CaptureService;

public class CustomApp extends Application{

    private static final String TAG = "CustomApp";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "CustomApp onCreate()");


    }
}
