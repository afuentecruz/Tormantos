package com.alberto.tfg.tormantos.dto.system;

import com.alberto.tfg.tormantos.utils.Helper;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * GeneralAppDto, object in order to hold the data of all theses apps
 * that we are not listening but must capture the start and end timestamp of use.
 * Also her package name.
 */
public class GeneralAppDto extends RealmObject {

    @PrimaryKey
    private String id;

    /**
     * The moment when the user started the app use
     */
    private Date startTimestamp;

    /**
     * The moment when the user ended the app use
     */
    private Date endTimestamp;

    /**
     * The packagename of the used app
     */
    private String packageName;

    public GeneralAppDto() {
        this.id = UUID.randomUUID().toString(); //Randomized id.
    }

    public GeneralAppDto(Date startTimestamp) {
        this.id = UUID.randomUUID().toString(); //Randomized id.
        this.startTimestamp = startTimestamp;
    }

    public Date getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Date startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public Date getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(Date endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        return "GeneralAppDto{" + "\n" +
                "\t" + "app: " + packageName + "\n" +
                "\t" + "start time: " + Helper.formatDate(startTimestamp) + "\n" +
                "\t" + "end time: " + Helper.formatDate(endTimestamp) + "\n" +
                '}' + "\n";
    }
}
