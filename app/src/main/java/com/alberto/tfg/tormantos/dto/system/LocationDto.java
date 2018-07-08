package com.alberto.tfg.tormantos.dto.system;

import com.alberto.tfg.tormantos.utils.Helper;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Location Dto in order to persist the information about the user location
 */
public class LocationDto extends RealmObject {

    @PrimaryKey
    private String id;

    /**
     * The location latitude value
     */
    Double latitude;

    /**
     * The location longitude value
     */
    Double longitude;

    /**
     * Timestamp when the location has captured
     */
    Date timestamp;

    public LocationDto() {
        this.id = UUID.randomUUID().toString(); //Randomized id.
    }

    public LocationDto(Double latitude, Double longitude, Date timestamp) {
        this.id = UUID.randomUUID().toString(); //Randomized id.
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "LocationDto{" + "\n" +
                "\t" + "latitude: " + latitude + "\n" +
                "\t" + "longitude: " + longitude + "\n" +
                "\t" + "timestamp: " + Helper.formatDate(timestamp) + "\n" +
                '}' + "\n";
    }
}
