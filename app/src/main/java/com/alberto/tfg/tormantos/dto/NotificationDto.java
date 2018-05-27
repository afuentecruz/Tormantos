package com.alberto.tfg.tormantos.dto;

import com.alberto.tfg.tormantos.utils.Helper;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Notification Dto object, used to stores all the system
 * displayed notifications data and persist it in realm as soon as it extends from RealmObject.
 */
public class NotificationDto extends RealmObject{

    @PrimaryKey
    private String id;

    private String sourcePackage;

    private String notificationContent;

    private Date timestamp;

    public NotificationDto() {
        this.id = UUID.randomUUID().toString(); //Randomized id.
        sourcePackage = "";
        notificationContent = "";
    }

    public String getSourcePackage() {
        return sourcePackage;
    }

    public void setSourcePackage(String sourcePackage) {
        this.sourcePackage = sourcePackage;
    }

    public String getNotificationContent() {
        return notificationContent;
    }

    public void setNotificationContent(String notificationContent) {
        this.notificationContent = notificationContent;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "NotificationDto{" + "\n" +
                "\t" + "sourcePackage: " + sourcePackage + "\n" +
                "\t" + "notificationContent: " + notificationContent + "\n" +
                "\t" + "timestamp: " + Helper.formatDate(timestamp) + "\n" +
                '}' +"\n" ;
    }
}
