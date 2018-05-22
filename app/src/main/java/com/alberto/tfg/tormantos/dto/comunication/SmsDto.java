package com.alberto.tfg.tormantos.dto.comunication;


import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Sms Dto object, used to stores the sms data and persist it in realm as soon as it extends from RealmObject.
 */
public class SmsDto extends RealmObject{

    @PrimaryKey
    private String id;

    /** Sms content body */
    private String content;

    /** Sms receivers */
    private RealmList<String> receivers;

    /** Sms send timestamp */
    private Date sendTimestamp;

    public SmsDto() {
        this.id = UUID.randomUUID().toString(); //Randomized id.
        this.receivers = new RealmList<>();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public RealmList<String> getReceivers() {
        return receivers;
    }

    public void setReceivers(RealmList<String> receivers) {
        this.receivers = receivers;
    }

    public Date getSendTimestamp() {
        return sendTimestamp;
    }

    public void setSendTimestamp(Date sendTimestamp) {
        this.sendTimestamp = sendTimestamp;
    }

    @Override
    public String toString() {
        return "SmsDto{" + "\n" +
                "\t" + "content: " + content + "\n" +
                "\t" + "receivers: " + Arrays.toString(receivers.toArray()) + "\n" +
                "\t" + "sendTimestamp: " + sendTimestamp + "\n" +
                "}" + "\n";
    }
}
