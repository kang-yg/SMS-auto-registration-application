package com.example.yg.sms_auto_registration;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.prolificinteractive.materialcalendarview.DayViewDecorator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TodoAddActivity extends Activity implements View.OnClickListener { //할일 추가 액티비티

    private static LocalDB database;
    private Button confirm;
    private Button cancel;
    private EditText edit_title;
    private EditText edit_content;
    private Button starttime;
    private Button endtime;
    private Button date_pick;
    private Button repeat_one,repeat_two;private int repeat_num=0;
    int hour, minute;
    String _startTime, _endTime, _startDate;
    Personal_TimePickerFragment fragment;
    Personal_TimePickerFragments fragments;
    oneDatePickerFragment datePickerFragment;
    DayViewDecorator eventDecorator ;
    private Spinner alram_spinner;

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


        cal.set(Calendar.MINUTE, fragment.minute);
        cal.set(Calendar.HOUR, fragment.hour_of_12_hour_format);


        hour = cal.get(Calendar.HOUR);
        minute = cal.get(Calendar.MINUTE);

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        int ampm = c.get(Calendar.AM_PM);
        String sAMPM = ampm == Calendar.AM ? "AM" : "PM";

        repeat_one= (Button)findViewById(R.id.repeat_one);
        repeat_two= (Button)findViewById(R.id.repeat_one);
        alram_spinner = (Spinner) findViewById(R.id.alram_spinner);
        edit_title = (EditText) findViewById(R.id.doAdd_title);
        edit_content = (EditText) findViewById(R.id.doAdd_content);
        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        confirm = (Button) findViewById(R.id.doAdd_complete);
        starttime = findViewById(R.id.start_time);
        endtime = findViewById(R.id.end_time);
        date_pick = findViewById(R.id.datepick);
        repeat_one = findViewById(R.id.repeat_one);
        repeat_two = findViewById(R.id.repeate_two);

        // datePickerFragment=new DatePickerFragment();
        fragment = new Personal_TimePickerFragment();
        fragments = new Personal_TimePickerFragments();

        if (hour >= 12) {
            hour = hour - 12;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년MM월d일");
        String now = sdf.format(new Date());
        _startDate = now;


        String[] year_arr = now.split("년");
        String[] month_arr = year_arr[1].split("월");
        String[] day_arr = month_arr[1].split("일");
        if(day_arr[0].equals("01")) {
            day_arr[0]="1";
            _startDate = year_arr[0] + "년" + month_arr[0] + "월" + day_arr[0] + "일";
        }else if(day_arr[0].equals("02")) {
            day_arr[0]="2";
            _startDate = year_arr[0] + "년" + month_arr[0] + "월" + day_arr[0] + "일";
        }else if(day_arr[0].equals("03")) {
            day_arr[0]="3";
            _startDate = year_arr[0] + "년" + month_arr[0] + "월" + day_arr[0] + "일";
        }else if(day_arr[0].equals("04")) {
            day_arr[0]="4";
            _startDate = year_arr[0] + "년" + month_arr[0] + "월" + day_arr[0] + "일";
        }else if(day_arr[0].equals("05")) {
            day_arr[0]="5";
            _startDate = year_arr[0] + "년" + month_arr[0] + "월" + day_arr[0] + "일";
        }else if(day_arr[0].equals("06")) {
            day_arr[0]="6";
            _startDate = year_arr[0] + "년" + month_arr[0] + "월" + day_arr[0] + "일";
        }else if(day_arr[0].equals("07")) {
            day_arr[0]="7";
            _startDate = year_arr[0] + "년" + month_arr[0] + "월" + day_arr[0] + "일";
        }else if(day_arr[0].equals("08")) {
            day_arr[0]="8";
            _startDate = year_arr[0] + "년" + month_arr[0] + "월" + day_arr[0] + "일";
        }else if(day_arr[0].equals("09")) {
            day_arr[0]="9";
            _startDate = year_arr[0] + "년" + month_arr[0] + "월" + day_arr[0] + "일";
        }
        date_pick.setText(_startDate);

        starttime.setText(sAMPM + " " + hour + " : " + String.format("%02d", minute));
        _startTime = sAMPM + " " + hour + ":" + String.format("%02d", minute);
        endtime.setText(sAMPM + " " + hour + " : " + String.format("%02d", minute));
        _endTime = sAMPM + " " + hour + ":" + String.format("%02d", minute);
        //date_pick.setText();

        if (database != null) { //db여는거
            database.close();
            database = null;
        }
        database = new LocalDB(this);
        database = LocalDB.getInstance(this);

        //TextView tv1= (TextView)findViewById(R.id.datepick);
        date_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogFragment dFragment = new oneDatePickerFragment();
                dFragment.show(getFragmentManager(), "Date Picker");
                _startDate = datePickerFragment.text;
                Log.d("받아온 날짜", String.valueOf(datePickerFragment.year));

            }
        });

        // Log.d("날짜 년값",String.valueOf(datePickerFragment.year));
        //  tv1.setText(datePickerFragment.text);

        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogFragment newFragment = new Personal_TimePickerFragment();
                newFragment.show(getFragmentManager(), "timePicker");
                Log.d("시", String.valueOf(Personal_TimePickerFragment.hour_of_12_hour_format));
                Log.d("상태", String.valueOf(Personal_TimePickerFragment.minute));

            }
        });

        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogFragment newFragment = new Personal_TimePickerFragments();
                newFragment.show(getFragmentManager(), "timePicker");


            }
        });
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

        /*if(TimePickerFragments.status=="AM"&&TimePickerFragment.status=="PM"){
            Log.d("상태",TimePickerFragments.status);
            endtime.setText(TimePickerFragment.texts);
             Toast.makeText(this, "시작시간 보다 이른 시간입니다.", Toast.LENGTH_SHORT).show();
        }
        else if(TimePickerFragment.status=="AM" && TimePickerFragments.status=="PM"){

        }
        else if(TimePickerFragment.status=="PM"&&TimePickerFragments.status=="PM")
            if(TimePickerFragment.hour_of_12_hour_format>TimePickerFragments.hour_of_12_hour_format){
                endtime.setText(TimePickerFragment.texts);
                 Toast.makeText(this, "시작시간 보다 이른 시간입니다.", Toast.LENGTH_SHORT).show();
            }
*/

        //Date endtimes = sdf.parse(TimePickerFragments.texts);
        /// Date sttime=sdf.parse(TimePickerFragment.texts);
        // int str=Integer.parseInt(String.valueOf(endtimes))-sttime;
      /*  if (TimePickerFragment.texts != null) {
            int si=TimePickerFragment.hour_of_12_hour_format-hour;
            int bun=TimePickerFragment.minute-minute;
            if(si>0){
                endtime.setText(TimePickerFragment.texts);
            }
            if(si<=0 && bun>0){
                endtime.setText(TimePickerFragment.texts);
            }
            Toast.makeText(this, "바꾸기완료", Toast.LENGTH_SHORT).show();

        }
        if( TimePickerFragments.texts !=null){
            int si=hour-TimePickerFragments.hour_of_12_hour_format;
            int bun=minute-TimePickerFragments.minute;
            if(si>0){
                endtime.setText(TimePickerFragment.texts);
            }
            if(si<=0 &&bun>0){
                endtime.setText(TimePickerFragment.texts);
            }
        }*/
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
                    before =20000;
                } else if (adapterView.getSelectedItemPosition() == 2) {
                    before = -900000;
                }  else{
                    before = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {        //여기 변수를 this로 선언하랬다.   add data.java도 만들어야하나?
                int _userNumber = 2;        //사용자번호
                int _scheduleclassification = 2;    //할일추가
                int _alarm = 2;

                if (Personal_TimePickerFragment.texts != null) {
                    _startTime = Personal_TimePickerFragment.texts;
                    Personal_TimePickerFragment.texts = null;
                }
                if (Personal_TimePickerFragments.texts != null) {
                    _endTime = Personal_TimePickerFragments.texts;
                    Personal_TimePickerFragments.texts = null;
                }
                if (oneDatePickerFragment.text != null) {
                    _startDate = oneDatePickerFragment.text;
                    oneDatePickerFragment.text = null;
                }

                //시간 날짜 지정
//                Log.d("CheckMyApplicationTime", MyApplication.dateForAlarm + MyApplication.timeForAlarm);
                String str = MyApplication.dateForAlarm + " " + MyApplication.timeForAlarm;
                Log.d("핫", MyApplication.dateForAlarm + " "+MyApplication.timeForAlarm);
                Log.d("핫", str);
                SimpleDateFormat abc = new SimpleDateFormat("yyyy/MM/dd HH:mm");




                try {
                    Date date = abc.parse(str);
                    MyApplication.millis = date.getTime();
                    Log.d("컄", MyApplication.millis.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                setAlrarm(MyApplication.millis, before);
                Log.d("컄", MyApplication.millis.toString()+"    "+ before);

                String scheduleTitle = edit_title.getText().toString();
                String scheduleContent = edit_content.getText().toString();

                database.insertRecord(_userNumber, _scheduleclassification, _startTime, _endTime, _startDate, null, _alarm, repeat_num, scheduleTitle, scheduleContent, null,0);
                Toast.makeText(getApplicationContext(), "할 일이 추가되었습니다.", Toast.LENGTH_LONG).show();

                PersonalFragment.todo_list = PersonalFragment.dbs.personal_slidingtoday();

                for(int i=0;i<PersonalFragment.todo_list.size();i++){
                    String[] year_arr = PersonalFragment.todo_list.get(i).getSearchlist_year().split("년");
                    String[] month_arr = year_arr[1].split("월");
                    String[] day_arr = month_arr[1].split("일");

                    PersonalFragment.color_group = new int[1];
                    eventDecorator=new EventDecorator(PersonalFragment.color_group,Integer.parseInt(year_arr[0]),Integer.parseInt(month_arr[0]),Integer.parseInt(day_arr[0]),2);
                    PersonalFragment.materialCalendarView.addDecorator(eventDecorator);
                }
                finish();
            }
        });
       /* repeat_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                view.getResources().getColor(R.color.blue);
            }
        });*/

    }


    public void backWard(View view) {
        finish();
    }



    @Override
    public void onClick(View v) {
        finish();
    }

    //알람 시작 함수
    private void setAlrarm(long _millis, long _term) {
        if (before != 0) {
            Intent i = new Intent(getApplicationContext(), AlarmReceiver.class);
            PendingIntent sender = PendingIntent.getBroadcast(TodoAddActivity.this, 0, i, 0);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _millis + _term, 0, sender);
        }
    }

    //알람 헤제 함수
    public void resetAlrarm() {
        Intent i = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(TodoAddActivity.this, 0, i, 0);
        alarmManager.cancel(sender);
    }

}