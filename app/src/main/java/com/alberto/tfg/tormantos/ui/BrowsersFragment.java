package com.alberto.tfg.tormantos.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alberto.tfg.tormantos.R;
import com.alberto.tfg.tormantos.dto.browsing.ChromeDto;
import com.alberto.tfg.tormantos.dto.browsing.FirefoxDto;
import com.alberto.tfg.tormantos.dto.system.GeneralAppDto;
import com.alberto.tfg.tormantos.manager.DBManager;
import com.alberto.tfg.tormantos.manager.realmquerys.RealmQuerys;
import com.alberto.tfg.tormantos.utils.Strings;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BrowsersFragment extends Fragment {

    @BindView(R.id.tewtview_chrome_totaltime)
    TextView textViewChromeTime;

    @BindView(R.id.tewtview_chrome_total_pages)
    TextView textViewChromeTotalPages;

    @BindView(R.id.tewtview_chrome_popular)
    TextView textViewChromePopular;

    @BindView(R.id.tewtview_firefox_totaltime)
    TextView textViewFirefoxTime;

    @BindView(R.id.tewtview_firefox_total_pages)
    TextView textViewFirefoxTotalPages;

    @BindView(R.id.tewtview_firefox_popular)
    TextView textViewFirefoxPopular;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_browsers, container, false);
        ButterKnife.bind(this, view);
        loadChromeData();
        loadFirefoxData();

        return view;
    }

    public void loadChromeData() {

        List<ChromeDto> chromeDtoList = (List<ChromeDto>) (List) DBManager.getAllObjects(ChromeDto.class);
        // -- total time spent in chrome
        textViewChromeTime.setText(getBrowserTotalTime(Strings.PACKAGE_CHROME));
        // -- total visited pages
        textViewChromeTotalPages.setText(Integer.toString(chromeDtoList.size()));
        // -- most visited page
        ChromeDto popularChromeUrl = getPopularPageInChrome(chromeDtoList);
        if (popularChromeUrl == null) {
            textViewChromePopular.setText("No tenemos datos suficientes");
        } else {
            textViewChromePopular.setText(popularChromeUrl.getSearchUrl());
        }
    }

    private void loadFirefoxData() {

        List<FirefoxDto> firefoxDtoList = (List<FirefoxDto>) (List) DBManager.getAllObjects(FirefoxDto.class);
        // -- total time spent in firefox
        textViewFirefoxTime.setText(getBrowserTotalTime(Strings.PACKAGE_FIREFOX));
        // -- total visited pages
        textViewFirefoxTotalPages.setText(Integer.toString(firefoxDtoList.size()));
        // -- most visited page
        FirefoxDto popularFirefoxUrl = getPopularPageInFirefox(firefoxDtoList);
        if (popularFirefoxUrl == null) {
            textViewFirefoxPopular.setText("No tenemos datos suficientes");
        } else {
            textViewFirefoxPopular.setText(popularFirefoxUrl.getSearchUrl());
        }

    }

    public ChromeDto getPopularPageInChrome(List<ChromeDto> chromeDtoList) {
        if (chromeDtoList.isEmpty())
            return null;

        int count = 1, tempCount;
        ChromeDto popular = chromeDtoList.get(0);
        ChromeDto temp = null;
        for (int i = 0; i < (chromeDtoList.size() - 1); i++) {
            temp = chromeDtoList.get(i);
            tempCount = 0;
            for (int j = 1; j < chromeDtoList.size(); j++) {
                if (temp.getSearchUrl().equals(chromeDtoList.get(j).getSearchUrl()))
                    tempCount++;
            }
            if (tempCount > count) {
                popular = temp;
                count = tempCount;
            }
        }
        return popular;
    }

    public FirefoxDto getPopularPageInFirefox(List<FirefoxDto> firefoxDtoList) {
        if (firefoxDtoList.isEmpty())
            return null;

        int count = 1, tempCount;
        FirefoxDto popular = firefoxDtoList.get(0);
        FirefoxDto temp = null;
        for (int i = 0; i < (firefoxDtoList.size() - 1); i++) {
            temp = firefoxDtoList.get(i);
            tempCount = 0;
            for (int j = 1; j < firefoxDtoList.size(); j++) {
                if (temp.getSearchUrl().equals(firefoxDtoList.get(j).getSearchUrl()))
                    tempCount++;
            }
            if (tempCount > count) {
                popular = temp;
                count = tempCount;
            }
        }
        return popular;
    }

    public String getBrowserTotalTime(String packageName) {
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
}
