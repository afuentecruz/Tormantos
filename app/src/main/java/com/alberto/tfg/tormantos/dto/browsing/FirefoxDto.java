package com.alberto.tfg.tormantos.dto.browsing;

import com.alberto.tfg.tormantos.utils.Helper;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Firefox Browser Dto object, used to store the firefox
 * interaction data and persist it in realm as soon as it extends from RealmObject.
 */
public class FirefoxDto extends RealmObject {

    @PrimaryKey
    private String id;

    /** String containing the visited urls or search terms */
    private String searchUrl;

    /** Timestamp when the search happened */
    private Date timestamp;

    public FirefoxDto() {
        this.id = UUID.randomUUID().toString(); //Randomized id.
    }

    public String getSearchUrl() {
        return searchUrl;
    }

    public void setSearchUrl(String searchUrl) {
        this.searchUrl = searchUrl;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "FirefoxDto{" + "\n" +
                "\t" + "searchUrl: " + searchUrl + "\n" +
                "\t" + "timestamp:" + Helper.formatDate(timestamp) + "\n" +
                '}' + "\n";
    }
}
