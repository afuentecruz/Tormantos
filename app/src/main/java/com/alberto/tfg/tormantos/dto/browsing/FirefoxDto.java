package com.alberto.tfg.tormantos.dto.browsing;

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

    private String searchUrl;

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
                "\t" + "timestamp:" + timestamp + "\n" +
                '}' + "\n";
    }
}
