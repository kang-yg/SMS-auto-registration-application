package com.example.yg.sms_auto_registration;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FirebaseDB_Group {
    private String groupNumber;
    private ArrayList<String> userUID = new ArrayList<>();
    private String groupName;

    public FirebaseDB_Group(String groupNumber, String groupName) {
        this.groupNumber = groupNumber;
        this.groupName = groupName;
    }

    public FirebaseDB_Group() {
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("GroupNumber", groupNumber);
        result.put("GroupName", groupName);

        return result;
    }

    public ArrayList<String> getUserUID() {
        return userUID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }


    public void setUserUID(String _userUID) {
        this.userUID.add(_userUID);
    }
}
