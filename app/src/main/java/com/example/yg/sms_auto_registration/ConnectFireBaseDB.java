package com.example.yg.sms_auto_registration;


import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConnectFireBaseDB {
    private static DatabaseReference myRef;


    /*---------------------------------------------------------------------------------------------------------------------*/
    //User

    //true 데이터 추가와 업데이트, false데이터 삭제(_userNumber기준으로 삭제)
    public static void postUser(boolean add, String _name, String _googleID, String _UID, String _providerID) {
        myRef = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdate = new HashMap<>();
        Map<String, Object> postValue = null;

        if (add) {
            FirebaseDB_User post = new FirebaseDB_User(_name, _googleID, _UID, _providerID);
            postValue = post.toMap();
        }
        childUpdate.put("/User/" + _UID, postValue);
        myRef.updateChildren(childUpdate);
    }

    public static void UserRead() {
        myRef = FirebaseDatabase.getInstance().getReference();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseDB_User firebaseDB_user = new FirebaseDB_User();
                ArrayList<String> arrayList = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Object objectValue = postSnapshot.getValue();
                    HashMap<String, String> data = (HashMap<String, String>) objectValue;
                    for (String k : data.keySet()) {
                        arrayList.add(data.get(k));
//                        Log.d("UserRead", "data[" + k + "]: " + data.get(k));
                    }
                }
                firebaseDB_user.setProviderID(arrayList.get(0));
                firebaseDB_user.setUserUID(arrayList.get(1));
                firebaseDB_user.setGoogleID(arrayList.get(2));
                firebaseDB_user.setUserName(arrayList.get(3));
                Log.d("UserRead", "getProviderID : " + firebaseDB_user.getProviderID());
                Log.d("UserRead", "getUserUID : " + firebaseDB_user.getUserUID());
                Log.d("UserRead", "getGoogleID : " + firebaseDB_user.getGoogleID());
                Log.d("UserRead", "getUserName : " + firebaseDB_user.getUserName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        };
        Query query = FirebaseDatabase.getInstance().getReference().child("User");
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    /*---------------------------------------------------------------------------------------------------------------------*/
    //Group

    


}