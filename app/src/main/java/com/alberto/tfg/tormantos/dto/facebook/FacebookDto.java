package com.alberto.tfg.tormantos.dto.facebook;

import com.alberto.tfg.tormantos.utils.Helper;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * The FacebookDto object, in order to stores the comments done in the app
 */
public class FacebookDto extends RealmObject {


    @PrimaryKey
    private String id;

    private String comment;

    private String searchKey;

    private Date timestamp;

    public FacebookDto() {
        this.id = UUID.randomUUID().toString(); //Randomized id.
    }

    public FacebookDto(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "FacebookDto{" + "\n" +
                "\t" + "comment: " + comment + "\n" +
                "\t" + "searchKey: " + searchKey + "\n" +
                "\t" + "timestamp: " + Helper.formatDate(timestamp) + "\n" +
                '}' + "\n";
    }
}
