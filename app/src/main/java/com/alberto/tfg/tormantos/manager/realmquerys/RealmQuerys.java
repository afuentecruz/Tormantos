package com.alberto.tfg.tormantos.manager.realmquerys;

import com.alberto.tfg.tormantos.dto.messaging.WhatsappDto;
import com.alberto.tfg.tormantos.dto.system.GeneralAppDto;

import java.util.List;

import io.realm.Realm;

public class RealmQuerys {

    public static List<GeneralAppDto> getAllGeneralAppByPackage(String packageName) {
        List<GeneralAppDto> realmObjectList;
        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        //Find all the chrome models
        realmObjectList = realm.where(GeneralAppDto.class).equalTo("packageName", packageName).findAll();
        realm.commitTransaction();

        return realmObjectList;
    }

    public static Long getWhatsappTotalDifferentConversations(){
        List<WhatsappDto> realmObjectList;

        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        Long numDistinct = realm.where(WhatsappDto.class).distinct("interlocutor").count();
        realm.commitTransaction();

        return numDistinct;
    }

    public static List<WhatsappDto> getWhatsappByInterlocutorSorted(){
        List<WhatsappDto> realmObjectList;

        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();
        realmObjectList= realm.where(WhatsappDto.class).sort("interlocutor").findAll();
        realm.commitTransaction();

        return realmObjectList;
    }
}
