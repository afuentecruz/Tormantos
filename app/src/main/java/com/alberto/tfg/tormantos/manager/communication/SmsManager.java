package com.alberto.tfg.tormantos.manager.communication;

import android.util.Log;

import com.alberto.tfg.tormantos.dto.comunication.SmsDto;

import java.util.List;

import io.realm.Realm;

/**
 * Sms dtos database manager
 */
public class SmsManager {


    /**
     * Save or update an SmsDto object in DB
     * @param smsDto, the SmsDto object to save in realm
     */
    public static void saveOrUpdateSmsDB(SmsDto smsDto){
        Realm realm = Realm.getDefaultInstance(); //instantiate RealmDB
        Log.d("SMSManager", "storing smsDto");
        //Save or update event
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(smsDto);
        realm.commitTransaction();
    }

    /**
     * Returns all the Sms stored objects in Realm
     * @return smsDBList
     * */
    public static List<SmsDto> getAllSmsModels(){
        List<SmsDto> smsDBList;
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        //Find all the sms models
        smsDBList= realm.where(SmsDto.class).findAll();
        realm.commitTransaction();

        return smsDBList;
    }
}
