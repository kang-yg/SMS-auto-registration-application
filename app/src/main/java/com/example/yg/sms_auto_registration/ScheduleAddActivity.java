package com.example.yg.sms_auto_registration;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class ScheduleAddActivity extends Activity implements View.OnClickListener { //일정추가 액티비티
    private Button cancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scheadd);

        cancel = (Button)findViewById(R.id.cancel);
        final RelativeLayout rl=(RelativeLayout) findViewById(R.id.test);

        cancel.setOnClickListener(this);

        Button date_pick=(Button)findViewById(R.id.datepick);
        Button end_date_pick=(Button)findViewById(R.id.end_datepick);

        date_pick.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DialogFragment dFragment= new DatePickerFragment();
                dFragment.show(getFragmentManager(),"Date Picker");

            }
        });

        end_date_pick.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DialogFragment dFragment= new DatePickerFragments();
                dFragment.show(getFragmentManager(),"Date Picker");

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
}
