package com.alberto.tfg.tormantos.manager.browsing;

import com.alberto.tfg.tormantos.dto.browsing.ChromeDto;

import java.util.List;

import io.realm.Realm;

/**
 * Chrome dto database manager
 */
public class ChromeManager {

    private static final String TAG = "ChromeManager";

    /**
     * Save or update an ChromeDto object in DB
     *
     * @param chromeDto, ChromeDto object to save in realm
     */
    public static void saveOrUpdateChromeDB(ChromeDto chromeDto) {
        Realm realm = Realm.getDefaultInstance(); //instantiate RealmDB

        //Save or update event
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(chromeDto);
        realm.commitTransaction();
    }

    /**
     * Returns all the ChromeDto objects saved in Realm
     *
     * @return chromeDBList
     */
    public static List<ChromeDto> getAllChromeModels() {
        List<ChromeDto> chromeDBList;
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        //Find all the chrome models
        chromeDBList = realm.where(ChromeDto.class).findAll();
        realm.commitTransaction();

        return chromeDBList;
    }
}
