package com.example.yg.sms_auto_registration;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddGroupSchedule extends Activity implements View.OnClickListener { //일정추가 액티비티

    private EditText editTitel;
    private EditText editContent;
    private EditText editPlace;

    private Button start_date_pick;
    private Button end_date_pick;
    private Button complete;
    private Button cancel;

    private int repeat_num = 0;
    private Button repeat_one, repeat_two;
    private int y, m, d;
    Integer scheduleNum; int groupNum;
     DayViewDecorator eventDecorator ;

    private AlarmManager alarmManager;
    private Calendar cal;
    private NotificationManager mNotification;
    AlarmVibrate alarmVibrate;
    int before = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scheadd);
        Log.d("mina", "aaaa");

        //알람 매니저 획득
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //현재 시각 획득
        cal = Calendar.getInstance();

        Intent intent = getIntent();
        groupNum = intent.getExtras().getInt("GroupNum");
        scheduleNum = intent.getExtras().getInt("ScheduleNumber");
        final String user = MyApplication.localUser_name;
        repeat_one = (Button) findViewById(R.id.repeat_one);
        repeat_two = (Button) findViewById(R.id.repeate_two);
        repeat_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeat_num = 1;
            }
        });
        repeat_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeat_num = 2;
            }
        });

        editTitel = (EditText) findViewById(R.id.scheadd_title);
        editContent = (EditText) findViewById(R.id.edit_content);
        editPlace = (EditText) findViewById(R.id.scheadd_place);

        Spinner spinner = (Spinner) findViewById(R.id.alram_spinner);
        String[] strs = getResources().getStringArray(R.array.alramspinner_sche);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.alramspinner_item,strs);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //editTitel.setText(autoTitle);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년MM월dd일");
        String now = sdf.format(new Date());
        String _startDate = now;

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("놔", String.valueOf(adapterView.getSelectedItemPosition()));
                if (adapterView.getSelectedItemPosition() == 0) {
                    before = 0;
                } else if (adapterView.getSelectedItemPosition() == 1) {
                    before = 20000;
                } else if (adapterView.getSelectedItemPosition() == 2) {
                    before = -86400000;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String[] year_arr = now.split("년");
        String[] month_arr = year_arr[1].split("월");
        String[] day_arr = month_arr[1].split("일");

            _startDate = year_arr[0] + "-" + month_arr[0] + "-" + day_arr[0] ;
        start_date_pick = (Button) findViewById(R.id.sta_datepick);

        start_date_pick.setText(_startDate);
        start_date_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dFragment = new DatePickerFragment();
                dFragment.show(getFragmentManager(), "Date Picker");
            }
        });

        String _endDate = now;


        String[] year_arr1 = now.split("년");
        String[] month_arr1 = year_arr[1].split("월");
        String[] day_arr1 = month_arr[1].split("일");
        _endDate = year_arr1[0] + "-" + month_arr1[0] + "-" + day_arr1[0] ;

        end_date_pick = (Button) findViewById(R.id.end_datepick);
        end_date_pick.setText(_endDate);
        end_date_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dFragment = new DatePickerFragments();
                dFragment.show(getFragmentManager(), "Date Picker");
            }
        });


        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(this);

        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference().child("ScheduleNumber");
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               scheduleNum = (int) (long) dataSnapshot.getValue();
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
        myRef.addChildEventListener(childEventListener);
        complete = (Button) findViewById(R.id.scheadd_complete);
        complete.setOnClickListener(new View.OnClickListener() {
            StringBuffer buffer01 = null;
            StringBuffer buffer02 = null;
            String title, content, place;

            @Override
            public void onClick(View v) {
                Log.d("mina", "aaaa");
                try {
                    title = editTitel.getText().toString();
                    content = editContent.getText().toString();
                    place = editPlace.getText().toString();
                    String startStr = (String) start_date_pick.getText();
                    String endStar = (String) end_date_pick.getText();

                    String arrText1[] = startStr.split("-");
                    startStr = "";
                    String arrText2[] = endStar.split("-");
                    endStar = "";

                    for (int i = 0; i < arrText1.length; i++) {
                        startStr += arrText1[i];
                        endStar += arrText2[i];
                    }
                    //시간 날짜 지정
//                Log.d("CheckMyApplicationTime", MyApplication.dateForAlarm + MyApplication.timeForAlarm);
                    String str = MyApplication.dateForAlarm;
                    Log.d("핫", MyApplication.dateForAlarm);
                    Log.d("핫", str);
                    SimpleDateFormat abc = new SimpleDateFormat("yyyy-MM-dd");

                    try {
                        Date date = abc.parse(str);
                        MyApplication.millis = date.getTime();

                        Log.d("컄", MyApplication.millis.toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    setAlrarm(MyApplication.millis, before);
                    Log.d("컄", MyApplication.millis.toString() + "    " + before);


                    int tempText1 = Integer.parseInt(startStr);
                    int tempText2 = Integer.parseInt(endStar);

                    if (tempText1 < tempText2 || tempText1 == tempText2) {
                        System.out.println("tempText1 > tempText2 : " + tempText1 + " > " + tempText2);
                        buffer01 = new StringBuffer(Integer.toString(tempText1));
                        buffer01.insert(4, "-");
                        buffer01.insert(7, "-");
                        buffer02 = new StringBuffer(Integer.toString(tempText2));
                        buffer02.insert(4, "-");
                        buffer02.insert(7, "-");
                        ConnectFireBaseDB.postSchedule(true, scheduleNum, groupNum, 1, buffer01.toString(), buffer02.toString(), title, content, user, place, repeat_num);
                        ConnectFireBaseDB.postScheduleNumber(true, scheduleNum+1);
                        Toast.makeText(getApplicationContext(), "일정추가 완료", Toast.LENGTH_LONG).show();
                        MyApplication.scheduleNumber=MyApplication.scheduleNumber+1;

                        finish();
                    } else {
                        System.out.println("tempText1 < tempText2 : " + tempText1 + " < " + tempText2);
                        buffer01 = new StringBuffer(Integer.toString(tempText1));
                        buffer01.insert(4, "-");
                        buffer01.insert(7, "-");

                        end_date_pick.setText(buffer01.toString());
                        Toast.makeText(getApplicationContext(), "시작일 종료일 확인", Toast.LENGTH_LONG).show();
                    }

//                    ConnectFireBaseDB.postSchedule(true, scheduleNum, groupNum, 3, startStr, endStar, title, content, "영규", place, 4);
//                    ConnectFireBaseDB.postScheduleNumber(true,scheduleNum+1);
//                    Toast.makeText(getApplicationContext(),"일정추가 완료", Toast.LENGTH_LONG).show();
                } catch (Exception E) {
                    E.printStackTrace();
                }
            }
        });

    }

    //알람 시작 함수
    private void setAlrarm(long _millis, long _term) {
        if (before != 0) {
            Intent i = new Intent(getApplicationContext(), AlarmReceiver.class);
            PendingIntent sender = PendingIntent.getBroadcast(AddGroupSchedule.this, 0, i, 0);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _millis + _term, 0, sender);
        }
    }
    public void backWard(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
