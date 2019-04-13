package com.example.yg.sms_auto_registration;

import java.util.HashMap;
import java.util.Map;

public class FirebaseDB_ScheduleNumber {
    private int scheduleNum;


    public FirebaseDB_ScheduleNumber(int _num) {
        this.scheduleNum = _num;
    }

    public FirebaseDB_ScheduleNumber() {

    }

    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("num", scheduleNum);

        return result;
    }

    public int getScheduleNum() {
        return scheduleNum;
    }

    public void setScheduleNum(int scheduleNum) {
        this.scheduleNum = scheduleNum;
    }
}
