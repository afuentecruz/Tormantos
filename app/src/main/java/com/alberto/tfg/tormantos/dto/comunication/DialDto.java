package com.alberto.tfg.tormantos.dto.comunication;

import com.alberto.tfg.tormantos.utils.Helper;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * The dial data dto. Stores the contact who the user is calling to, the timestamp start
 * and the timestamp end.
 */
public class DialDto extends RealmObject {

    @PrimaryKey
    private String id;

    /**
     * Name of the contact who the user decided to call
     */
    private String contact;

    /**
     * Timestamp when the call started
     */
    private Date timestampStart;

    /**
     * Timestamp when the call ended
     */
    private Date timestampEnd;

    public DialDto() {
        this.id = UUID.randomUUID().toString(); //Randomized id.
    }

    public DialDto(Date timestampStart) {
        this.id = UUID.randomUUID().toString(); //Randomized id.
        this.timestampStart = timestampStart;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Date getTimestampStart() {
        return timestampStart;
    }

    public void setTimestampStart(Date timestampStart) {
        this.timestampStart = timestampStart;
    }

    public Date getTimestampEnd() {
        return timestampEnd;
    }

    public void setTimestampEnd(Date timestampEnd) {
        this.timestampEnd = timestampEnd;
    }

    @Override
    public String toString() {
        return "DialDto{" + "\n" +
                "\t" + "contact: " + contact + "\n" +
                "\t" + "timestampStart: " + Helper.formatDate(timestampStart) + "\n" +
                "\t" + "timestampEnd: " + Helper.formatDate(timestampEnd) + "\n" +
                '}' + "\n";
    }
}
