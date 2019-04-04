package com.example.yg.sms_auto_registration;


import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

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
        Log.d("UserRead", "start UserRead");
        myRef = FirebaseDatabase.getInstance().getReference();

        ValueEventListener valueEventListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<String> arrayList = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Object objectValue = postSnapshot.getValue();
                    HashMap<String, String> data = (HashMap<String, String>) objectValue;
                    for (String k : data.keySet()) {
                        arrayList.add(data.get(k));
//                        Log.d("UserRead", "data[" + k + "]: " + data.get(k));
                    }
                }
/*                for(int i = 0 ; i < arrayList.size()/4 ; i++){
                    MyApplication.setFirebaseDB_user(i,arrayList.get(i*4),arrayList.get(i*4+1),arrayList.get(i*4+2),arrayList.get(i*4+3));
                }*/

                for(int i = 0 ; i < arrayList.size()/4 ; i++){
                    MyApplication.firebaseDB_user.add(new FirebaseDB_User());
                    MyApplication.firebaseDB_user.get(i).setProviderID(arrayList.get(i*4));
                    MyApplication.firebaseDB_user.get(i).setUserUID(arrayList.get(i*4+1));
                    MyApplication.firebaseDB_user.get(i).setGoogleID(arrayList.get(i*4+2));
                    MyApplication.firebaseDB_user.get(i).setUserName(arrayList.get(i*4+3));

                    Log.d("UserRead", MyApplication.firebaseDB_user.get(i).getUserUID());

                }

/*                firebaseDB_user.setProviderID(arrayList.get(0));
                firebaseDB_user.setUserUID(arrayList.get(1));
                firebaseDB_user.setGoogleID(arrayList.get(2));
                firebaseDB_user.setUserName(arrayList.get(3));*/
/*                for(int i = 0 ; i < arrayList.size()/4 ; i++) {
                    Log.d("UserRead", "getProviderID : " + MyApplication.firebaseDB_user[i].getProviderID());
                    Log.d("UserRead", "getUserUID : " + MyApplication.firebaseDB_user[i].getUserUID());
                    Log.d("UserRead", "getGoogleID : " + MyApplication.firebaseDB_user[i].getGoogleID());
                    Log.d("UserRead", "getUserName : " + MyApplication.firebaseDB_user[i].getUserName());
                }*/

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

                for (int p = 0; p < arrayList.size() / 3; p++) {
                    MyApplication.firebaseDB_groups.add(new FirebaseDB_Group());
                    MyApplication.firebaseDB_groups.get(p).setGroupName(arrayList.get(p * 3));
                    Object objectValue = arrayList.get(p * 3 + 1);
                    ArrayList<String> arrayUID = (ArrayList<String>) objectValue;
                    for (int j = 0; j < arrayUID.size(); j++) {
                        MyApplication.firebaseDB_groups.get(p).setUserUID(arrayUID.get(j));
                    }
                    int groupNum = Integer.parseInt(String.valueOf(arrayList.get(p * 3 + 2)));
                    MyApplication.firebaseDB_groups.get(p).setGroupNumber(groupNum);

                }

                //Add GroupCalendar spinner
                ArrayList<String> spinnerArrayList = new ArrayList<>();

                Log.d("GroupRead", Integer.toString(MyApplication.firebaseDB_groups.size()));
                for(int i = 0 ; i < MyApplication.firebaseDB_groups.size() ; i++){
                    spinnerArrayList.add(MyApplication.firebaseDB_groups.get(i).getGroupName());
                }
                final MainSingleton mainSingleton = MainSingleton.getInstance();

                ArrayAdapter<String> adp = new ArrayAdapter<String> (mainSingleton.activity, android.R.layout.simple_spinner_dropdown_item, spinnerArrayList);

                mainSingleton.spinner.setAdapter(adp);

                mainSingleton.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("spinner", Long.toString(mainSingleton.spinner.getItemIdAtPosition(position)));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                //그룹캘린더 버튼 텍스트 설정, 수정필요
                String text = mainSingleton.spinner.getSelectedItem().toString();
                mainSingleton.group_btn.setText(text);

/*                firebaseDB_group.setGroupName(arrayList.get(0));

                Object objectValue = arrayList.get(1);
                ArrayList<String> arrayUID = (ArrayList<String>) objectValue;
                for (int i = 0; i < arrayUID.size(); i++) {
                    firebaseDB_group.setUserUID(arrayUID.get(i));
                }

                int groupNum = Integer.parseInt(String.valueOf(arrayList.get(2)));
                firebaseDB_group.setGroupNumber(groupNum);*/

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

    public static void postRevision(Boolean _add, int _revisionNumber, int _revisionScheduleNumber, String _revisionDate, String _revisionContent, String _revisionUser) {
        myRef = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdate = new HashMap<>();
        Map<String, Object> postValue = null;
        if (_add) {
            FirebaseDB_RevisionHistory post = new FirebaseDB_RevisionHistory(_revisionNumber, _revisionScheduleNumber, _revisionDate, _revisionContent, _revisionUser);
            postValue = post.toMap();
        }
        childUpdate.put("/RevisionHistory/" + _revisionNumber, postValue);
        myRef.updateChildren(childUpdate);
    }

    public static void RevisionRead() {
        myRef = FirebaseDatabase.getInstance().getReference();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FirebaseDB_RevisionHistory firebaseDB_revisionHistory = new FirebaseDB_RevisionHistory();
                ArrayList<String> arrayList = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Object objectValue = postSnapshot.getValue();
                    HashMap<String, String> data = (HashMap<String, String>) objectValue;
                    for (String k : data.keySet()) {
                        arrayList.add(data.get(k));
                    }
                }
                firebaseDB_revisionHistory.setRevisionDate(arrayList.get(0));

                firebaseDB_revisionHistory.setRevisionUser(arrayList.get(1));

                int revisionNumber = Integer.parseInt(String.valueOf(arrayList.get(2)));
                firebaseDB_revisionHistory.setRevisionNumber(revisionNumber);

                int revisionScheduleNumber = Integer.parseInt(String.valueOf(arrayList.get(3)));
                firebaseDB_revisionHistory.setRevisionScheduleNumber(revisionScheduleNumber);

                firebaseDB_revisionHistory.setRevisionContent(arrayList.get(4));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        Query query = FirebaseDatabase.getInstance().getReference().child("RevisionHistory");
        query.addListenerForSingleValueEvent(valueEventListener);
    }

}