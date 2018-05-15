package com.alberto.tfg.tormantos.handler;

import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.alberto.tfg.tormantos.analizer.impl.WhatsappAnalizerImpl;
import com.alberto.tfg.tormantos.sto.EventSto;
import com.alberto.tfg.tormantos.utils.EventDataHelper;
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

    private WhatsappAnalizerImpl whatsappAnalizer;

    public EventHandler() {
        Log.d(TAG, "constructor!");
        whatsappAnalizer = new WhatsappAnalizerImpl();

    }

    public void handleEvent(AccessibilityEvent event, Date timestamp) {

        EventSto eventSto = new EventSto(event, timestamp,
                event.getPackageName().toString(),
                event.getClassName().toString());
        EventDataHelper.log(eventSto);

        switch (eventSto.getPackageName()) {
            case Strings.PACKAGE_WHATSAPP:
                whatsappAnalizer.compute(eventSto);

                break;
            case Strings.PACKAGE_GMAIL:

                Log.d(TAG, "gmail!");
                break;
            default:
                Log.d(TAG, "default");
                break;

        }

    }
}
