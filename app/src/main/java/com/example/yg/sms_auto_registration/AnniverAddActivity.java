
package com.example.yg.sms_auto_registration;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.DayViewDecorator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.*;

public class AnniverAddActivity extends Activity implements View.OnClickListener {
    private static LocalDB database;
    private Button confirm;
    private Button cancel;
    private EditText edit_title;
    private Button date_pick;
    String _startDate;
    oneDatePickerFragment datePickerFragment;
    private Button repeat_one, repeat_two;
    private int repeat_num = 0;
    DayViewDecorator eventDecorator;

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


        repeat_one = (Button) findViewById(R.id.repeat_one);
        repeat_two = (Button) findViewById(R.id.repeat_one);
        edit_title = (EditText) findViewById(R.id.anniver_title);
        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        confirm = (Button) findViewById(R.id.anniver_complete);
        date_pick = findViewById(R.id.datepick);

        if (database != null) { //db여는거
            database.close();
            database = null;
        }
        database = new LocalDB(this);
        database = LocalDB.getInstance(this);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년MM월dd일");
        String now = sdf.format(new Date());
        _startDate = now;

        String[] year_arr = now.split("년");
        String[] month_arr = year_arr[1].split("월");
        String[] day_arr = month_arr[1].split("일");
        if (day_arr[0].equals("01")) {
            day_arr[0] = "1";
            _startDate = year_arr[0] + "년" + month_arr[0] + "월" + day_arr[0] + "일";
        } else if (day_arr[0].equals("02")) {
            day_arr[0] = "2";
            _startDate = year_arr[0] + "년" + month_arr[0] + "월" + day_arr[0] + "일";
        } else if (day_arr[0].equals("03")) {
            day_arr[0] = "3";
            _startDate = year_arr[0] + "년" + month_arr[0] + "월" + day_arr[0] + "일";
        } else if (day_arr[0].equals("04")) {
            day_arr[0] = "4";
            _startDate = year_arr[0] + "년" + month_arr[0] + "월" + day_arr[0] + "일";
        } else if (day_arr[0].equals("05")) {
            day_arr[0] = "5";
            _startDate = year_arr[0] + "년" + month_arr[0] + "월" + day_arr[0] + "일";
        } else if (day_arr[0].equals("06")) {
            day_arr[0] = "6";
            _startDate = year_arr[0] + "년" + month_arr[0] + "월" + day_arr[0] + "일";
        } else if (day_arr[0].equals("07")) {
            day_arr[0] = "7";
            _startDate = year_arr[0] + "년" + month_arr[0] + "월" + day_arr[0] + "일";
        } else if (day_arr[0].equals("08")) {
            day_arr[0] = "8";
            _startDate = year_arr[0] + "년" + month_arr[0] + "월" + day_arr[0] + "일";
        } else if (day_arr[0].equals("09")) {
            day_arr[0] = "9";
            _startDate = year_arr[0] + "년" + month_arr[0] + "월" + day_arr[0] + "일";
        }
        date_pick.setText(_startDate);


        final RelativeLayout rl = (RelativeLayout) findViewById(R.id.test);
        // Button start_btn=(Button)findViewById(R.id.start_time);
        // Button end_btn=(Button)findViewById(R.id.end_time);
        Button date_pick = (Button) findViewById(R.id.datepick);

        date_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogFragment dFragment = new oneDatePickerFragment();
                dFragment.show(getFragmentManager(), "Date Picker");
                _startDate = datePickerFragment.text;

            }
        });


        Spinner spinner = (Spinner) findViewById(R.id.alram_spinner);
        String[] strs = getResources().getStringArray(R.array.alramspinner_sche);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.alramspinner_item, strs);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



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

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {        //여기 변수를 this로 선언하랬다.   add data.java도 만들어야하나?
                int _userNumber = 2;        //사용자번호
                int _scheduleclassification = 3;    //기념일추가
                int _alarm = 2;

                if (datePickerFragment.text != null) {
                    _startDate = datePickerFragment.text;
                    datePickerFragment.text = null;
                }


                //시간 날짜 지정
//                Log.d("CheckMyApplicationTime", MyApplication.dateForAlarm + MyApplication.timeForAlarm);
                String str = MyApplication.dateForAlarm;
                Log.d("핫", MyApplication.dateForAlarm);
                Log.d("핫", str);
                SimpleDateFormat abc = new SimpleDateFormat("yyyy/MM/dd");

                try {
                    Date date = abc.parse(str);
                    MyApplication.millis = date.getTime();

                    Log.d("컄", MyApplication.millis.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                setAlrarm(MyApplication.millis, MyApplication.myBefore);
                Log.d("컄", MyApplication.millis.toString() + "    " + MyApplication.myBefore);
                String scheduleTitle = edit_title.getText().toString();

                database.insertRecord(_userNumber, _scheduleclassification, null, null, _startDate, null, _alarm, repeat_num, scheduleTitle, null, null,0);
                Toast.makeText(getApplicationContext(), "기념일이 추가되었습니다.", Toast.LENGTH_LONG).show();

                PersonalFragment.todo_list = PersonalFragment.dbs.personal_slidingtoday();

                for (int i = 0; i < PersonalFragment.todo_list.size(); i++) {
                    String[] year_arr = PersonalFragment.todo_list.get(i).getSearchlist_year().split("년");
                    String[] month_arr = year_arr[1].split("월");
                    String[] day_arr = month_arr[1].split("일");

                    PersonalFragment.color_group = new int[1];
                    eventDecorator = new EventDecorator(PersonalFragment.color_group, Integer.parseInt(year_arr[0]), Integer.parseInt(month_arr[0]), Integer.parseInt(day_arr[0]), 2);
                    PersonalFragment.materialCalendarView.addDecorator(eventDecorator);
                }
                finish();
            }
        });
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
        if (MyApplication.myBefore != 0) {
            Intent i = new Intent(getApplicationContext(), AlarmReceiver.class);
            PendingIntent sender = PendingIntent.getBroadcast(AnniverAddActivity.this, 0, i, 0);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, _millis + _term, 0, sender);
        }
    }
}