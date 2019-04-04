package com.example.yg.sms_auto_registration;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(),AlertDialog.THEME_HOLO_LIGHT, this, year, month,day);
        // return DatePickerDialog dpd = new DatePickerDialog(getActivity(),AlertDialog.THEME_HOLO_LIGHT,this,year, month, day);
    }

    public void onDateSet(DatePicker _view, int _year, int _month, int _day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(_year, _month, _day);

        calendar.add(Calendar.DATE,0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = simpleDateFormat.format(calendar.getTime());

        //Group schedule start day
        Button stBut = (Button)getActivity().findViewById(R.id.sta_datepick);
        stBut.setText(formatted);

/*        //Group do list day
        Button doDay = (Button)getActivity().findViewById(R.id.datepick);
        doDay.setText(formatted);*/
    }
}
