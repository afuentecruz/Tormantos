package com.alberto.tfg.tormantos.analyzer.impl.system;

import com.alberto.tfg.tormantos.analyzer.Analyzer;
import com.alberto.tfg.tormantos.dto.system.NotificationDto;
import com.alberto.tfg.tormantos.manager.DBManager;
import com.alberto.tfg.tormantos.sto.EventSto;
import com.alberto.tfg.tormantos.utils.Helper;

/**
 * Analyzer implementation for Android System Notifications.
 */
public class NotificationAnalyzerImpl implements Analyzer {

    private static final String TAG = "NotificationAnalyzer";

    private NotificationDto notificationDto;

    public NotificationAnalyzerImpl() {

    }

    public String getNotificationContent(EventSto eventSto) {

        String content = Helper.getEventText(eventSto.getEvent());
        String senderName = null;
        if (content.startsWith("Mensaje de")) {
            senderName = content.substring(content.indexOf("Mensaje de "), content.length());
            System.out.println("notificador: " + senderName);
        }
        return senderName;
    }

    @Override
    public void compute(EventSto eventSto) {

        if (!"".equals(Helper.getEventText(eventSto.getEvent()))) {
            notificationDto = new NotificationDto();
            notificationDto.setNotificationContent(Helper.getEventText(eventSto.getEvent()));
            notificationDto.setSourcePackage(eventSto.getPackageName());
            notificationDto.setTimestamp(eventSto.getCaptureInstant());
            storeObjectInRealm();
        }
    }

    @Override
    public void storeObjectInRealm() {
        if (notificationDto != null &&
                !"".equals(notificationDto.getNotificationContent()) &&
                !"".equals(notificationDto.getSourcePackage())) {
            DBManager.saveOrUpdate(this.notificationDto);
        }
    }
}
