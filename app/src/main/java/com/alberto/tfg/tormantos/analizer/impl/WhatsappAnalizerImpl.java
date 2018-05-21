package com.alberto.tfg.tormantos.analizer.impl;

import android.util.Log;

import com.alberto.tfg.tormantos.analizer.Analizer;
import com.alberto.tfg.tormantos.dto.common.TimestampString;
import com.alberto.tfg.tormantos.dto.messaging.WhatsappDto;
import com.alberto.tfg.tormantos.manager.messaging.WhatsappManager;
import com.alberto.tfg.tormantos.sto.EventSto;
import com.alberto.tfg.tormantos.utils.Helper;
import com.alberto.tfg.tormantos.utils.Strings;

import java.util.Date;

/**
 * Analizer implementation for Whatsapp
 */
public class WhatsappAnalizerImpl implements Analizer {

    private static final String TAG = "WhatsappAnalizer";

    private String currentMessage;

    private String currentInterlocutor;

    private WhatsappDto whatsappDto;

    private WhatsappManager whatsappManager;

    public WhatsappAnalizerImpl() {
        Log.d(TAG, "Constructor del whatsappAnalizer");
        whatsappManager = new WhatsappManager();
        whatsappDto = null;
    }

    @Override
    public void compute(EventSto eventSto) {

        if (this.whatsappDto == null) {
            whatsappDto = new WhatsappDto(eventSto.getCaptureInstant());
        }

        switch (eventSto.getClassName()) {

            case Strings.CLASS_HOMEACTIVITY: // Navigated to WhatsApp home
                Log.d(TAG, "Home");
                storeObjectInRealm(eventSto.getCaptureInstant());
                // Create a new whatsappDto object
                this.whatsappDto = new WhatsappDto(eventSto.getCaptureInstant());
                this.currentMessage = "";
                break;

            case Strings.WIDGET_RELATIVE_LAYOUT: //Clicked on a conversation

                // Extract the contact name string
                currentInterlocutor = this.getContactName(Helper.getEventText(eventSto.getEvent()));
                whatsappDto.setInterlocutor(currentInterlocutor);

                Log.d(TAG, "Clicked whatsapp conversation: " + currentInterlocutor);
                break;


            case Strings.WIDGET_EDITTEXT: // Writing
                Log.d(TAG, "Escribiendo en whatsapp");

                // Get the writted line in the conversation
                this.currentMessage = Helper.getEventText(eventSto.getEvent());
                break;

            default:
                break;

        }
    }


    /**
     * getContactName, method that extracts the name of the person or group that the user is writing on
     *
     * @param eventText, accessibilityEvent text.
     * @return String contactName, the extracted name
     */
    private String getContactName(String eventText) {


        String contactName = "";
        for (int i = 0; i < eventText.length(); i++) {
            if (eventText.charAt(0) == eventText.charAt(i)) {
                contactName = eventText.substring(0, i);
            }
        }
        return contactName;
    }


    /**
     * Method called by the eventHandler when the keyboard triggers the send text event
     *
     * @param sendTimestamp Date
     */
    public void confirmKeyboardInput(Date sendTimestamp) {

        if (this.whatsappDto == null || this.currentMessage == null || this.currentMessage.isEmpty()
                || this.currentMessage.equals(Strings.KEY_KEYBOARD_WRITTE_MSG))
            return;

        TimestampString ts = new TimestampString(this.currentMessage, sendTimestamp);
        this.whatsappDto.getTextList().add(ts);
        Log.d(TAG, "Confirmada linea: " + ts);
    }


    public void storeObjectInRealm(Date timestamp) {

        if (this.whatsappDto != null && this.whatsappDto.getInterlocutor() != null) {
            if(this.whatsappDto.getInterlocutor().equals("")){
                this.whatsappDto.setInterlocutor("Whatsapp Home");
            }
            //  if(this.whatsappDB.getLastTextRegistry().length() != 0){ // There is some data to store
            this.whatsappDto.setEndTimestamp(timestamp);
            WhatsappManager.saveOrUpdateWhatsappDB(this.whatsappDto);
            //}

        }
    }
}
