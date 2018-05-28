package com.alberto.tfg.tormantos.manager.browsing;

import com.alberto.tfg.tormantos.dto.browsing.FirefoxDto;

import java.util.List;

import io.realm.Realm;

/**
 * Firefox dto database manager
 */
public class FirefoxManager {

    private static final String TAG ="FirefoxManager";

    /**
     * Save or update an FirefoxDto object in DB
     * @param firefoxDto, FirefoxDto  object to save in realm
     */
    public static void saveOrUpdateFirefoxDB(FirefoxDto firefoxDto){
        Realm realm = Realm.getDefaultInstance(); //instantiate RealmDB

        //Save or update event
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(firefoxDto);
        realm.commitTransaction();
    }

    /**
     * Returns all the FirefoxDto objects saved in Realm
     * @return firefoxDBList
     * */
    public static List<FirefoxDto> getAllFirefoxModels(){
        List<FirefoxDto> firefoxDBList;
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        //Find all the gmail models
        firefoxDBList = realm.where(FirefoxDto.class).findAll();
        realm.commitTransaction();

        return firefoxDBList;
    }
}
