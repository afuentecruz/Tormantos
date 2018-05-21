package com.alberto.tfg.tormantos.dto.messaging;

import com.alberto.tfg.tormantos.dto.common.TimestampString;
import com.alberto.tfg.tormantos.utils.Helper;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Whatsapp Dto object, used to store the whatsapp interaction data and persist it in realm as soon as it extends from RealmObject.
 */
public class WhatsappDto extends RealmObject {

    @PrimaryKey
    private String id;

    private String interlocutor;

    private RealmList<TimestampString> textList;

    // Describes the start time when the user clicked a conversation
    private Date startTimestamp;

    // Describes the end time when the user abandoned a conversation
    private Date endTimestamp;

    public WhatsappDto() {

        this.id = UUID.randomUUID().toString(); //Randomized id.
        this.textList = new RealmList<>();
    }

    public WhatsappDto(Date startTimestamp) {
        this.id = UUID.randomUUID().toString(); //Randomized id.
        this.startTimestamp = startTimestamp;
        textList = new RealmList<>();
    }

    public WhatsappDto(String id, String interlocutor, RealmList<TimestampString> textList, Date startTimestamp, Date endTimestamp) {
        this.id = id;
        this.interlocutor = interlocutor;
        this.textList = textList;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
    }

    public String getInterlocutor() {
        return interlocutor;
    }

    public void setInterlocutor(String interlocutor) {
        this.interlocutor = interlocutor;
    }

    public RealmList<TimestampString> getTextList() {
        return textList;
    }

    public Date getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Date startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public Date getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(Date endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    @Override
    public String toString() {
        /*
         return "WhatsappDB{" + "\n" +
                '\t' + "id: " + id + "\n" +
                '\t' + "interlocutor: " + interlocutor + "\n" +
                '\t' + "timestamp Start: " + timestampStart + "\n" +
                '\t' + "timestamp End: " + timestampEnd + "\n" +
                '\t' + "textList: " +  Arrays.toString(textList.toArray()) +
                '}' + "\n";
         */
        return "WhatsappDto{" + "\n" +
                "\t" + "id: " + id + "\n" +
                "\t" + "interlocutor: " + interlocutor + "\n" +
                "\t" + "start: " + Helper.formatDate(startTimestamp) + "\n" +
                "\t" + "end: " + Helper.formatDate(endTimestamp) + "\n" +
                "\t" + "textList: " + Arrays.toString(textList.toArray()) + "\n" +
                '}' + "\n";
    }
}
