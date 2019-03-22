package com.example.yg.sms_auto_registration;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class FirebaseDB_RevisionHistory {
    private int revisionNumber;
    private int revisionScheduleNumber;
    private String revisionDate;
    private String revisionContent;
    private String revisionUser;

    public FirebaseDB_RevisionHistory(int revisionNumber, int revisionScheduleNumber, String revisiondate, String revisionContent, String revisionUser) {
        this.revisionNumber = revisionNumber;
        this.revisionScheduleNumber = revisionScheduleNumber;
        this.revisionDate = revisiondate;
        this.revisionContent = revisionContent;
        this.revisionUser = revisionUser;
    }

    public FirebaseDB_RevisionHistory() {
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("RevisionNumber", revisionNumber);
        result.put("RevisionScheduleNumber", revisionScheduleNumber);
        result.put("RevisionDate", revisionDate);
        result.put("RevisionContent", revisionContent);
        result.put("RevisionUser", revisionUser);

        return result;
    }

    public int getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(int revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public int getRevisionScheduleNumber() {
        return revisionScheduleNumber;
    }

    public void setRevisionScheduleNumber(int revisionScheduleNumber) {
        this.revisionScheduleNumber = revisionScheduleNumber;
    }

    public String getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(String revisionDate) {
        this.revisionDate = revisionDate;
    }

    public String getRevisionContent() {
        return revisionContent;
    }

    public void setRevisionContent(String revisionContent) {
        this.revisionContent = revisionContent;
    }

    public String getRevisionUser() {
        return revisionUser;
    }

    public void setRevisionUser(String revisionUser) {
        this.revisionUser = revisionUser;
    }
}
