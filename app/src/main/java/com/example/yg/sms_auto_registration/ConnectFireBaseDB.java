package com.example.yg.sms_auto_registration;


import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
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
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        Query query = FirebaseDatabase.getInstance().getReference().child("User");
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    /*---------------------------------------------------------------------------------------------------------------------*/
    //Group

    public static void postGroup(boolean add, int _groupNumber, ArrayList<String> _userUID, String _groupName) {
        myRef = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdate = new HashMap<>();
        Map<String, Object> postValue = null;

        if (add) {
            FirebaseDB_Group post = new FirebaseDB_Group(_groupNumber, _groupName);
            postValue = post.toMap();
            postValue.put("UserUID", _userUID);
        }
        childUpdate.put("/Group/" + _groupNumber, postValue);
        myRef.updateChildren(childUpdate);
    }

    public static void GroupRead() {
        myRef = FirebaseDatabase.getInstance().getReference();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseDB_Group firebaseDB_group = new FirebaseDB_Group();
                ArrayList<String> arrayList = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Object objectValue = postSnapshot.getValue();
                    HashMap<String, String> data = (HashMap<String, String>) objectValue;
                    for (String k : data.keySet()) {
                        arrayList.add(data.get(k));
                    }
                }

                firebaseDB_group.setGroupName(arrayList.get(0));

                Object objectValue = arrayList.get(1);
                ArrayList<String> arrayUID = (ArrayList<String>) objectValue;
                for (int i = 0; i < arrayUID.size(); i++) {
                    firebaseDB_group.setUserUID(arrayUID.get(i));
                }

                int groupNum = Integer.parseInt(String.valueOf(arrayList.get(2)));
                firebaseDB_group.setGroupNumber(groupNum);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        Query query = FirebaseDatabase.getInstance().getReference().child("Group");
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    /*---------------------------------------------------------------------------------------------------------------------*/
    //Schedule

    public static void postSchedule(boolean _add, int _scheduleNumber, int _scheduleGroupNumber, int _scheduleClassification, String _startDate, String _endDate, String _scheduleTitle, String _scheduleContent, String _scheduleWriter, String _schedulePlace, int _scheduleRepeat) {
        myRef = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdate = new HashMap<>();
        Map<String, Object> postValue = null;
        if (_add) {
            FirebaseDB_Schedule post = new FirebaseDB_Schedule(_scheduleNumber, _scheduleGroupNumber, _scheduleClassification, _startDate, _endDate, _scheduleTitle, _scheduleContent, _scheduleWriter, _schedulePlace, _scheduleRepeat);
            postValue = post.toMap();
        }
        childUpdate.put("/GroupSchedule/" + _scheduleNumber, postValue);
        myRef.updateChildren(childUpdate);
    }

    public static void ScheduleRead() {
        myRef = FirebaseDatabase.getInstance().getReference();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseDB_Schedule firebaseDB_schedule = new FirebaseDB_Schedule();
                ArrayList<String> arrayList = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Object objectValue = postSnapshot.getValue();
                    HashMap<String, String> data = (HashMap<String, String>) objectValue;
                    for (String k : data.keySet()) {
                        arrayList.add(data.get(k));
                    }
                }
                firebaseDB_schedule.setStartDate(arrayList.get(0));

                firebaseDB_schedule.setScheduleTitle(arrayList.get(1));

                int scheduleNumber = Integer.parseInt(String.valueOf(arrayList.get(2)));
                firebaseDB_schedule.setScheduleNumber(scheduleNumber);

                int scheduleGroupNumber = Integer.parseInt(String.valueOf(arrayList.get(3)));
                firebaseDB_schedule.setScheduleGroupNumber(scheduleGroupNumber);

                firebaseDB_schedule.setScheduleContent(arrayList.get(4));

                int scheduleRepeat = Integer.parseInt(String.valueOf(arrayList.get(5)));
                firebaseDB_schedule.setScheduleRepeat(scheduleRepeat);

                firebaseDB_schedule.setScheduleWriter(arrayList.get(6));

                int scheduleClassification = Integer.parseInt(String.valueOf(arrayList.get(7)));
                firebaseDB_schedule.setScheduleClassification(scheduleClassification);

                firebaseDB_schedule.setSchedulePlace(arrayList.get(8));

                firebaseDB_schedule.setEndDate(arrayList.get(9));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        Query query = FirebaseDatabase.getInstance().getReference().child("GroupSchedule");
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    /*---------------------------------------------------------------------------------------------------------------------*/
    //Revision history


}