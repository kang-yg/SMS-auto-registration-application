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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Group_TodoAddActivity extends Activity implements View.OnClickListener { //할일 추가 액티비티
    private Button cancel, complete;
    private Button layoutDate;
    private EditText layoutTitle, layoutContent;
    private int repeat_num=0;
    private Button starttime;
    private Button endtime;
    int hour, minute;
    String _startTime, _endTime, _startDate;
    Integer scheduleNum; int groupNum;
    TimePickerFragment fragment;
    TimePickerFragments fragments;

    private AlarmManager alarmManager;
    private Calendar cal;
    private NotificationManager mNotification;
    AlarmVibrate alarmVibrate;
    int before = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doadd);

        //알람 매니저 획득
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //현재 시각 획득
        cal = Calendar.getInstance();

        Intent intent = getIntent();
        groupNum = intent.getExtras().getInt("GroupNum");
        scheduleNum = intent.getExtras().getInt("ScheduleNumber");
        final String user = MyApplication.localUser_name;
        final Button repeat_one = (Button) findViewById(R.id.repeat_one);
        final Button repeat_two=(Button) findViewById(R.id.repeate_two);

        repeat_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeat_num=1;
            }
        });

        repeat_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeat_num=2;
            }
        });

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        int ampm = c.get(Calendar.AM_PM);
        String sAMPM = ampm == Calendar.AM ? "AM" : "PM";
        starttime = findViewById(R.id.start_time);
        endtime = findViewById(R.id.end_time);
        fragment = new TimePickerFragment();
        fragments = new TimePickerFragments();

        Spinner spinner = (Spinner) findViewById(R.id.alram_spinner);
        String[] strs = getResources().getStringArray(R.array.alramspinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.alramspinner_item,strs);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("놔",String.valueOf(adapterView.getSelectedItemPosition()));
                if (adapterView.getSelectedItemPosition() == 0) {
                    before = 0;
                } else if (adapterView.getSelectedItemPosition() == 1) {
                    before = 20000;
                } else if (adapterView.getSelectedItemPosition() == 2) {
                    before = -900000;
                } else{
                    before = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        if (hour >= 12) {
            hour = hour - 12;
        }

        starttime.setText(sAMPM + " " + hour + " : " + String.format("%02d", minute));
        _startTime = sAMPM + " " + hour + ":" + String.format("%02d", minute);
        endtime.setText(sAMPM + " " + hour + " : " + String.format("%02d", minute));
        _endTime = sAMPM + " " + hour + ":" + String.format("%02d", minute);

        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "timePicker");
            }
        });

        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogFragment newFragment = new TimePickerFragments();
                newFragment.show(getFragmentManager(), "timePicker");
            }
        });
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년MM월dd일");
        String now = sdf.format(new Date());
        String _startDate = now;

        String[] year_arr = now.split("년");
        String[] month_arr = year_arr[1].split("월");
        String[] day_arr = month_arr[1].split("일");

        _startDate = year_arr[0] + "-" + month_arr[0] + "-" + day_arr[0] ;

        layoutDate = (Button)findViewById(R.id.datepick);
        layoutDate.setText(_startDate);
        layoutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dFragment = new TodoAddDatePickerFragment();
                dFragment.show(getFragmentManager(), "Date Picker");
            }
        });

        Log.d("MyApplication", MyApplication.dateForAlarm);
        Log.d("MyApplication", MyApplication.timeForAlarm);

        layoutTitle = (EditText)findViewById(R.id.doAdd_title);
        layoutContent = (EditText)findViewById(R.id.doAdd_content);

        cancel = (Button)findViewById(R.id.cancel);
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
        complete = (Button)findViewById(R.id.doAdd_complete);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //시간 날짜 지정
//                Log.d("CheckMyApplicationTime", MyApplication.dateForAlarm + MyApplication.timeForAlarm);
                String str = MyApplication.dateForAlarm + " " + MyApplication.timeForAlarm;
                Log.d("핫", MyApplication.dateForAlarm + " "+MyApplication.timeForAlarm);
                Log.d("핫", str);
                SimpleDateFormat abc = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                try {
                    Date date = abc.parse(str);
                    MyApplication.millis = date.getTime();
                    Log.d("컄", MyApplication.millis.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                setAlrarm(MyApplication.millis, before);
                Log.d("컄", MyApplication.millis.toString()+"    "+ before);


                String title = layoutTitle.getText().toString();
                String content = layoutContent.getText().toString();
                String date = (String) layoutDate.getText();
                ConnectFireBaseDB.postSchedule(true, scheduleNum, groupNum, 2, date, null, title, content, user, null, repeat_num);
                ConnectFireBaseDB.postScheduleNumber(true,scheduleNum+1);
                Toast.makeText(getApplicationContext(),"할일추가 완료", Toast.LENGTH_LONG).show();
                MyApplication.scheduleNumber=MyApplication.scheduleNumber+1;
                finish();
            }
        });
    }

    //알람 시작 함수
    private void setAlrarm(long _millis, long _term) {
        if (before != 0) {
            Intent i = new Intent(getApplicationContext(), AlarmReceiver.class);
            PendingIntent sender = PendingIntent.getBroadcast(Group_TodoAddActivity.this, 0, i, 0);
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
