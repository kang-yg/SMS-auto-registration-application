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


public class AnniversaryAddActivity extends Activity implements View.OnClickListener { //기념일 추가 액티비티
    private Button cancle, complete, layoutDate;
    private EditText layoutTitle;
    private int repeat_num=0;
    Integer scheduleNum; int groupNum;

    private AlarmManager alarmManager;
    private Calendar cal;
    private NotificationManager mNotification;
    AlarmVibrate alarmVibrate;
    int hour, minute;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anniveradd);

        //알람 매니저 획득
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //현재 시각 획득
        cal = Calendar.getInstance();



        Spinner spinner = (Spinner) findViewById(R.id.alram_spinner);
        String[] strs = getResources().getStringArray(R.array.alramspinner_sche);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.alramspinner_item,strs);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Intent intent = getIntent();
        groupNum = intent.getExtras().getInt("GroupNum");
        scheduleNum = intent.getExtras().getInt("ScheduleNumber");
        final String user = MyApplication.localUser_name;

        layoutTitle = (EditText)findViewById(R.id.anniver_title);

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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년MM월dd일");
        String now = sdf.format(new Date());
        String _startDate = now;

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("놔", String.valueOf(adapterView.getSelectedItemPosition()));
                if (adapterView.getSelectedItemPosition() == 0) {
                    MyApplication.myBefore = new Long("0");
                } else if (adapterView.getSelectedItemPosition() == 1) {
                    MyApplication.myBefore = new Long("20000");
                } else if (adapterView.getSelectedItemPosition() == 2) {
                    MyApplication.myBefore = new Long("-86400000");
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
        layoutDate = (Button)findViewById(R.id.datepick);
        layoutDate.setText(_startDate);
        layoutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dFragment = new AnniversaryAddDatePickerFragment();
                dFragment.show(getFragmentManager(), "Date Picker");
            }
        });
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
        complete = (Button)findViewById(R.id.anniver_complete);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                setAlrarm(MyApplication.millis, MyApplication.myBefore);
                Log.d("컄", MyApplication.millis.toString() + "    " + MyApplication.myBefore);

                String title = layoutTitle.getText().toString();
                String date = (String) layoutDate.getText();
                ConnectFireBaseDB.postSchedule(true, scheduleNum, groupNum, 3, date, null, title, null, user, null, repeat_num);
                ConnectFireBaseDB.postScheduleNumber(true,scheduleNum+1);
                Toast.makeText(getApplicationContext(),"기념일 추가 완료", Toast.LENGTH_LONG).show();
                MyApplication.scheduleNumber=MyApplication.scheduleNumber+1;
                finish();
            }
        });

        cancle = (Button)findViewById(R.id.cancel);
        cancle.setOnClickListener(this);

    }

    //알람 시작 함수
    private void setAlrarm(long _millis, long _term) {
        if (MyApplication.myBefore != 0) {
            Intent i = new Intent(getApplicationContext(), AlarmReceiver.class);
            PendingIntent sender = PendingIntent.getBroadcast(AnniversaryAddActivity.this, 0, i, 0);
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
