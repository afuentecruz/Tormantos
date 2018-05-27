package com.alberto.tfg.tormantos.manager.system;

import android.util.Log;

import com.alberto.tfg.tormantos.dto.NotificationDto;

import java.util.List;

import io.realm.Realm;

/**
 * Notification Dto database manager
 */
public class NotificationManager {

    private static final String TAG ="NotificationManager";


    /**
     * Save or updates a NotificationDto object in DB
     * @param notificationDto, object to be saved or updated
     */
    public static void saveOrUpdateNotificationDB(NotificationDto notificationDto){
        Log.d(TAG, "SaveOrUpdateNotificationDB" + notificationDto.toString());
        Realm realm = Realm.getDefaultInstance();

        //Save or update whatsappDB
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(notificationDto);
        realm.commitTransaction();
    }

    /**
     * Returns all the NotificationDto objects saved in Realm
     * @return notificationDBList
     * */
    public static List<NotificationDto> getAllNotificationModels(){
        List<NotificationDto> notificationDBList;
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        notificationDBList = realm.where(NotificationDto.class).findAll();
        realm.commitTransaction();

        return notificationDBList;
    }
}
