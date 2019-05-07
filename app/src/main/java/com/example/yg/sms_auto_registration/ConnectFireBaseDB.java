package com.example.yg.sms_auto_registration;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ConnectFireBaseDB {
    private static DatabaseReference myRef;
    static Integer group_number;

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

        DatabaseReference myRef1= FirebaseDatabase.getInstance().getReference().child("GroupNumber");
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                group_number = (int) (long) dataSnapshot.getValue();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        myRef1.addChildEventListener(childEventListener);
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

                for (int i = 0; i < arrayList.size() / 4; i++) {
                    MyApplication.firebaseDB_user.add(new FirebaseDB_User());
                    MyApplication.firebaseDB_user.get(i).setProviderID(arrayList.get(i * 4));
                    MyApplication.firebaseDB_user.get(i).setUserUID(arrayList.get(i * 4 + 1));
                    MyApplication.firebaseDB_user.get(i).setGoogleID(arrayList.get(i * 4 + 2));
                    MyApplication.firebaseDB_user.get(i).setUserName(arrayList.get(i * 4 + 3));

                    Log.d("UserRead", MyApplication.firebaseDB_user.get(i).getUserUID());
                }

                //Add Group
                final GroupAddSingleton groupAddSingleton = GroupAddSingleton.getInstance();
                groupAddSingleton.addGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("aaaaa", "clicked");
                        String title = groupAddSingleton.title.getText().toString();
                        String[] group_getEdit = groupAddSingleton.inviteEdit.getText().toString().split(",|/|\\s");
                        for (int i = 0; i < group_getEdit.length; i++) {
                            group_getEdit[i].trim();
                        }
                        Arrays.sort(group_getEdit);
                        ArrayList<String> tempUID = new ArrayList<>();

                        for (int i = 0; i < group_getEdit.length; i++) {
                            for (int j = 0; j < MyApplication.firebaseDB_user.size(); j++) {
                                if (group_getEdit[i].equals(MyApplication.firebaseDB_user.get(j).getUserUID())) {
                                    tempUID.add(group_getEdit[i]);
                                }
                            }
                        }
                        Log.d("곽",group_number.toString());
                        ConnectFireBaseDB.GroupNumberRead();
                        ConnectFireBaseDB.postGroup(true, group_number, tempUID, title);
                        ConnectFireBaseDB.postGroupNumber(true, group_number + 1);
                        MyApplication.groupNumber=MyApplication.groupNumber+1;
                        ConnectFireBaseDB.GroupRead();
                    }
                });

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
                ArrayList<FirebaseDB_Group> groupInfo = new ArrayList<>();

                for (int i = 0; i < MyApplication.firebaseDB_groups.size(); i++) {
                    for (int j = 0; j < MyApplication.firebaseDB_groups.get(i).getUserUID().size(); j++) {
                        if (MyApplication.localUser_uid.equals(MyApplication.firebaseDB_groups.get(i).getUserUID().get(j)) && !spinnerArrayList.contains(MyApplication.firebaseDB_groups.get(i).getGroupName())) {
                            spinnerArrayList.add(MyApplication.firebaseDB_groups.get(i).getGroupName());
                            groupInfo.add(MyApplication.firebaseDB_groups.get(i));
                        }
                    }
                    if(i == MyApplication.firebaseDB_groups.size()-1){
                        spinnerArrayList.add("+");
                    }
                }

                Log.d("GroupRead", Integer.toString(MyApplication.firebaseDB_groups.size()));
