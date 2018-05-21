package com.alberto.tfg.tormantos.manager.messaging;

import android.util.Log;

import com.alberto.tfg.tormantos.dto.messaging.WhatsappDto;

import java.util.List;

import io.realm.Realm;

/**
 * Whatsapp model database manager
 */
public class WhatsappManager {

    public static final String TAG = "WhatsappManager";

    /**
     * Save or updates a whatsappDto object in DB
     * @param whatsappDto, object to be saved or updated
     */
    public static void saveOrUpdateWhatsappDB(WhatsappDto whatsappDto){
        Log.d(TAG, "SaveOrUpdateWhatsappDB" + whatsappDto.toString());
        Realm realm = Realm.getDefaultInstance();

        //Save or update whatsappDB
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(whatsappDto);
        realm.commitTransaction();
    }

    public static List<WhatsappDto> getAllWhatsappModels(){
        List<WhatsappDto> whatsappDBList;
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        whatsappDBList = realm.where(WhatsappDto.class).findAll();
        realm.commitTransaction();

        return whatsappDBList;
    }

}
