package com.alberto.tfg.tormantos.handler;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.alberto.tfg.tormantos.analizer.impl.WhatsappAnalizerImpl;
import com.alberto.tfg.tormantos.sto.EventSto;
import com.alberto.tfg.tormantos.utils.Helper;
import com.alberto.tfg.tormantos.utils.Strings;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Handler component.
 * <p>
 * This class manages all the events captured by the accessibility service
 * Looks for her package name calls the corresponding analizer implementation.
 */
public class EventHandler {

    private static final String TAG = "EventHandler";

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");

    private String currentPackage;

    private WhatsappAnalizerImpl whatsappAnalizer;

    public EventHandler() {
        Log.d(TAG, "constructor!");
        whatsappAnalizer = new WhatsappAnalizerImpl();
        currentPackage = "";
    }

    public void handleEvent(AccessibilityEvent event, Date timestamp) {

        EventSto eventSto = new EventSto(event, timestamp,
                event.getPackageName().toString(),
                event.getClassName().toString());

        Helper.log(eventSto);

        checkRemainingData(eventSto);

        // -- Stores the current listening app package name if it isn't the keyboard
        if (!eventSto.getPackageName().equals(Strings.PACKAGE_KEYBOARD))
            currentPackage = eventSto.getPackageName();

        switch (eventSto.getPackageName()) {
            case Strings.PACKAGE_WHATSAPP:
                whatsappAnalizer.compute(eventSto);

                break;
            case Strings.PACKAGE_GMAIL:
                break;

            case Strings.PACKAGE_KEYBOARD: // Keyboard displayed event
                handleKeyboardEvent(eventSto);
                break;
            case Strings.PACKAGE_SHORTCUT: // App shortcut launch event
                handleShortchut(eventSto);
                break;
            default:
                Log.d(TAG, "default");
                break;
        }
    }

    /**
     * Handles a keyboard event in order to
     * confirm the user input in the correspond app analizer
     *
     * @param eventSto the EventSto.
     */
    private void handleKeyboardEvent(EventSto eventSto) {
        if (Helper.getEventText(eventSto.getEvent()).equals(Strings.KEY_KEYBOARD_SHOW_MSG)) {
            switch (this.currentPackage) {
                case Strings.PACKAGE_WHATSAPP:
                    whatsappAnalizer.confirmKeyboardInput(eventSto.getCaptureInstant());

                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Handles the launch of the shortcut of an application
     *
     * @param eventSto the EventSto.
     */
    private void handleShortchut(EventSto eventSto) {

        switch (Helper.getEventText(eventSto.getEvent())) {
            case Strings.KEY_WHATSAPP:
                whatsappAnalizer.compute(eventSto);
                break;
            default:
                break;
        }
    }

    /**
     * Checks if the app changed suddenly (for example, navigated to home from any other activity)
     * and try to store any previous content of the corresponding analizer.
     * @param eventSto the EventSto.
     */
    private void checkRemainingData(EventSto eventSto) {
        if (!eventSto.getPackageName().equals(Strings.PACKAGE_KEYBOARD)
                && !eventSto.getPackageName().equals(this.currentPackage)) {
            switch (this.currentPackage) {
                case Strings.PACKAGE_WHATSAPP:
                    whatsappAnalizer.storeObjectInRealm(eventSto.getCaptureInstant());
                    break;
                default:
                    break;
            }
        }
    }
}
