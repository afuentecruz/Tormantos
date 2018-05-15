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
public class CaptureService extends AccessibilityService {

    private static String TAG = "CaptureService";

    public CaptureService(){
        System.out.println("constructor executed");
    }

    @Override
    public void onAccessibilityEvent(final AccessibilityEvent event) {
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
        System.out.println("que pasael servicio");
    }



    @Override
    public void onInterrupt() {

        Toast toast = Toast.makeText(getApplicationContext(), "Tormantos KO KO KO KO KO", Toast.LENGTH_LONG);
        toast.show();
        Log.d(TAG, "A TOMAR POR CULO BICICLETA");
    }


    @Override
    public void onServiceConnected() {
        super.onServiceConnected();

        Toast toast = Toast.makeText(getApplicationContext(), "Service connected", Toast.LENGTH_LONG);
        toast.show();

        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.flags = AccessibilityServiceInfo.DEFAULT;
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        info.notificationTimeout = 100;

        this.setServiceInfo(info);


/*
        Toast toast = Toast.makeText(getApplicationContext(), "Service connected", Toast.LENGTH_LONG);
        toast.show();

        Log.d("CULO", "Service connected");
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.flags = AccessibilityServiceInfo.DEFAULT;
        info.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        setServiceInfo(info);

        super.onServiceConnected();*/
        /*
        // Set the type of events that this service wants to listen to. Others won't be passed to this service.
        // We are only considering windows state changed event.
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_WINDOWS_CHANGED | AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED | AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED;
        // If you only want this service to work with specific applications, set their package names here. Otherwise, when the service is activated, it will listen to events from all applications.
        info.packageNames = new String[] {"com.example.android.myFirstApp", "com.example.android.mySecondApp"};
        // Set the type of feedback your service will provide. We are setting it to GENERIC.
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        // Default services are invoked only if no package-specific ones are present for the type of AccessibilityEvent generated.
        // This is a general-purpose service, so we will set some flags
        info.flags = AccessibilityServiceInfo.DEFAULT;
        info.flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS; info.flags = AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS;
        info.flags = AccessibilityServiceInfo.FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY; info.flags = AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS;
        // We are keeping the timeout to 0 as we don’t need any delay or to pause our accessibility events
        info.notificationTimeout = 0;
        this.setServiceInfo(info);
        */
    }

}
