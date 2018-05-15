package com.alberto.tfg.tormantos.analizer.impl;

import android.util.Log;

import com.alberto.tfg.tormantos.analizer.Analizer;
import com.alberto.tfg.tormantos.dto.messaging.WhatsappDto;
import com.alberto.tfg.tormantos.sto.EventSto;
import com.alberto.tfg.tormantos.utils.Strings;

/**
 * Analizer implementation for Whatsapp
 */
public class WhatsappAnalizerImpl implements Analizer {

    private static final String TAG = "WhatsappAnalizer";

    private WhatsappDto whatsappDto;

    @Override
    public void compute(EventSto eventSto) {

        switch (eventSto.getClassName()) {

            case Strings.CLASS_HOMEACTIVITY:
                Log.d(TAG, "Home");
                break;
            default:
                break;

        }


    }
}
