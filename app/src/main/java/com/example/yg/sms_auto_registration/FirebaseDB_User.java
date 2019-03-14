package com.example.yg.sms_auto_registration;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class FirebaseDB_User {
    private String userName;
    private String googleID;

    public FirebaseDB_User(){}

    public FirebaseDB_User(String _userName, String _googleID) {
        this.userName = _userName;
        this.googleID = _googleID;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Name", userName);
        result.put("GoogleID", googleID);
        return result;
    }

    public String getUserName() {
        return userName;
    }

    public String getGoogleID() {
        return googleID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setGoogleID(String googleID) {
        this.googleID = googleID;
    }
}