//                for (int i = 0; i < MyApplication.firebaseDB_groups.size(); i++) {
//                    spinnerArrayList.add(MyApplication.firebaseDB_groups.get(i).getGroupName());
//                }
                final MainSingleton mainSingleton = MainSingleton.getInstance();

                ArrayAdapter<String> adp = new ArrayAdapter<String>(mainSingleton.activity, android.R.layout.simple_list_item_1, spinnerArrayList);

                mainSingleton.spinner.setAdapter(adp);

                mainSingleton.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String itemName = mainSingleton.spinner.getSelectedItem().toString();
                        for (int i = 0; i < MyApplication.firebaseDB_groups.size(); i++) {
                            if (itemName.equals(MyApplication.firebaseDB_groups.get(i).getGroupName())) {
                                MyApplication.currentGroupNum = MyApplication.firebaseDB_groups.get(i).getGroupNumber();
                            }
                        }

                        String text = mainSingleton.spinner.getItemAtPosition(position).toString();
                        mainSingleton.group_btn.setText(text);

                        GroupFragment.set_materialCalendarView.removeDecorators();
                        GroupFragment.set_materialCalendarView.addDecorator(new OndateDecorator());
                        GroupFragment.set_materialCalendarView.addDecorator(new SaturdayDecorator());
                        GroupFragment.set_materialCalendarView.addDecorator(new SundayDecorator());


                        for (int i=0;i<GroupFragment.materialCalendarView_group.size();i++) {
                            if(mainSingleton.group_btn.getText().toString().equals(GroupFragment.materialCalendarView_group.get(i).GroupName)) {
                                for(int j=0;j<GroupFragment.schedule_date.size();j++) {
                                    if(GroupFragment.materialCalendarView_group.get(i).num.equals(GroupFragment.schedule_date.get(j).group_num)) {
                                        GroupFragment.color_group = new int[1];
                                        DayViewDecorator eventDecorator=new EventDecorator(GroupFragment.color_group,GroupFragment.schedule_date.get(j).year,GroupFragment.schedule_date.get(j).month,GroupFragment.schedule_date.get(j).day,1);
                                        GroupFragment.set_materialCalendarView.addDecorator(eventDecorator);
                                        //Log.d("굳",GroupFragment.schedule_date.get(j).year+"-"+GroupFragment.schedule_date.get(j).month+"-"+GroupFragment.schedule_date.get(j).day+""+GroupFragment.schedule_date.get(j).group_num+""+GroupFragment.schedule_date.get(j).sche_title);
                                    }
                                }
                            }
                        }

                        Log.d("spinner", "spinner item: " + mainSingleton.spinner.getSelectedItem().toString());
                        Log.d("spinner", "MyApplication.currentGroupNum: " + Integer.toString(MyApplication.currentGroupNum));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                //그룹캘린더 버튼 텍스트 설정, 수정필요
                if(mainSingleton.spinner.getSelectedItem()==null) {
                    mainSingleton.group_btn.setText("그룹 캘린더");
                }else {
                    String text = mainSingleton.spinner.getSelectedItem().toString();
                    mainSingleton.group_btn.setText(text);
                }
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

    public static void postRevision(Boolean _add, int _revisionNumber, int _revisionGroupNumber, String _revisionDate, String _revisionContent, String _revisionUser) {
        myRef = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdate = new HashMap<>();
        Map<String, Object> postValue = null;
        if (_add) {
            FirebaseDB_RevisionHistory post = new FirebaseDB_RevisionHistory(_revisionNumber, _revisionGroupNumber, _revisionDate, _revisionContent, _revisionUser);
            postValue = post.toMap();
        }
        childUpdate.put("/RevisionHistory/" + _revisionGroupNumber, postValue);
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
                firebaseDB_revisionHistory.setRevisionGroupNumber(revisionScheduleNumber);

                firebaseDB_revisionHistory.setRevisionContent(arrayList.get(4));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        Query query = FirebaseDatabase.getInstance().getReference().child("RevisionHistory");
        query.addListenerForSingleValueEvent(valueEventListener);
    }


    /*---------------------------------------------------------------------------------------------------------------------*/
    //GroupNumber
    public static void postGroupNumber(boolean add, int _num) {
        myRef = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdate = new HashMap<>();
        Map<String, Object> postValue = null;

        if (add) {
            FirebaseDB_GroupNumber post = new FirebaseDB_GroupNumber(_num);
            postValue = post.toMap();
        }

        childUpdate.put("/GroupNumber/", postValue);
        myRef.updateChildren(childUpdate);
    }


    public static void GroupNumberRead() {
        myRef = FirebaseDatabase.getInstance().getReference();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int groupNumber = -1;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Object objectValue = postSnapshot.getValue();
                    groupNumber = (int) (long) objectValue;
                }

                MyApplication.groupNumber = groupNumber;
                Log.d("GroupNumberRead", Integer.toString(MyApplication.groupNumber));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        Query query = FirebaseDatabase.getInstance().getReference().child("GroupNumber");
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    /*---------------------------------------------------------------------------------------------------------------------*/
    //ScheduleNumber
    public static void postScheduleNumber(boolean add, int _num) {
        myRef = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdate = new HashMap<>();
        Map<String, Object> postValue = null;

        if (add) {
            FirebaseDB_ScheduleNumber post = new FirebaseDB_ScheduleNumber(_num);
            postValue = post.toMap();
        }

        childUpdate.put("/ScheduleNumber/", postValue);
        myRef.updateChildren(childUpdate);
    }

    public static void ScheduleNumberRead() {
        myRef = FirebaseDatabase.getInstance().getReference();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer scheduleNum;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    Object objectValue = postSnapshot.getValue();
//                    scheduleNum = (int) (long) objectValue;
                    scheduleNum = (int) (long) postSnapshot.getValue();
                    MyApplication.scheduleNumber = scheduleNum.intValue();
                    Log.d("긁",String.valueOf(scheduleNum));
                }

