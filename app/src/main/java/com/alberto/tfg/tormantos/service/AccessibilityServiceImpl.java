package com.alberto.tfg.tormantos.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import com.alberto.tfg.tormantos.handler.EventHandler;
import com.alberto.tfg.tormantos.manager.AlarmReceiver;

import java.util.Calendar;
import java.util.Date;

/**
 * Accessibility Service implementation to capture all the accessibility events
 * generated across the ui.
 */
public class AccessibilityServiceImpl extends android.accessibilityservice.AccessibilityService {

    private static String TAG = "AccessibilityServiceImpl";

    /**
     * Required attributes for the dump service
     */
    private PendingIntent pendingIntent;
    private AlarmManager manager;

    /**
     * The EventHandler instance
     */
    private EventHandler eventHandler;


    @Override
    public void onAccessibilityEvent(final AccessibilityEvent event) {

        if (!isEventNull(event)) {
            eventHandler.handleEvent(event, new Date());
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");


        // -- start the accessibility events handler
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
        setupDumpService();
    }

    /**
     * Checks the event package name and class name and returns true if
     * any of them are null
     *
     * @param event, the Accessibility Event.
     * @return true if null, false otherwise.
     */
    private Boolean isEventNull(AccessibilityEvent event) {
        return event.getPackageName() == null ||
                event.getClassName() == null;
    }

    private void setupDumpService() {
        // Retrieve a PendingIntent that will perform a broadcast
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        manager = (AlarmManager) getSystemService(getApplicationContext().ALARM_SERVICE);

        Calendar firingCal = Calendar.getInstance();
        Calendar currentCal = Calendar.getInstance();

        firingCal.set(Calendar.HOUR_OF_DAY, 9); // At the hour you wanna fire
        firingCal.set(Calendar.MINUTE, 0); // Particular minute
        firingCal.set(Calendar.SECOND, 0); // particular second

        long intendedTime = firingCal.getTimeInMillis();
        long currentTime = currentCal.getTimeInMillis();

        if (intendedTime >= currentTime) {
            // set from today
            manager.setRepeating(AlarmManager.RTC, intendedTime, AlarmManager.INTERVAL_DAY, pendingIntent);
        } else {
            // set from next day
            firingCal.add(Calendar.DAY_OF_MONTH, 1);
            intendedTime = firingCal.getTimeInMillis();

            manager.setRepeating(AlarmManager.RTC, intendedTime, AlarmManager.INTERVAL_DAY, pendingIntent);
        }

    }
}
