package com.alberto.tfg.tormantos.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alberto.tfg.tormantos.R;
import com.alberto.tfg.tormantos.dto.comunication.DialDto;
import com.alberto.tfg.tormantos.dto.comunication.GmailDto;
import com.alberto.tfg.tormantos.dto.comunication.SmsDto;
import com.alberto.tfg.tormantos.dto.system.GeneralAppDto;
import com.alberto.tfg.tormantos.manager.DBManager;
import com.alberto.tfg.tormantos.manager.realmquerys.RealmQuerys;
import com.alberto.tfg.tormantos.utils.Strings;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommunicationFragment extends Fragment {

    /**
     * Dial fields
     */
    @BindView(R.id.tewtview_dial_totaltime)
    TextView textViewDialTime;
    @BindView(R.id.tewtview_dial_total_calls)
    TextView textViewDialTotalCalls;
    @BindView(R.id.tewtview_dial_popular)
    TextView textViewDialPopular;

    /**
     * SMS fields
     */
    @BindView(R.id.tewtview_sms_totaltime)
    TextView textViewSmsTime;
    @BindView(R.id.tewtview_sms_total_sent)
    TextView textViewSmsTotalSent;
    @BindView(R.id.tewtview_sms_popular)
    TextView textViewSmsPopular;

    /**
     * Gmail fields
     */
    @BindView(R.id.tewtview_gmail_totaltime)
    TextView textViewGmailTime;
    @BindView(R.id.tewtview_gmail_total_sent)
    TextView textViewGmailTotalSent;
    @BindView(R.id.tewtview_gmail_popular)
    TextView textViewGmailPopular;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_communication, container, false);
        ButterKnife.bind(this, view);
        loadDialData();
        loadSmsData();
        loadGmailData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDialData();
        loadSmsData();
        loadGmailData();

    }

    private void loadDialData() {

        List<DialDto> dialDtoList = (List<DialDto>) (List) DBManager.getAllObjects(DialDto.class);
        // -- total time spent in dialer
        textViewDialTime.setText(getAppTotalTime(Strings.PACKAGE_DIALER));
        // -- total calls
        textViewDialTotalCalls.setText(Integer.toString(dialDtoList.size()));
        // -- most called contact
        DialDto popularContactInDialer = getPopularContactInDialer(dialDtoList);
        if (popularContactInDialer == null) {
            textViewDialPopular.setText("No tenemos datos suficientes");
        } else {
            textViewDialPopular.setText(popularContactInDialer.getContact());
        }
    }

    private void loadSmsData() {
        List<SmsDto> smsDtoList = (List<SmsDto>) (List) DBManager.getAllObjects(SmsDto.class);
        // -- total time spent in sms app
        textViewSmsTime.setText(getAppTotalTime(Strings.PACKAGE_SMS));
        // -- total sent sms
        textViewSmsTotalSent.setText(Integer.toString(smsDtoList.size()));
        // -- the contact who the user sends more sms
        SmsDto popularContactInDialer = getPopularContactInSms(smsDtoList);
        if (popularContactInDialer == null) {
            textViewSmsPopular.setText("No tenemos datos suficientes");
        } else {
            textViewSmsPopular.setText(popularContactInDialer.getReceivers().get(0));
        }
    }

    private void loadGmailData() {

        List<GmailDto> gmailDtoList = (List<GmailDto>) (List) DBManager.getAllObjects(GmailDto.class);
        // -- total time spent in gmail
        textViewGmailTime.setText(getAppTotalTime(Strings.PACKAGE_GMAIL));
        // -- total mails sent
        textViewGmailTotalSent.setText(Integer.toString(gmailDtoList.size()));
        // -- the mail of the most popular contact
        GmailDto popularContactInGmail = getPopularContactInGmail(gmailDtoList);
        if (popularContactInGmail == null) {
            textViewGmailPopular.setText("No tenemos datos suficientes");
        } else {
            textViewGmailPopular.setText(popularContactInGmail.getReceivers().get(0));
        }

    }

    public String getAppTotalTime(String packageName) {
        long totalTimeOfUse = 0;
        List<GeneralAppDto> generalAppDtoList = RealmQuerys.getAllGeneralAppByPackage(packageName);
        for (GeneralAppDto generalAppDto : generalAppDtoList) {
            long useDiff = generalAppDto.getEndTimestamp().getTime() - generalAppDto.getStartTimestamp().getTime();
            totalTimeOfUse = totalTimeOfUse + useDiff;
        }
        long browserUseSeconds = TimeUnit.MILLISECONDS.toSeconds(totalTimeOfUse);
        long browserUseMinutes = TimeUnit.MILLISECONDS.toMinutes(totalTimeOfUse);
        long browserUseHours = TimeUnit.MILLISECONDS.toHours(totalTimeOfUse);

        return String.format("%d horas, %d minutos, %d segundos.", browserUseHours, browserUseMinutes, browserUseSeconds);
    }

    public DialDto getPopularContactInDialer(List<DialDto> dialDtoList) {
        if (dialDtoList.isEmpty())
            return null;

        int count = 1, tempCount;
        DialDto popular = dialDtoList.get(0);
        DialDto temp = null;
        for (int i = 0; i < (dialDtoList.size() - 1); i++) {
            temp = dialDtoList.get(i);
            tempCount = 0;
            for (int j = 1; j < dialDtoList.size(); j++) {
                if (temp.getContact().equals(dialDtoList.get(j).getContact()))
                    tempCount++;
            }
            if (tempCount > count) {
                popular = temp;
                count = tempCount;
            }
        }
        return popular;
    }

    public SmsDto getPopularContactInSms(List<SmsDto> smsDtoList) {
        if (smsDtoList.isEmpty())
            return null;

        int count = 1, tempCount;
        SmsDto popular = smsDtoList.get(0);
        SmsDto temp = null;
        for (int i = 0; i < (smsDtoList.size() - 1); i++) {
            if (!smsDtoList.get(i).getReceivers().isEmpty()) {

                temp = smsDtoList.get(i);
                tempCount = 0;
                for (int j = 1; j < smsDtoList.size(); j++) {
                    if (temp.getReceivers().get(0).equals(smsDtoList.get(j).getReceivers().get(0)))
                        tempCount++;
                }
                if (tempCount > count) {
                    popular = temp;
                    count = tempCount;
                }
            }
        }
        return popular;
    }

    public GmailDto getPopularContactInGmail(List<GmailDto> gmailDtoList) {
        if (gmailDtoList.isEmpty())
            return null;

        int count = 1, tempCount;
        GmailDto popular = gmailDtoList.get(0);
        GmailDto temp = null;
        for (int i = 0; i < (gmailDtoList.size() - 1); i++) {
            if (!gmailDtoList.get(i).getReceivers().isEmpty()) {

                temp = gmailDtoList.get(i);
                tempCount = 0;
                for (int j = 1; j < gmailDtoList.size(); j++) {
                    if (!gmailDtoList.get(j).getReceivers().isEmpty()) {
                        if (temp.getReceivers().get(0).equals(gmailDtoList.get(j).getReceivers().get(0)))
                            tempCount++;
                    }
                }
                if (tempCount > count) {
                    popular = temp;
                    count = tempCount;
                }
            }
        }
        return popular;
    }

}
