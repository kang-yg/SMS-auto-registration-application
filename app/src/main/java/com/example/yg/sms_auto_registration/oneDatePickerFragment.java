package com.example.yg.sms_auto_registration;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class oneDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    public static int year, month, day;
    public static String text;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog dpd = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);

        return dpd;
        // return DatePickerDialog dpd = new DatePickerDialog(getActivity(),AlertDialog.THEME_HOLO_LIGHT,this,year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        TextView tv1 = (TextView) getActivity().findViewById(R.id.datepick);

        this.year = view.getYear();
        this.month = view.getMonth() + 1;
        this.day = view.getDayOfMonth();
        //Log.d("월 값", String.valueOf(this.month));
        tv1.setText(view.getYear() + "년" + String.format("%02d", this.month) + "월" + view.getDayOfMonth() + "일");
        text = String.valueOf(this.year) + "년" + String.format("%02d", this.month) + "월" + String.valueOf(this.day) + "일";
        Log.d("월 포멧", String.valueOf(this.month));
        Log.d("text", text);
        MyApplication.dateForAlarm = view.getYear() + "/" + String.format("%02d", this.month) + "/" + view.getDayOfMonth();



    }


}
