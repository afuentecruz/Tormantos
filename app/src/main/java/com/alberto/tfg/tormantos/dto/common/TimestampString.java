package com.alberto.tfg.tormantos.dto.common;

import java.util.Date;

/**
 * Common object that includes a string and her timestamp
 */
public class TimestampString {

    private String content;

    private Date timestamp;

    public TimestampString(){}

    public TimestampString(String content, Date timestamp){
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
