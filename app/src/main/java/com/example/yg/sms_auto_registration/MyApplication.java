package com.example.yg.sms_auto_registration;

import android.app.Application;

public class MyApplication extends Application {
    static FirebaseDB_User[] firebaseDB_user = new FirebaseDB_User[50];
    static int userCount = 0 ;

    static String localUser_name;
    static String localUser_email;
    static String localUser_uid;
    static String localUser_providerId;
}
