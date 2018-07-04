package com.alberto.tfg.tormantos.analyzer.impl.browsing;

import android.content.Context;
import android.widget.Toast;

import com.alberto.tfg.tormantos.analyzer.Analyzer;
import com.alberto.tfg.tormantos.dto.browsing.FirefoxDto;
import com.alberto.tfg.tormantos.manager.DBManager;
import com.alberto.tfg.tormantos.sto.EventSto;
import com.alberto.tfg.tormantos.utils.Helper;
import com.alberto.tfg.tormantos.utils.Strings;

import java.util.Date;

/**
 * Analizer implementation for Firefox Web Browser.
 */
public class FirefoxAnalyzerImpl implements Analyzer {

    private static final String TAG = "FirefoxAnalizer";

    private Context context;

    private FirefoxDto firefoxDto;

    private boolean confirmInput = false;

    public FirefoxAnalyzerImpl(Context context) {
        this.context = context;
    }

    @Override
    public void compute(EventSto eventSto) {
        Helper.log(eventSto);

        switch (eventSto.getClassName()) {
            case Strings.WIDGET_EDITTEXT:
                if (!Strings.KEY_FIREFOX_SEARCH.equals(Helper.getEventText(eventSto.getEvent()))
                        && !("".equals(Helper.getEventText(eventSto.getEvent())))) {
                    firefoxDto = new FirefoxDto();
                    firefoxDto.setSearchUrl(Helper.getEventText(eventSto.getEvent()));

                    if (confirmInput) {
                        firefoxDto.setTimestamp(eventSto.getCaptureInstant());
                        this.storeObjectInRealm();
                        confirmInput = false;
                    }
                }
                break;
            case Strings.WIDGET_FRAME: // A web shortcut has been clicked
                if (!(Strings.KEY_FIREFOX_SEARCH.equals(Helper.getEventText(eventSto.getEvent())))
                        && !("".equals(Helper.getEventText(eventSto.getEvent())))) {
                    confirmInput = true;
                }
                break;
            case Strings.WIDGET_LINEAR_LAYOUT: // // A bookmark is clicked
                if (!(Strings.KEY_FIREFOX_SEARCH.equals(Helper.getEventText(eventSto.getEvent())))
                        && !("".equals(Helper.getEventText(eventSto.getEvent())))) {
                    confirmInput = true;
                }
            default:
                break;
        }
    }

    @Override
    public void storeObjectInRealm() {
        if (firefoxDto != null &&
                firefoxDto.getSearchUrl() != null &&
                firefoxDto.getTimestamp() != null) {

            DBManager.saveOrUpdate(this.firefoxDto);
            Toast.makeText(context, "Stored firefox:\n" + this.firefoxDto.toString(), Toast.LENGTH_LONG).show();

        }
    }

    /**
     * Confirm the user inputs into the editText search
     * field.
     *
     * @param timestamp date where the input has occurred
     */
    public void confirmKeyboardInput(Date timestamp) {
        firefoxDto.setTimestamp(timestamp);
        this.storeObjectInRealm();
    }
}
