package com.alberto.tfg.tormantos.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alberto.tfg.tormantos.R;
import com.alberto.tfg.tormantos.dto.messaging.TelegramDto;
import com.alberto.tfg.tormantos.dto.messaging.WhatsappDto;
import com.alberto.tfg.tormantos.dto.system.GeneralAppDto;
import com.alberto.tfg.tormantos.manager.DBManager;
import com.alberto.tfg.tormantos.manager.realmquerys.RealmQuerys;
import com.alberto.tfg.tormantos.utils.Strings;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessagingFragment extends Fragment {

    /**
     * Whatsapp fields
     */
    @BindView(R.id.tewtview_whatsapp_totaltime)
    TextView textViewWhatsappTime;
    @BindView(R.id.tewtview_whatsapp_distinct)
    TextView textViewWhatsappDistinct;
    @BindView(R.id.tewtview_whatsapp_popular)
    TextView textViewWhatsappPopular;
    @BindView(R.id.tewtview_whatsapp_total_lines)
    TextView textViewWhatsappTotalLines;

    /**
     * Telegram fields
     */
    @BindView(R.id.tewtview_telegram_totaltime)
    TextView textViewTelegramTime;
    @BindView(R.id.tewtview_telegram_total_sent)
    TextView textViewTelegramTotalSent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_messaging, container, false);
        ButterKnife.bind(this, view);
        loadWhatsappData();
        loadTelegramData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadWhatsappData();
        loadTelegramData();
    }

    private void loadWhatsappData() {

        List<WhatsappDto> whatsappDtoList = (List<WhatsappDto>) (List) DBManager.getAllObjects(WhatsappDto.class);

        // -- total time spent in whatsapp
        textViewWhatsappTime.setText(getAppTotalTime(Strings.PACKAGE_WHATSAPP));

        // -- opened conversations
        Long totalDifferentConversations = RealmQuerys.getWhatsappTotalDifferentConversations();
        textViewWhatsappDistinct.setText(Long.toString(totalDifferentConversations));

        // -- most talked person
        List<WhatsappDto> whatsappDtosListSorted = RealmQuerys.getWhatsappByInterlocutorSorted();
        WhatsappDto popularContact = getPopularContact(whatsappDtosListSorted);
        if (popularContact == null) {
            textViewWhatsappPopular.setText("No tenemos datos suficientes");
        } else {
            textViewWhatsappPopular.setText(popularContact.getInterlocutor());
        }

        // -- total written lines
        textViewWhatsappTotalLines.setText(Long.toString(getTotalWrittenLines(whatsappDtoList)));

    }

    public void loadTelegramData() {

        List<TelegramDto> telegramDtoList = (List<TelegramDto>) (List) DBManager.getAllObjects(TelegramDto.class);

        // -- total time spent in telegram
        textViewTelegramTime.setText(getAppTotalTime(Strings.PACKAGE_TELEGRAM));

        // -- total written lines
        textViewTelegramTotalSent.setText(Integer.toString(telegramDtoList.size()));
    }

    public String getAppTotalTime(String packageName) {
        long totalTimeOfUse = 0;
        List<GeneralAppDto> generalAppDtoList = RealmQuerys.getAllGeneralAppByPackage(packageName);
        for (GeneralAppDto generalAppDto : generalAppDtoList) {
            long useDiff = generalAppDto.getEndTimestamp().getTime() - generalAppDto.getStartTimestamp().getTime();
            totalTimeOfUse = totalTimeOfUse + useDiff;
        }
        long whatsappUseSeconds = TimeUnit.MILLISECONDS.toSeconds(totalTimeOfUse);
        long whatsappUseMinutes = TimeUnit.MILLISECONDS.toMinutes(totalTimeOfUse);
        long whatsappUseHours = TimeUnit.MILLISECONDS.toHours(totalTimeOfUse);

        return String.format("%d horas, %d minutos, %d segundos.", whatsappUseHours, whatsappUseMinutes, whatsappUseSeconds);
    }

    public WhatsappDto getPopularContact(List<WhatsappDto> whatsappDtoList) {
        if (whatsappDtoList.isEmpty())
            return null;

        int count = 1, tempCount;
        WhatsappDto popular = whatsappDtoList.get(0);
        WhatsappDto temp = null;
        for (int i = 0; i < (whatsappDtoList.size() - 1); i++) {
            temp = whatsappDtoList.get(i);
            tempCount = 0;
            for (int j = 1; j < whatsappDtoList.size(); j++) {
                if (!"Whatsapp Home".equals(temp.getInterlocutor()) &&
                        temp.getInterlocutor().equals(whatsappDtoList.get(j).getInterlocutor()))
                    tempCount++;
            }
            if (tempCount > count) {
                popular = temp;
                count = tempCount;
            }
        }
        return popular;
    }

    public long getTotalWrittenLines(List<WhatsappDto> whatsappDtoList) {

        long totalLines = new Long(0);

        for (WhatsappDto whatsappDto : whatsappDtoList) {
            if (!whatsappDto.getTextList().isEmpty()) {
                totalLines += whatsappDto.getTextList().size();
            }
        }

        return totalLines;
    }


}
