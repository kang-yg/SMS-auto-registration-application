package com.example.yg.sms_auto_registration;


import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ConnectFireBaseDB {
    private static DatabaseReference myRef;

    //true 데이터 추가와 업데이트, false데이터 삭제(_userNumber기준으로 삭제)
    public static void postFirebaseDB(boolean add, int _userNumber, String _name, String _googleID) {
        myRef = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdate = new HashMap<>();
        Map<String, Object> postValue = null;

        if (add) {
            FirebaseDB_User post = new FirebaseDB_User(_name, _googleID);
            postValue = post.toMap();
        }
        childUpdate.put("/User/" + _userNumber, postValue);
        myRef.updateChildren(childUpdate);
    }

    public static void UserRead() {
        myRef = FirebaseDatabase.getInstance().getReference();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, Object> mMap = new HashMap<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    String key = postSnapshot.getKey();
                    Object objectValue = postSnapshot.getValue();
                    HashMap<String, String> data = (HashMap<String, String>) objectValue;
                    for (String k : data.keySet()) {
                        FirebaseDB_User firebaseDB_user = new FirebaseDB_User();
                        firebaseDB_user.setGoogleID(k);
                        firebaseDB_user.setUserName(data.get(k));
                        //Log.d("UserRead", "data[" + k + "]: " + data.get(k));
                        Log.d("UserRead", "firebaseDB_user.googleID : " + firebaseDB_user.getGoogleID());
                        Log.d("UserRead", "firebaseDB_user.userName : " + firebaseDB_user.getUserName());
                    }
                    Log.d("UserRead", "key: " + key);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        Query query = FirebaseDatabase.getInstance().getReference().child("User");
        query.addListenerForSingleValueEvent(valueEventListener);
    }
}