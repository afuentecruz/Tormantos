package com.alberto.tfg.tormantos.analyzer.impl.browsing;

import android.content.Context;
import android.widget.Toast;

import com.alberto.tfg.tormantos.analyzer.Analyzer;
import com.alberto.tfg.tormantos.dto.browsing.ChromeDto;
import com.alberto.tfg.tormantos.manager.DBManager;
import com.alberto.tfg.tormantos.sto.EventSto;
import com.alberto.tfg.tormantos.utils.Helper;
import com.alberto.tfg.tormantos.utils.Strings;

public class ChromeAnalyzerImpl implements Analyzer {

    private Context context;

    private ChromeDto chromeDto;

    public ChromeAnalyzerImpl(Context context) {
        this.context = context;
    }

    @Override
    public void compute(EventSto eventSto) {
        Helper.log(eventSto);

        switch (eventSto.getClassName()) {
            case Strings.VIEW_VIEWGROUP:
                chromeDto = new ChromeDto();
                chromeDto.setSearchUrl(formatText(Helper.getEventText(eventSto.getEvent())));
                chromeDto.setTimestamp(eventSto.getCaptureInstant());
                storeObjectInRealm();
                break;
            case Strings.WIDGET_FRAME:
                chromeDto = new ChromeDto();
                chromeDto.setSearchUrl(Helper.getEventText(eventSto.getEvent()));
                chromeDto.setTimestamp(eventSto.getCaptureInstant());
                storeObjectInRealm();
                break;
            default:
                break;
        }

    }

    @Override
    public void storeObjectInRealm() {
        if (chromeDto != null &&
                chromeDto.getSearchUrl() != null &&
                chromeDto.getTimestamp() != null) {

            DBManager.saveOrUpdate(this.chromeDto);
            Toast.makeText(context, "Stored chrome:\n" + this.chromeDto.toString(), Toast.LENGTH_LONG).show();

        }
    }

    private String formatText(String text) {

        String formattedText = text;

        for (int i = 0; i < text.length(); i++) {
            if (Character.isUpperCase(text.charAt(i))) {
                formattedText = text.substring(0, i);
                break;
            }
        }

        return formattedText;
    }
}
