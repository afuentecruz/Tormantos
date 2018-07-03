package com.alberto.tfg.tormantos.manager;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;

public class DBManager {

    /**
     * Save or update a Realm object in DB
     *
     * @param realmObject, RealmObject instance to be saved
     */
    public static void saveOrUpdate(RealmObject realmObject) {
        Realm realm = Realm.getDefaultInstance(); //instantiate RealmDB

        //Save or update event
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(realmObject);
        realm.commitTransaction();
    }

    /**
     * Returns all stored objects saved in Realm from a specific class
     *
     * @return realmObjectList, a list with all the stored realm objects
     */
    public static List<RealmObject> getAllObjects(Class objectClass) {
        List<RealmObject> realmObjectList;
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        //Find all the chrome models
        realmObjectList = realm.where(objectClass).findAll();
        realm.commitTransaction();

        return realmObjectList;
    }
}
