package com.example.yg.sms_auto_registration;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FirebaseDB_Schedule {
    private int scheduleNumber;
    private int scheduleGroupNumber;
    private int scheduleClassification;
    private String startDate;
    private String endDate;
    private String scheduleTitle;
    private String scheduleContent;
    private String scheduleWriter;
    private String schedulePlace;
    private int scheduleRepeat;

    public FirebaseDB_Schedule(int scheduleNumber, int scheduleGroupNumber, int scheduleClassification, String startDate, String endDate, String scheduleTitle, String scheduleContent, String scheduleWriter, String schedulePlace, int scheduleRepeat) {
        this.scheduleNumber = scheduleNumber;
        this.scheduleGroupNumber = scheduleGroupNumber;
        this.scheduleClassification = scheduleClassification;
        this.startDate = startDate;
        this.endDate = endDate;
        this.scheduleTitle = scheduleTitle;
        this.scheduleContent = scheduleContent;
        this.scheduleWriter = scheduleWriter;
        this.schedulePlace = schedulePlace;
        this.scheduleRepeat = scheduleRepeat;
    }

    public FirebaseDB_Schedule() {
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("ScheduleNumber", scheduleNumber);
        result.put("ScheduleGroupNumber", scheduleGroupNumber);
        result.put("ScheduleClassification", scheduleClassification);
        result.put("StartDate", startDate);
        result.put("EndDate", endDate);
        result.put("ScheduleTitle", scheduleTitle);
        result.put("ScheduleContent", scheduleContent);
        result.put("ScheduleWriter", scheduleWriter);
        result.put("SchedulePlace", schedulePlace);
        result.put("ScheduleRepeat", scheduleRepeat);

        return result;
    }

    public int getScheduleNumber() {
        return scheduleNumber;
    }

    public void setScheduleNumber(int scheduleNumber) {
        this.scheduleNumber = scheduleNumber;
    }

    public int getScheduleGroupNumber() {
        return scheduleGroupNumber;
    }

    public void setScheduleGroupNumber(int scheduleGroupNumber) {
        this.scheduleGroupNumber = scheduleGroupNumber;
    }

    public int getScheduleClassification() {
        return scheduleClassification;
    }

    public void setScheduleClassification(int scheduleClassification) {
        this.scheduleClassification = scheduleClassification;
    }

    public String getScheduleTitle() {
        return scheduleTitle;
    }

    public void setScheduleTitle(String scheduleTitle) {
        this.scheduleTitle = scheduleTitle;
    }

    public String getScheduleContent() {
        return scheduleContent;
    }

    public void setScheduleContent(String scheduleContent) {
        this.scheduleContent = scheduleContent;
    }

    public String getScheduleWriter() {
        return scheduleWriter;
    }

    public void setScheduleWriter(String scheduleWriter) {
        this.scheduleWriter = scheduleWriter;
    }

    public String getSchedulePlace() {
        return schedulePlace;
    }

    public void setSchedulePlace(String schedulePlace) {
        this.schedulePlace = schedulePlace;
    }


    public int getScheduleRepeat() {
        return scheduleRepeat;
    }

    public void setScheduleRepeat(int scheduleRepeat) {
        this.scheduleRepeat = scheduleRepeat;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
