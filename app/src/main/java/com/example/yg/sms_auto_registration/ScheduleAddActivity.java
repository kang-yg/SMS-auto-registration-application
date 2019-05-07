package com.example.yg.sms_auto_registration;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.DayViewDecorator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ScheduleAddActivity extends Activity implements View.OnClickListener { //일정추가 액티비티
    private Button cancel;
    private Button confirm;
    private EditText edit_title;
    private EditText edit_content;
    private EditText edit_place;
    private Button date_pick;
    private Button end_date_pick;
    private Button repeat_one, repeat_two;
    private int repeat_num = 0;
    private static LocalDB database;
    private static final String TAG = "Schedule";
    String _endDate, _startDate;
    Personal_DatePickerFragment datePickerFragment;
    Personal_DatePickerFragments datePickerFragments;
    String s2, s1, s;
    DayViewDecorator eventDecorator ;
    private AlarmManager alarmManager;
    private Calendar cal;
    private NotificationManager mNotification;
    AlarmVibrate alarmVibrate;
    int before = 0;
    int hour, minute;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scheadd);


        //알람 매니저 획득
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //현재 시각 획득
        cal = Calendar.getInstance();

        edit_title = (EditText) findViewById(R.id.scheadd_title);
        edit_content = (EditText) findViewById(R.id.edit_content);
        edit_place = (EditText) findViewById(R.id.scheadd_place);
        date_pick = findViewById(R.id.sta_datepick);
        end_date_pick = findViewById(R.id.end_datepick);
        repeat_one = (Button) findViewById(R.id.repeat_one);
        repeat_two = (Button) findViewById(R.id.repeat_one);
        Spinner spinner = (Spinner) findViewById(R.id.alram_spinner);
        String[] strs = getResources().getStringArray(R.array.alramspinner_sche);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.alramspinner_item,strs);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        if (database != null) { //db여는거
            database.close();
            database = null;
        }
        database = new LocalDB(this);
        database = LocalDB.getInstance(this);
        //  boolean isOpen = database.open();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년MM월dd일");
        String now = sdf.format(new Date());
        _startDate = now;

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

        _endDate = now;


        String[] year_arr1 = now.split("년");
        String[] month_arr1 = year_arr[1].split("월");
        String[] day_arr1 = month_arr[1].split("일");
        if(day_arr1[0].equals("01")) {
            day_arr1[0]="1";
            _endDate = year_arr1[0] + "년" + month_arr1[0] + "월" + day_arr1[0] + "일";
        }else if(day_arr1[0].equals("02")) {
            day_arr1[0]="2";
            _endDate = year_arr1[0] + "년" + month_arr1[0] + "월" + day_arr1[0] + "일";
        }else if(day_arr1[0].equals("03")) {
            day_arr1[0]="3";
            _endDate = year_arr1[0] + "년" + month_arr1[0] + "월" + day_arr1[0] + "일";
        }else if(day_arr1[0].equals("04")) {
            day_arr1[0]="4";
            _endDate = year_arr1[0] + "년" + month_arr1[0] + "월" + day_arr1[0] + "일";
        }else if(day_arr1[0].equals("05")) {
            day_arr1[0]="5";
            _endDate = year_arr1[0] + "년" + month_arr1[0] + "월" + day_arr1[0] + "일";
        }else if(day_arr1[0].equals("06")) {
            day_arr1[0]="6";
            _endDate = year_arr1[0] + "년" + month_arr1[0] + "월" + day_arr1[0] + "일";
        }else if(day_arr1[0].equals("07")) {
            day_arr1[0]="7";
            _endDate = year_arr1[0] + "년" + month_arr1[0] + "월" + day_arr1[0] + "일";
        }else if(day_arr1[0].equals("08")) {
            day_arr1[0]="8";
            _endDate = year_arr1[0] + "년" + month_arr1[0] + "월" + day_arr1[0] + "일";
        }else if(day_arr1[0].equals("09")) {
            day_arr1[0]="9";
            _endDate = year_arr1[0] + "년" + month_arr1[0] + "월" + day_arr1[0] + "일";
        }
        end_date_pick.setText(_endDate);
        date_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogFragment dFragment = new Personal_DatePickerFragment();
                dFragment.show(getFragmentManager(), "Date Picker");
                _startDate = datePickerFragment.text;
                s = Personal_DatePickerFragment.text;
                s1 = String.valueOf(Personal_DatePickerFragment.year + Personal_DatePickerFragment.month + Personal_DatePickerFragment.day);

            }
        });

        end_date_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogFragment dFragment = new Personal_DatePickerFragments();
                dFragment.show(getFragmentManager(), "Date Picker");
                _endDate = datePickerFragments.text;

               /* int str = Integer.parseInt(s1) - Integer.parseInt(s2);
                if (str > 0) {
                    end_date_pick.setText(datePickerFragment.text);
                }*/

            }
        });

