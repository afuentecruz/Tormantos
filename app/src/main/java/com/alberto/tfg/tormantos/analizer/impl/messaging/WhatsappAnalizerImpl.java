package com.alberto.tfg.tormantos.analizer.impl.messaging;

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

    /** The message that the user is writting */
    private String currentMessage;
    /** Contact name of the person who the user is writting */
    private String currentInterlocutor;
    /** WhatsappDto object that stores all the information */
    private WhatsappDto whatsappDto;


    public WhatsappAnalizerImpl() {
        Log.d(TAG, "Constructor del whatsappAnalizer");
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

                break;
            case Strings.WIDGET_EDITTEXT: // Writing
                this.currentMessage = Helper.getEventText(eventSto.getEvent());

                break;
            default:
                break;
        }
    }

    /**
     * getContactName, method that extracts the name of the person or group the user is writing on
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
    }


    public void storeObjectInRealm(Date timestamp) {

        if (this.whatsappDto != null && this.whatsappDto.getInterlocutor() != null) {
            if (this.whatsappDto.getInterlocutor().equals("")) {
                this.whatsappDto.setInterlocutor("Whatsapp Home");
            }

            this.whatsappDto.setEndTimestamp(timestamp);
            Log.d(TAG, "save: " + whatsappDto.toString());
            WhatsappManager.saveOrUpdateWhatsappDB(this.whatsappDto);
        }
    }
}
