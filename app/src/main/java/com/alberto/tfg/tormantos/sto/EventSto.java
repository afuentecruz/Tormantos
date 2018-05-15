package com.alberto.tfg.tormantos.sto;

import android.view.accessibility.AccessibilityEvent;

import java.util.Date;

/**
 * Accessibility event Simple Transfer Objects.
 *
 * Includes the capture timestamp, her package name and the own event.
 */
public class EventSto {

    /** Capture timestamp */
    Date captureInstant;

    /** Event package name */
    String packageName;

    /** Event className */
    String className;

    /** The event */
    AccessibilityEvent event;

    public EventSto(){
    }

    public EventSto(AccessibilityEvent event, Date captureInstant, String packageName, String className){
        this.event = event;
        this.captureInstant = captureInstant;
        this.packageName = packageName;
        this.className = className;
    }

    public Date getCaptureInstant() {
        return captureInstant;
    }

    public void setCaptureInstant(Date captureInstant) {
        this.captureInstant = captureInstant;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public AccessibilityEvent getEvent() {
        return event;
    }

    public void setEvent(AccessibilityEvent event) {
        this.event = event;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}

