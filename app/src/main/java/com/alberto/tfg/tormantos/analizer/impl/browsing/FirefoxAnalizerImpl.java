package com.alberto.tfg.tormantos.analizer.impl.browsing;

import android.content.Context;
import android.widget.Toast;

import com.alberto.tfg.tormantos.analizer.Analizer;
import com.alberto.tfg.tormantos.dto.browsing.FirefoxDto;
import com.alberto.tfg.tormantos.manager.browsing.FirefoxManager;
import com.alberto.tfg.tormantos.sto.EventSto;
import com.alberto.tfg.tormantos.utils.Helper;
import com.alberto.tfg.tormantos.utils.Strings;

import java.util.Date;

/**
 * Analizer implementation for Firefox Web Browser.
 */
public class FirefoxAnalizerImpl implements Analizer {

    private static final String TAG = "FirefoxAnalizer";

    private Context context;

    private FirefoxDto firefoxDto;

    public FirefoxAnalizerImpl(Context context) {
        this.context = context;
        firefoxDto = new FirefoxDto();
    }

    @Override
    public void compute(EventSto eventSto) {
        switch (eventSto.getClassName()) {
            case Strings.WIDGET_EDITTEXT:
                if (!Strings.KEY_FIREFOX_SEARCH.equals(Helper.getEventText(eventSto.getEvent()))) {
                    firefoxDto.setSearchUrl(Helper.getEventText(eventSto.getEvent()));
                }
                break;
        }
    }

    @Override
    public void storeObjectInRealm() {
        if (firefoxDto != null &&
                firefoxDto.getSearchUrl() != null &&
                firefoxDto.getTimestamp() != null) {

            FirefoxManager.saveOrUpdateGmailDB(this.firefoxDto);
            Toast.makeText(context, "Stored firefox:\n" + this.firefoxDto.toString(), Toast.LENGTH_LONG).show();

        }
    }

    /**
     * Confirm the user inputs into the editText search
     * field.
     * @param timestamp date where the input has occurred
     */
    public void confirmKeyboardInput(Date timestamp){
        firefoxDto.setTimestamp(timestamp);
        this.storeObjectInRealm();
    }
}
