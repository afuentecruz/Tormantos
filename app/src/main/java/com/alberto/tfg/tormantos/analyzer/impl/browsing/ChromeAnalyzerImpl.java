package com.alberto.tfg.tormantos.analyzer.impl.browsing;

import android.view.accessibility.AccessibilityEvent;

import com.alberto.tfg.tormantos.analyzer.Analyzer;
import com.alberto.tfg.tormantos.dto.browsing.ChromeDto;
import com.alberto.tfg.tormantos.manager.DBManager;
import com.alberto.tfg.tormantos.sto.EventSto;
import com.alberto.tfg.tormantos.utils.Helper;
import com.alberto.tfg.tormantos.utils.Strings;

import java.util.Date;

public class ChromeAnalyzerImpl implements Analyzer {

    private ChromeDto chromeDto;

    private String previousSearch = "";


    public ChromeAnalyzerImpl() {}

    public void confirmKeyboardInput(Date timestamp) {
        if (this.chromeDto != null) {
            chromeDto.setTimestamp(timestamp);
            storeObjectInRealm();
        }
    }

    private void handleFrequentSites(EventSto eventSto) {

        if (!"".equals(Helper.getEventText(eventSto.getEvent()))) {
            chromeDto = new ChromeDto(eventSto.getCaptureInstant());
            chromeDto.setSearchUrl(Helper.getEventText(eventSto.getEvent()));
        }
        storeObjectInRealm();
    }

    private void captureSearchString(EventSto eventSto) {
        if (Helper.getEventText(eventSto.getEvent()).length() != 1) {
            if (eventSto.getEvent().getEventType() == AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED) {

                chromeDto = new ChromeDto(eventSto.getCaptureInstant());
                chromeDto.setSearchUrl(Helper.getEventText(eventSto.getEvent()));
            }
        }
    }

    private void captureUrlPrompt(EventSto eventSto) {
        if (eventSto.getEvent().getEventType() == AccessibilityEvent.TYPE_VIEW_CLICKED) {

            String eventText = Helper.getEventText(eventSto.getEvent());
            String promptedUrl = "";
            for (int i = 0; i < eventText.length(); i++) {
                if (Character.isUpperCase(eventText.charAt(i))) {
                    promptedUrl = eventText.substring(0, i);
                    break;
                }
            }

            if (!"".equals(promptedUrl)) {
                chromeDto = new ChromeDto(eventSto.getCaptureInstant());
                chromeDto.setSearchUrl(promptedUrl);
                storeObjectInRealm();
            }


        }
    }

    @Override
    public void compute(EventSto eventSto) {

        switch (eventSto.getClassName()) {
            case Strings.WIDGET_FRAME:
                handleFrequentSites(eventSto);
                break;
            case Strings.WIDGET_EDITTEXT:
                captureSearchString(eventSto);
                break;
            case Strings.VIEW_VIEWGROUP:
                captureUrlPrompt(eventSto);
                break;
            default:
                break;
        }
    }

    @Override
    public void storeObjectInRealm() {
        if ((chromeDto != null) && (!"".equals(chromeDto.getSearchUrl()))) {
            if (!previousSearch.equals(chromeDto.getSearchUrl())) {
                DBManager.saveOrUpdate(this.chromeDto);
                previousSearch = chromeDto.getSearchUrl();
            }
        }
    }
}
