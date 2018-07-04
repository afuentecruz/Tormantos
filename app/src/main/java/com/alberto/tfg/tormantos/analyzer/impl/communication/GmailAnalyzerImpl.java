package com.alberto.tfg.tormantos.analyzer.impl.communication;

import android.content.Context;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import com.alberto.tfg.tormantos.analyzer.Analyzer;
import com.alberto.tfg.tormantos.dto.comunication.GmailDto;
import com.alberto.tfg.tormantos.manager.DBManager;
import com.alberto.tfg.tormantos.sto.EventSto;
import com.alberto.tfg.tormantos.utils.Helper;
import com.alberto.tfg.tormantos.utils.Strings;

import io.realm.RealmList;

/**
 * Analyzer implementation for Gmail capture.
 */
public class GmailAnalyzerImpl implements Analyzer {

    private static final String TAG = "GmailAnalyzer";
    private Context context;

    /**
     * GmailDto object that stores the user information
     */
    private GmailDto gmailDto;

    public GmailAnalyzerImpl(Context context) {
        this.context = context;
        gmailDto = new GmailDto();
    }

    @Override
    public void compute(EventSto eventSto) {

        if (eventSto.getEvent().getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            // Gmail was launched, instantiate it
            gmailDto = new GmailDto();
        } else {
            // Whatever accessibility event
            switch (eventSto.getClassName()) {
                case Strings.WIDGET_IMAGEBUTTON:
                    Log.d(TAG, "Redactar nuevo correo!");
                    break;
                case Strings.WIDGET_SPINNER: //Sender
                    gmailDto.setSender(Helper.getEventText(eventSto.getEvent()));
                    break;
                case Strings.WIDGET_AUTOCOMPLETE: //Receivers
                    if (eventSto.getEvent().getEventType() == AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED) {
                        String receiver = Helper.getEventText(eventSto.getEvent());
                        if (receiver.endsWith(", ")) {
                            // Extract all the receivers one by one
                            receiver = receiver.replace("<", "");
                            receiver = receiver.replace(">", "");
                            gmailDto.setReceivers(this.formatReceivers(receiver));
                            // Log.d(TAG, receiver);
                        }
                    }

                    break;
                case Strings.WIDGET_EDITTEXT: //Mail subject
                    gmailDto.setSubject(Helper.getEventText(eventSto.getEvent()));

                    break;
                case Strings.WIDGET_VIEW_VIEW: //Mail body
                    // Deprecated, not using View_View event anymmore, in fact, the body write doesn't generates any event.
                    gmailDto.setBody(Helper.getEventText(eventSto.getEvent()));

                    break;
                case Strings.WIDGET_TOAST: //Mail finally send
                    if (Helper.getEventText(eventSto.getEvent()).equals(Strings.KEY_GMAIL_SENDED)) {
                        gmailDto.setTimestamp(eventSto.getCaptureInstant());
                        storeObjectInRealm();
                    }

                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Extract the receiverse from the event raw string.
     *
     * @param rawReceivers the receivers data string.
     * @return Realm string list.
     */
    private RealmList<String> formatReceivers(String rawReceivers) {
        //Receivers is a String like "<albertodlfnte@gmail.com>, <adelafue@gmail.com>, ";
        RealmList<String> receiversList = new RealmList<>();
        String[] receiversArray = rawReceivers.split(", ");

        for (int i = 0; i < receiversArray.length; i++) {
            receiversList.add(receiversArray[i]);
        }
        return receiversList;
    }

    /**
     * Stores the GmailDto into RealmDB.
     */
    public void storeObjectInRealm() {
        if (this.gmailDto != null) {
            DBManager.saveOrUpdate(this.gmailDto);
            Toast.makeText(context, "Stored gmail:\n" + this.gmailDto.toString(), Toast.LENGTH_LONG).show();

        }
    }
}
