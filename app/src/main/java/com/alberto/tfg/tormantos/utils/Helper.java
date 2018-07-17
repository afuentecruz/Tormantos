package com.alberto.tfg.tormantos.utils;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.alberto.tfg.tormantos.sto.EventSto;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Helper class to handle the main data
 * of the Accessility Events
 */
public class Helper {

    private static final String TAG = "Helper";

    private static final String KEY_DATE_PATTERN = "dd/MM/YYYY HH:mm:ss";

    /**
     * Generates a string from a Date with the pattern KEY_DATE_PATTERN
     * @param date the Date
     * @return String with the formatted date value
     */
    public static String formatDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(KEY_DATE_PATTERN);
        return sdf.format(date).toString();
    }

    /**
     * Extracts the text contained in an Accessibility event
     * @param event The accessibility evet
     * @return String with the event text
     */
    public static String getEventText(AccessibilityEvent event) {
        StringBuilder sb = new StringBuilder();
        for (CharSequence s : event.getText()) {
            sb.append(s);
        }
        return sb.toString();
    }

    /**
     * Logs an accessibility event contained in an eventSto.
     * @param event The eventSto
     */
    public static void log(EventSto event){

        SimpleDateFormat sdf = new SimpleDateFormat(KEY_DATE_PATTERN);

        if(!getEventText(event.getEvent()).equals("")){
            Log.d(TAG, String.format(
                    "[type] %s [eventId] %s [class] %s [package] %s [time] %s [text] %s",
                    getEventType(event.getEvent()), event.getEvent().getEventType(), event.getEvent().getClassName(), event.getPackageName(),
                    sdf.format(event.getCaptureInstant()).toString(), getEventText(event.getEvent()))
            );
        }
    }

    /**
     * Returns the type of the event as string
     * @param event The Accessibility Event
     * @return String with the event type value
     */
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
