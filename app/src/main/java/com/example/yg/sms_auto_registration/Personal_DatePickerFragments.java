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

public class Personal_DatePickerFragments extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    public static int year,month,day;
    public static String text;
    Personal_DatePickerFragment datePickerFragment;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog dpd = new DatePickerDialog(getActivity(),AlertDialog.THEME_HOLO_LIGHT,this,year, month, day);

        return dpd;
        // return DatePickerDialog dpd = new DatePickerDialog(getActivity(),AlertDialog.THEME_HOLO_LIGHT,this,year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        datePickerFragment=new Personal_DatePickerFragment();

        TextView tv1= (TextView) getActivity().findViewById(R.id.end_datepick);

        this.year=view.getYear();
        this.month=view.getMonth()+1;
        this.day=view.getDayOfMonth();
        tv1.setText(view.getYear()+"년"+String.format("%02d",this.month)+"월"+view.getDayOfMonth()+"일");
        text=String.valueOf(this.year)+"년"+String.format("%02d",this.month)+"월"+String.valueOf(this.day)+"일";
        //Log.d("내가 선택한 월 값 00형식으로",String.format("%02d",this.month));
        Log.d("text값",text);


        String s1=String.valueOf(datePickerFragment.year)+String.valueOf(datePickerFragment.month)+String.valueOf(datePickerFragment.day);
        String s2=String.valueOf(this.year)+String.valueOf(this.month)+String.valueOf(this.day);
        if(datePickerFragment.text!=null){
            if(Integer.parseInt(s1) >= Integer.parseInt(s2)){
                tv1.setText(datePickerFragment.text);
                text=datePickerFragment.text;
            }
        }
        Calendar cal = Calendar.getInstance();

        int years = cal.get(Calendar.YEAR); //현재년도
        int months = cal.get(Calendar.MONTH)+1;   //현재 달
        int days = cal.get(Calendar.DAY_OF_MONTH);  //현재 일
        String s3=String.valueOf(years)+String.valueOf(months)+String.valueOf(days);
        Log.d("현재",s3);
        Log.d("선택",s2);
        if(Integer.parseInt(s3)>=Integer.parseInt(s2)){
            tv1.setText( text=String.valueOf(years)+"년"+String.format("%02d",months)+"월"+String.valueOf(days)+"일");
            text=String.valueOf(years)+"년"+String.format("%02d",months)+"월"+String.valueOf(days)+"일";

        }
    }
}
