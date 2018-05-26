package com.alberto.tfg.tormantos.dto.comunication;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Gmail Dto object, used to stores the gmail data and persist it in realm as soon as it extends from RealmObject.
 */
public class GmailDto extends RealmObject {

    @PrimaryKey
    private String id;

    /** The mail sender */
    private String sender;

    /** The mail receivers */
    private RealmList<String> receivers;

    /** Mail subject */
    private String subject;

    /** Mail content */
    private String body;

    /** When the mail was sended */
    private Date timestamp;

    public GmailDto() {
        this.id = UUID.randomUUID().toString(); //Randomized id.
        this.receivers = new RealmList<>();
    }


    public GmailDto(String sender, RealmList<String> receivers, String subject, String body, Date timestamp) {
        this.id = UUID.randomUUID().toString(); //Randomized id.
        this.sender = sender;
        this.receivers = receivers;
        this.subject = subject;
        this.body = body;
        this.timestamp = timestamp;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public RealmList<String> getReceivers() {
        return receivers;
    }

    public void setReceivers(RealmList<String> receivers) {
        this.receivers = receivers;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "GmailDto{" + "\n" +
                "\t" + "sender: " + sender + "\n" +
                "\t" + "receivers: " + receivers.toString()+ "\n" +
                "\t" + "subject: " + subject + "\n" +
                "\t" + "body: " + body + "\n" +
                "\t" + "timestamp: " + timestamp + "\n" +
                '}' + "\n" ;
    }
}
