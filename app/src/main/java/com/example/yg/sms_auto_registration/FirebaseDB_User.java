package com.example.yg.sms_auto_registration;

import java.util.HashMap;
import java.util.Map;

public class FirebaseDB_User {
    public String userName;
    public String googleID;

    public FirebaseDB_User(String _userName, String _googleID) {
        this.userName = _userName;
        this.googleID = _googleID;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Name", userName);
        result.put("GoogleID", googleID);
        return result;
    }

}
