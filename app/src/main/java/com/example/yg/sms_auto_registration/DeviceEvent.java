package com.example.yg.sms_auto_registration;

public class DeviceEvent {
    private int calendar_id;
    private String title;
    private String eventLocation;
    private String description;
    private String dtstart;
    private String dtend;

    public DeviceEvent(int calendar_id, String title, String eventLocation, String description, String dtstart, String dtend) {
        this.calendar_id = calendar_id;
        this.title = title;
        this.eventLocation = eventLocation;
        this.description = description;
        this.dtstart = dtstart;
        this.dtend = dtend;
    }

    public DeviceEvent() {
    }

    public int getCalendar_id() {
        return calendar_id;
    }

    public void setCalendar_id(int calendar_id) {
        this.calendar_id = calendar_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDtstart() {
        return dtstart;
    }

    public void setDtstart(String dtstart) {
        this.dtstart = dtstart;
    }

    public String getDtend() {
        return dtend;
    }

    public void setDtend(String dtend) {
        this.dtend = dtend;
    }
}
