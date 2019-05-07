package com.example.yg.sms_auto_registration;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.util.Log;
import android.widget.TimePicker;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Button;

import java.util.Calendar;

public class Personal_TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private TimeData time;
    static int hour_of_12_hour_format;//시
    static int minute;       //분
    static String status;
    public static String texts;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        // Get a Calendar instance
        final Calendar calendar = Calendar.getInstance();
        // Get the current hour and minute
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // TimePickerDialog Theme : THEME_HOLO_LIGHT
        TimePickerDialog tpd = new TimePickerDialog(getActivity(),
                AlertDialog.THEME_HOLO_LIGHT,this,hour,minute,false);

        // Return the TimePickerDialog
        return tpd;
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        // Set a variable to hold the current time AM PM Status
        // Initially we set the variable value to AM
        status = "AM";

        if(hourOfDay > 11)
        {
            // If the hour is greater than or equal to 12
            // Then the current AM PM status is PM
            status = "PM";
        }

        // Initialize a new variable to hold 12 hour format hour value


        if(hourOfDay > 11){

            // If the hour is greater than or equal to 12
            // Then we subtract 12 from the hour to make it 12 hour format time
            hour_of_12_hour_format = hourOfDay - 12;
        }
        else {
            hour_of_12_hour_format = hourOfDay;
        }



        // Get the calling activity TextView reference
        Button tv = (Button) getActivity().findViewById(R.id.start_time);
        // Display the 12 hour format time in app interface
        tv.setText(status+" "+hour_of_12_hour_format + " : " + String.format("%02d",minute));

        time=new TimeData();
        time.setHourOfDay(hour_of_12_hour_format);
        time.setMinute(minute);
        time.setState(status);
        Log.d("시 값", String.valueOf(time.hour));
        Log.d("분 값", String.valueOf(time.minute));
        Log.d("시 값", String.valueOf(hour_of_12_hour_format));
        Log.d("분 값", String.valueOf(minute));
        texts=time.state+" "+String.valueOf(time.hour)+":"+String.valueOf(time.minute);
        MyApplication.timeForAlarm = Integer.toString(hourOfDay) + ":" + String.format("%02d",minute);
        Log.d("분 값", texts);
    }

}

