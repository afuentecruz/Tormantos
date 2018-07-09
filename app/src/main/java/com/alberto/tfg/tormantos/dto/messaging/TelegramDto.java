package com.alberto.tfg.tormantos.dto.messaging;

import com.alberto.tfg.tormantos.utils.Helper;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TelegramDto extends RealmObject {

    @PrimaryKey
    private String id;

    /**
     * The sended message
     */
    private String message;

    /**
     * Instant when the message was sent
     */
    private Date timestamp;

    public TelegramDto() {
        this.id = UUID.randomUUID().toString(); //Randomized id.
    }

    public TelegramDto(String message, Date timestamp) {
        this.id = UUID.randomUUID().toString(); //Randomized id.
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "TelegramDto{" + "\n" +
                "\t" + "message: " + message + "\n" +
                "\t" + "timestamp: " + Helper.formatDate(timestamp) + "\n" +
                '}' + "\n";
    }
}
