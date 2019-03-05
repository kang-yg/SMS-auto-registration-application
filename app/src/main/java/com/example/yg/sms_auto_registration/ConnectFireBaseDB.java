package com.example.yg.sms_auto_registration;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConnectFireBaseDB {
    private static DatabaseReference mRootRef;

    public static void write(){
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mRootRef.child("user").child("name").setValue("도봉순");
    }
}
