package com.example.yg.sms_auto_registration;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {
    static List<FirebaseDB_User> firebaseDB_user = new ArrayList<FirebaseDB_User>();
    static List<FirebaseDB_Group> firebaseDB_groups = new ArrayList<FirebaseDB_Group>();
    static List<DeviceEvent> deviceEvents = new ArrayList<DeviceEvent>();

    static int groupNumber;
    static int scheduleNumber;

    static String localUser_name;
    static String localUser_email;
    static String localUser_uid;
    static String localUser_providerId;

    static int currentGroupNum;

    public static String timeForAlarm = "";
    public static String dateForAlarm = "";
    public static Long millis;
    public static Long myBefore = new Long("0");
}
