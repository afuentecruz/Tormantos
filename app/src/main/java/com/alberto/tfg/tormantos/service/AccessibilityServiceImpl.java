package com.alberto.tfg.tormantos.service;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import com.alberto.tfg.tormantos.handler.EventHandler;

import java.util.Date;

/**
 * Accessibility Service implementation to capture all the accessibility events
 * generated across the ui.
 */
public class AccessibilityServiceImpl extends android.accessibilityservice.AccessibilityService {

    private static String TAG = "AccessibilityServiceImpl";

    private EventHandler eventHandler;

    @Override
    public void onAccessibilityEvent(final AccessibilityEvent event) {

        if (!eventIsNull(event)) {
            eventHandler.handleEvent(event, new Date());
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");

        this.eventHandler = new EventHandler(this.getApplicationContext());
    }

    @Override
    public void onInterrupt() {
    }

    @Override
    public void onServiceConnected() {
        super.onServiceConnected();

        Toast toast = Toast.makeText(getApplicationContext(), "Tormantos is watching you!", Toast.LENGTH_LONG);
        toast.show();
    }

    /**
     * Checks the event package name and class name and returns true if
     * any of them are null
     *
     * @param event, the Accessibility Event.
     * @return true if null, false otherwise.
     */
    private Boolean eventIsNull(AccessibilityEvent event) {
        if (event.getPackageName() == null ||
                event.getClassName() == null) {
            return true;
        } else {
            return false;
        }
    }
}
