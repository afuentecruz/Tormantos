package com.alberto.tfg.tormantos.dto.messaging;

import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Whatsapp Dto object, used to persist in realm as soon as it extends from RealmObject.
 */
public class WhatsappDto extends RealmObject{

    @PrimaryKey
    private String id;

    private String interlocutor;

    private RealmList<String> textList;

    // Describes the start time when the user clicked a conversation
    private String timestampStart;

    // Describes the end time when the user abandoned a conversation
    private String timestampEnd;

    public WhatsappDto(){

        this.id = UUID.randomUUID().toString(); //Randomized id.
    }

    public WhatsappDto(String id, String interlocutor, RealmList<String> textList, String timestampStart, String timestampEnd) {
        this.id = id;
        this.interlocutor = interlocutor;
        this.textList = textList;
        this.timestampStart = timestampStart;
        this.timestampEnd = timestampEnd;
    }
}