//                MyApplication.scheduleNumber = scheduleNum.intValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        Query query = FirebaseDatabase.getInstance().getReference().child("ScheduleNumber");
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    /*---------------------------------------------------------------------------------------------------------------------*/
    //GroupUserList

    public static void getUserNameForUserList(final int gn) {
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

                for (int i = 0; i < arrayList.size() / 4; i++) {
                    MyApplication.firebaseDB_user.add(new FirebaseDB_User());
                    MyApplication.firebaseDB_user.get(i).setProviderID(arrayList.get(i * 4));
                    MyApplication.firebaseDB_user.get(i).setUserUID(arrayList.get(i * 4 + 1));
                    MyApplication.firebaseDB_user.get(i).setGoogleID(arrayList.get(i * 4 + 2));
                    MyApplication.firebaseDB_user.get(i).setUserName(arrayList.get(i * 4 + 3));

                    Log.d("UserRead", MyApplication.firebaseDB_user.get(i).getUserUID());
                }

                //Group userList
                ConnectFireBaseDB.GroupRead();
                ArrayList<String> userUIDList = new ArrayList<>();
                for (int i = 0; i < MyApplication.firebaseDB_groups.size(); i++) {
                    if (gn == MyApplication.firebaseDB_groups.get(i).getGroupNumber()) {
                        for (int j = 0; j < MyApplication.firebaseDB_groups.get(i).getUserUID().size(); j++) {
                            if (!userUIDList.contains(MyApplication.firebaseDB_groups.get(i).getUserUID().get(j))) {
                                userUIDList.add(MyApplication.firebaseDB_groups.get(i).getUserUID().get(j));
                            }
                        }
                    }
                }
                ArrayList<String> userNameList = new ArrayList<>();
                for (int i = 0; i < userUIDList.size(); i++) {
                    for (int j = 0; j < MyApplication.firebaseDB_user.size(); j++) {
                        if (userUIDList.get(i).equals(MyApplication.firebaseDB_user.get(j).getUserUID())) {
                            userNameList.add(MyApplication.firebaseDB_user.get(j).getUserName());
                        }
                    }
                }

                final GroupListSingleton groupListSingleton = GroupListSingleton.getInstance();
                ArrayList<GroupUserListItem> groupUserListItems = new ArrayList<>();
                for (int i = 0; i < userNameList.size(); i++) {
                    groupUserListItems.add(new GroupUserListItem(userNameList.get(i)));
                }

                GroupUserListAdapter groupUserListAdapter = new GroupUserListAdapter(groupUserListItems);
                groupListSingleton.mRecyclerView.setAdapter(groupUserListAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        };
        Query query = FirebaseDatabase.getInstance().getReference().child("User");
        query.addListenerForSingleValueEvent(valueEventListener);
    }
}