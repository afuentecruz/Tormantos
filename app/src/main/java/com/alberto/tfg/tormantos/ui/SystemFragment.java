package com.alberto.tfg.tormantos.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alberto.tfg.tormantos.R;
import com.alberto.tfg.tormantos.dto.system.NotificationDto;
import com.alberto.tfg.tormantos.manager.DBManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SystemFragment extends Fragment {

    /**
     * Notification fields
     */
    @BindView(R.id.tewtview_notifications_distinct)
    TextView textViewNotificationsTotal;
    @BindView(R.id.tewtview_notifications_popular)
    TextView textViewNoitificationPopular;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_system, container, false);
        ButterKnife.bind(this, view);
        loadNotificationsData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadNotificationsData();
    }

    private void loadNotificationsData() {

        List<NotificationDto> notificationDtoList = (List<NotificationDto>) (List) DBManager.getAllObjects(NotificationDto.class);

        // -- total received notifications
        textViewNotificationsTotal.setText(Integer.toString(notificationDtoList.size()));

        // -- app that received more notifications
        NotificationDto popularAppInNotifications = getPopularAppInNotifications(notificationDtoList);
        if (popularAppInNotifications== null) {
            textViewNoitificationPopular.setText("No tenemos datos suficientes");
        } else {
            textViewNoitificationPopular.setText(popularAppInNotifications.getSourcePackage());
        }
    }



    public NotificationDto getPopularAppInNotifications(List<NotificationDto> notificationDtoList) {
        if (notificationDtoList.isEmpty())
            return null;

        int count = 1, tempCount;
        NotificationDto popular = notificationDtoList.get(0);
        NotificationDto temp = null;
        for (int i = 0; i < (notificationDtoList.size() - 1); i++) {
            temp = notificationDtoList.get(i);
            tempCount = 0;
            for (int j = 1; j < notificationDtoList.size(); j++) {
                if (temp.getSourcePackage().equals(notificationDtoList.get(j).getSourcePackage()))
                    tempCount++;
            }
            if (tempCount > count) {
                popular = temp;
                count = tempCount;
            }
        }
        return popular;
    }
}