//SMS
        try {
            Intent intent = getIntent();
            String autoTitle = intent.getExtras().getString("Title");
            String autoDate = intent.getExtras().getString("Date");
            if (autoTitle != null) {
                edit_title.setText(autoTitle);
            }
            if (autoDate != null){
                Date date = new SimpleDateFormat("MM/dd").parse(autoDate);
                String newAutoDate = new SimpleDateFormat("MM월dd일").format(date);
                Date currentYearDate = new SimpleDateFormat("yyyy년MM월dd일").parse(now);
                String currentYear = new SimpleDateFormat("yyyy년").format(currentYearDate);

                String[] m = newAutoDate.split("월");
                String[] d = m[1].split("일");

                if(d[0].equals("01")) {
                    d[0]="1";
                    newAutoDate = m[0]+"월"+d[0]+"일";
                }else if(d[0].equals("02")) {
                    d[0]="2";
                    newAutoDate = m[0]+"월"+d[0]+"일";
                }else if(d[0].equals("03")) {
                    d[0]="3";
                    newAutoDate = m[0]+"월"+d[0]+"일";
                }else if(d[0].equals("04")) {
                    d[0]="4";
                    newAutoDate = m[0]+"월"+d[0]+"일";
                }else if(d[0].equals("05")) {
                    d[0]="5";
                    newAutoDate = m[0]+"월"+d[0]+"일";
                }else if(d[0].equals("06")) {
                    d[0]="6";
                    newAutoDate = m[0]+"월"+d[0]+"일";
                }else if(d[0].equals("07")) {
                    d[0]="7";
                    newAutoDate = m[0]+"월"+d[0]+"일";
                }else if(d[0].equals("08")) {
                    d[0]="8";
                    newAutoDate = m[0]+"월"+d[0]+"일";
                }else if(d[0].equals("09")) {
                    d[0]="9";
                    newAutoDate = m[0]+"월"+d[0]+"일";
                }

                date_pick.setText(currentYear + newAutoDate);
                end_date_pick.setText(currentYear + newAutoDate);
                MyApplication.dateForAlarm = currentYear + newAutoDate;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

//        //SMS
//        try {
//            Intent intent = getIntent();
//            String autoTitle = intent.getExtras().getString("Title");
//            String autoDate = intent.getExtras().getString("Date");
//            if (autoTitle != null) {
//                edit_title.setText(autoTitle);
//            }
//            if (autoDate != null){
//                Date date = new SimpleDateFormat("MM/dd").parse(autoDate);
//                String newAutoDate = new SimpleDateFormat("MM월dd일").format(date);
//                Date currentYearDate = new SimpleDateFormat("yyyy년MM월dd일").parse(now);
//                String currentYear = new SimpleDateFormat("yyyy년").format(currentYearDate);
//                date_pick.setText(currentYear + newAutoDate);
//                end_date_pick.setText(currentYear + newAutoDate);
//                MyApplication.dateForAlarm = currentYear + newAutoDate;
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        //for(int i=0;;){

        //  }
        /*if(datePickerFragment.text!=null ||datePickerFragments.text!=null) {
            int str = Integer.parseInt(s1) - Integer.parseInt(s2);
            if (str > 0) {
                end_date_pick.setText(datePickerFragment.text);
            }
        }*/

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

        cancel = (Button) findViewById(R.id.cancel);
        final RelativeLayout rl = (RelativeLayout) findViewById(R.id.test);
        cancel.setOnClickListener(this);

        confirm = (Button) findViewById(R.id.scheadd_complete);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {        //여기 변수를 this로 선언하랬다.
                int _userNumber = 2;        //사용자번호
                int _scheduleclassification = 1;    //일정추가번호
                int _alarm = 2;
                String scheduleTitle = edit_title.getText().toString();
                String scheduleContent = edit_content.getText().toString();
                String schedulePlace = edit_place.getText().toString();

                String str = MyApplication.dateForAlarm ;
                Log.d("핫", MyApplication.dateForAlarm );
                Log.d("핫", str);
                SimpleDateFormat abc = new SimpleDateFormat("yyyy/MM/dd");

                try {
                    Date date = abc.parse(str);
                    MyApplication.millis = date.getTime();
                    Log.d("컄", MyApplication.millis.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    setAlrarm(MyApplication.millis, before);
                    Log.d("컄", MyApplication.millis.toString()+"    "+ before);
                } catch (Exception e) {e.printStackTrace();}


                if (datePickerFragment.text != null) {
                    _startDate = datePickerFragment.text;
                    datePickerFragment.text = null;
                }
                if (datePickerFragments.text != null) {
                    _endDate = datePickerFragments.text;
                    datePickerFragments.text = null;
                }
                Log.d("startdate2", _startDate);
                database.insertRecord(_userNumber, _scheduleclassification, null, null, date_pick.getText().toString(), end_date_pick.getText().toString(), _alarm, repeat_num, scheduleTitle, scheduleContent, schedulePlace,0);
//                database.insertRecord(_userNumber, _scheduleclassification, null, null, _startDate, _endDate, _alarm, repeat_num, scheduleTitle, scheduleContent, schedulePlace);
                Toast.makeText(getApplicationContext(), "일정이 추가되었습니다.", Toast.LENGTH_LONG).show();

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
    }

    //알람 시작 함수
    private void setAlrarm(long _millis, long _term) {
        if (before != 0) {
            Intent i = new Intent(getApplicationContext(), AlarmReceiver.class);
            PendingIntent sender = PendingIntent.getBroadcast(ScheduleAddActivity.this, 0, i, 0);
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

    protected void onDestroy() {
        // close database
        if (database != null) {
            database.close();
            database = null;
        }

        super.onDestroy();
    }
}
