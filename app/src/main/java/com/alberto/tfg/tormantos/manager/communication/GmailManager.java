package com.alberto.tfg.tormantos.manager.communication;

import com.alberto.tfg.tormantos.dto.comunication.GmailDto;

import java.util.List;

import io.realm.Realm;

/**
 * Gmail dto database manager
 */
public class GmailManager {

    private static final String TAG ="GmailManager";

    /**
     * Save or update an GmailDto object in DB
     * @param gmailDto, GmailDto object to save in realm
     */
    public static void saveOrUpdateGmailDB(GmailDto gmailDto){
        Realm realm = Realm.getDefaultInstance(); //instantiate RealmDB

        //Save or update event
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(gmailDto);
        realm.commitTransaction();
    }

    /**
     * getAllGmailModels, returns all the GmailDB objects saved in Realm
     * @return gmailDBList
     * */
    public static List<GmailDto> getAllGmailModels(){
        List<GmailDto> gmailDBList;
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        //Find all the gmail models
        gmailDBList = realm.where(GmailDto.class).findAll();
        realm.commitTransaction();

        return gmailDBList;
    }
}
