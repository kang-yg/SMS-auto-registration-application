package com.example.yg.sms_auto_registration;

import java.util.HashMap;
import java.util.Map;

public class FirebaseDB_GroupNumber {
    private int groupNumber;

    public FirebaseDB_GroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    public FirebaseDB_GroupNumber() {

    }

    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("num", groupNumber);

        return result;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }
}
