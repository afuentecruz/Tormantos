package com.alberto.tfg.tormantos.analizer.impl.communication;

import android.util.Log;

import com.alberto.tfg.tormantos.analizer.Analizer;
import com.alberto.tfg.tormantos.dto.comunication.SmsDto;
import com.alberto.tfg.tormantos.manager.communication.SmsManager;
import com.alberto.tfg.tormantos.sto.EventSto;
import com.alberto.tfg.tormantos.utils.Helper;
import com.alberto.tfg.tormantos.utils.Strings;

/**
 * Analizer implementation for sms capture
 */
public class SmsAnalizerImpl implements Analizer {

    private static final String TAG = "SmsAnalizer";

    /**
     * SmsDto object that stores the user information
     */
    private SmsDto smsDto;

    /**
     * Current contact name
     */
    private String currentContactName = "";

    @Override
    public void compute(EventSto eventSto) {
        Helper.log(eventSto);

        switch (eventSto.getClassName()) {
            case Strings.CLASS_SMS_CONVERSATION:
                Log.d(TAG, "empezada conversación de sms");
                this.smsDto = new SmsDto();
                break;
            case Strings.WIDGET_AUTOCOMPLETE:
                // Contact name
                this.formatReceivers(Helper.getEventText(eventSto.getEvent()));
                break;
            case Strings.WIDGET_EDITTEXT:
                // Sms content
                this.smsDto.setContent(Helper.getEventText(eventSto.getEvent()));
                break;
            case Strings.WIDGET_LINEAR_LAYOUT:
                // Sms sended
                this.smsDto.setSendTimestamp(eventSto.getCaptureInstant());
                this.storeObjectInRealm();
                break;
            default:
                break;
        }
    }

    /**
     * Get the sms receivers from the raw string
     * located into the event text.
     * @param rawReceivers the event text string.
     */
    private void formatReceivers(String rawReceivers) {
        for (int i = 0; i < rawReceivers.length(); i++) {
            if (rawReceivers.substring(i, rawReceivers.length()).equals(Strings.KEY_SMS_CONTACT_ADDED)) {
                Log.d(TAG, "Contact SMS: " + rawReceivers.substring(0, i));
                this.smsDto.getReceivers().add(rawReceivers.substring(0, i));
                this.currentContactName = rawReceivers.substring(0, i);
            }
        }
    }

    /**
     * Stores the smsDto object into realmDB.
     */
    public void storeObjectInRealm() {
        if (this.smsDto.getReceivers().size() == 0) {
            if (!("".equals(this.currentContactName) || currentContactName != null)) {
                this.smsDto.getReceivers().add(currentContactName);
            } else {
                this.smsDto.getReceivers().add("unknown");
            }

        }
        SmsManager.saveOrUpdateSmsDB(this.smsDto);
    }
}
