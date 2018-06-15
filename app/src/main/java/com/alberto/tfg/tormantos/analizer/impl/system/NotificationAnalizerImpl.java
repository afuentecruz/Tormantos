package com.alberto.tfg.tormantos.analizer.impl.system;

import android.content.Context;
import android.widget.Toast;

import com.alberto.tfg.tormantos.analizer.Analizer;
import com.alberto.tfg.tormantos.dto.NotificationDto;
import com.alberto.tfg.tormantos.manager.system.NotificationManager;
import com.alberto.tfg.tormantos.sto.EventSto;
import com.alberto.tfg.tormantos.utils.Helper;

/**
 * Analizer implementation for Android System Notifications.
 */
public class NotificationAnalizerImpl implements Analizer{

    private static final String TAG = "NotificationAnalizer";

    private NotificationDto notificationDto;

    private Context context;

    public NotificationAnalizerImpl(Context context){
        this.context = context;
    }

    public String getNotificationContent(EventSto eventSto){

        String content = Helper.getEventText(eventSto.getEvent());
        String senderName = null;
        if(content.startsWith("Mensaje de")){
            senderName = content.substring(content.indexOf("Mensaje de "), content.length());
            System.out.println("notificador: " + senderName);
        }
        return senderName;
    }

    @Override
    public void compute(EventSto eventSto) {

        if(!"".equals(Helper.getEventText(eventSto.getEvent()))){
            notificationDto = new NotificationDto();
            notificationDto.setNotificationContent(Helper.getEventText(eventSto.getEvent()));
            notificationDto.setSourcePackage(eventSto.getPackageName());
            notificationDto.setTimestamp(eventSto.getCaptureInstant());
            storeObjectInRealm();
        }
    }

    @Override
    public void storeObjectInRealm() {
        if(notificationDto != null &&
                !"".equals(notificationDto.getNotificationContent()) &&
                !"".equals(notificationDto.getSourcePackage())){
            NotificationManager.saveOrUpdateNotificationDB(this.notificationDto);
            Toast.makeText(context, "Stored whatsapp:\n" + this.notificationDto.toString(), Toast.LENGTH_LONG).show();

        }
    }
}
