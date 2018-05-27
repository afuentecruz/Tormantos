package com.alberto.tfg.tormantos.analizer.impl.messaging;

import android.content.Context;
import android.widget.Toast;

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
    private Context context;
    /**
     * The message that the user is writting
     */
    private String currentMessage;
    /**
     * Contact name of the person who the user is writting
     */
    private String currentInterlocutor;
    /**
     * WhatsappDto object that stores all the information
     */
    private WhatsappDto whatsappDto;


    public WhatsappAnalizerImpl(Context context) {
        this.context = context;
        whatsappDto = null;
    }

    @Override
    public void compute(EventSto eventSto) {

        if (this.whatsappDto == null) {
            whatsappDto = new WhatsappDto(eventSto.getCaptureInstant());
        }

        switch (eventSto.getClassName()) {
            case Strings.CLASS_HOMEACTIVITY: // Navigated to WhatsApp home

                // Stores any previous content in database)
                this.whatsappDto.setEndTimestamp(eventSto.getCaptureInstant());
                storeObjectInRealm();

                // Create a new whatsappDto object
                this.whatsappDto = new WhatsappDto(eventSto.getCaptureInstant());
                this.whatsappDto.setInterlocutor("Whatsapp Home"); // user navigated to WhatsApp home
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

    /**
     * Checks if there is any remaining data waiting to be stored
     * in the dto.
     *
     * @param eventSto the EventSto.
     */
    public void checkRemainingData(EventSto eventSto) {
        if (this.whatsappDto.getTextList() != null &&
                this.whatsappDto.getStartTimestamp() != null &&
                this.whatsappDto.getInterlocutor() != null) {

            this.whatsappDto.setEndTimestamp(eventSto.getCaptureInstant());
            storeObjectInRealm();
        }
    }


    /**
     * Stores the WhatsappDto into RealmDB.
     */
    public void storeObjectInRealm() {
        if (this.whatsappDto != null && this.whatsappDto.getInterlocutor() != null) {
            WhatsappManager.saveOrUpdateWhatsappDB(this.whatsappDto);
            Toast.makeText(context, "Stored whatsapp:\n" + this.whatsappDto.toString(), Toast.LENGTH_LONG).show();

        }
    }
}