package com.alberto.tfg.tormantos.utils;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

/**
 * Helper class to handle the main data
 * of the Accessility Events
 */
public class EventDataHelper {

    private static final String TAG = "Helper";

    public static String getEventText(AccessibilityEvent event) {
        StringBuilder sb = new StringBuilder();
        for (CharSequence s : event.getText()) {
            sb.append(s);
        }
        return sb.toString();
    }

    public static void log(AccessibilityEvent event){

        if(!getEventText(event).equals("")){
            Log.d(TAG, String.format(
                    "[type] %s [eventId] %s [class] %s [package] %s [time] %s [text] %s",
                    getEventType(event), event.getEventType(), event.getClassName(), event.getPackageName(),
                    event.getEventTime(), getEventText(event))
            );
        }

    }

    public static String getEventType(AccessibilityEvent event) {
        switch (event.getEventType()) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                return "TYPE_NOTIFICATION_STATE_CHANGED";
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                return "TYPE_VIEW_CLICKED";
            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
                return "TYPE_VIEW_FOCUSED";
            case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:
                return "TYPE_VIEW_LONG_CLICKED";
            case AccessibilityEvent.TYPE_VIEW_SELECTED:
                return "TYPE_VIEW_SELECTED";
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                return "TYPE_WINDOW_STATE_CHANGED";
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                return "TYPE_VIEW_TEXT_CHANGED";
            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
                return "TYPE_VIEW_TEXT_SELECTION_CHANGED";
        }
        return Integer.toString(event.getEventType());
    }
}
