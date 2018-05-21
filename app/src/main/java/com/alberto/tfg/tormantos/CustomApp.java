package com.alberto.tfg.tormantos;

import android.app.Application;
import android.util.Log;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class CustomApp extends Application{

    private static final String TAG = "CustomApp";

    @Override
    public void onCreate() {
        super.onCreate();
        setupRealm();
        Log.d(TAG, "CustomApp onCreate()");
    }

    public void setupRealm(){
        Log.d(TAG, "Setting up RealmDB");

        // Instance realm
        Realm.init(this.getApplicationContext());

        // Set up realm DB default configuration
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .name("tormantos.realm")
                .schemaVersion(0)
                .build());
    }
}
