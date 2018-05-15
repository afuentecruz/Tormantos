package com.alberto.tfg.tormantos.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import com.alberto.tfg.tormantos.utils.EventDataHelper;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Accessibility Service implementation to capture all the accessibility events
 * generated across the ui.
 */
public class AccessibilityServiceImpl extends android.accessibilityservice.AccessibilityService {

    private static String TAG = "AccessibilityServiceImpl";

    @Override
    public void onAccessibilityEvent(final AccessibilityEvent event) {

        if(event.getText().isEmpty())
            return;

        AccessibilityNodeInfo source = event.getSource();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat dt = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss");

        System.out.println("now: " + timestamp);
        System.out.println(event.getEventTime());

        EventDataHelper.log(event);
       // Log.d("Source", source.toString() + "");
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.d(TAG, "onCreate");
    }



    @Override
    public void onInterrupt() {
    }


    @Override
    public void onServiceConnected() {
        super.onServiceConnected();

        Toast toast = Toast.makeText(getApplicationContext(), "Service connected", Toast.LENGTH_LONG);
        toast.show();
    }

}
