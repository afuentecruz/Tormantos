package com.alberto.tfg.tormantos.analyzer.impl.communication;

import android.content.Context;
import android.widget.Toast;

import com.alberto.tfg.tormantos.analyzer.Analyzer;
import com.alberto.tfg.tormantos.dto.comunication.DialDto;
import com.alberto.tfg.tormantos.manager.DBManager;
import com.alberto.tfg.tormantos.sto.EventSto;
import com.alberto.tfg.tormantos.utils.Helper;
import com.alberto.tfg.tormantos.utils.Strings;

/**
 * Analyzer implementation for the user calls
 */
public class DialAnalyzerImpl implements Analyzer {

    private Context context;

    private DialDto dialDto;

    public DialAnalyzerImpl(Context context) {
        this.context = context;
    }

    private String getContactNameFromQuickContacts(String eventText) {

        String contactName = "";
        for (int i = 0; i < eventText.length(); i++) {
            if (eventText.substring(0, i).equals(Strings.KEY_DIAL_QUICK_CONTACT)) {
                contactName = eventText.substring(i, eventText.length());
            }
        }
        return contactName;
    }

    private String getContactNameFromRecentContacts(String eventText) {

        String contactName = "";
        for (int i = 0; i < eventText.length(); i++) {
            if (eventText.substring(0, i).equals(Strings.KEY_DIAL_CALL_TO)) {
                contactName = eventText.substring(i, eventText.length());
            }
        }
        return contactName;
    }

    @Override
    public void compute(EventSto eventSto) {

        switch (eventSto.getClassName()) {
            case Strings.WIDGET_QUICKCONTACTBADGE: // -- call made from the contact list
                if (!"".equals(Helper.getEventText(eventSto.getEvent()))) {
                    dialDto = new DialDto(eventSto.getCaptureInstant());
                    dialDto.setContact(getContactNameFromQuickContacts(Helper.getEventText(eventSto.getEvent())));
                }
                break;
            case Strings.WIDGET_FRAME: // -- call made from favourite contacts
                if (!"".equals(Helper.getEventText(eventSto.getEvent()))) {
                    dialDto = new DialDto(eventSto.getCaptureInstant());
                    dialDto.setContact(Helper.getEventText(eventSto.getEvent()));
                }
                break;

            case Strings.WIDGET_IMAGEVIEW: // -- call made from the recent calls
                if (!"".equals(Helper.getEventText(eventSto.getEvent()))) {
                    dialDto = new DialDto(eventSto.getCaptureInstant());
                    dialDto.setContact(Helper.getEventText(eventSto.getEvent()));
                }
                break;
            case Strings.WIDGET_IMAGEBUTTON:
                if (Strings.KEY_DIAL_END.equals(Helper.getEventText(eventSto.getEvent()))) {
                    if (dialDto != null) {
                        dialDto.setTimestampEnd(eventSto.getCaptureInstant());
                        storeObjectInRealm();
                    }
                }

            default:
                break;

        }
    }

    @Override
    public void storeObjectInRealm() {
        DBManager.saveOrUpdate(this.dialDto);
        Toast.makeText(context, "Stored dial:\n" + this.dialDto.toString(), Toast.LENGTH_LONG).show();
    }
}
