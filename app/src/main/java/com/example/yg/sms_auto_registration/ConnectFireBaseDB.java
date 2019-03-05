package com.example.yg.sms_auto_registration;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConnectFireBaseDB {
    private static DatabaseReference mRootRef;

    public static void write(String _name, int _age){
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mRootRef.child("user").child("name").setValue(_name);
        mRootRef.child("user").child("age").setValue(_age);
    }
}
