package com.example.yg.sms_auto_registration;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class FirebaseDB_User {
    private String userName;
    private String googleID;
    private String userUID;
    private String providerID;


    public FirebaseDB_User(){}

    public FirebaseDB_User(String _userName, String _googleID, String _UID, String _providerID) {
        this.userName = _userName;
        this.googleID = _googleID;
        this.userUID = _UID;
        this.providerID = _providerID;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Name", userName);
        result.put("GoogleID", googleID);
        result.put("ProviderId", providerID);
        result.put("UID", userUID);

        return result;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGoogleID() {
        return googleID;
    }

    public void setGoogleID(String googleID) {
        this.googleID = googleID;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getProviderID() {
        return providerID;
    }

    public void setProviderID(String providerID) {
        this.providerID = providerID;
    }
}
