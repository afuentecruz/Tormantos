package com.alberto.tfg.tormantos.dto.common;

import com.alberto.tfg.tormantos.utils.Helper;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Common object that includes a string and her timestamp
 */
public class TimestampString extends RealmObject {

    private String text;

    private Date timestamp;

    public TimestampString(){}

    public TimestampString(String text, Date timestamp){
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        /*
                return "{" + '\n' +
                '\t' +'\t' + "content: " + content + '\n' +
                '\t' +'\t' + "timestamp: " + timestamp + '\n' +
                '\t' + '}' + '\n';

         */
        return "{" + "\n"+
                "\t" + "\t" + "text: " + text + "\n" +
                "\t" + "\t" + "timestamp: " + Helper.formatDate(timestamp) + "\n" +
                "\t" + '}' + "\n";
    }
}
