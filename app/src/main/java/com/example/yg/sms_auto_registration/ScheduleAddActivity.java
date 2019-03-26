package com.example.yg.sms_auto_registration;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class ScheduleAddActivity extends Activity implements View.OnClickListener { //일정추가 액티비티
    private Button cancel;
    private Button start_date_pick;
    private Button end_date_pick;
    private Button complete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scheadd);

        start_date_pick=(Button)findViewById(R.id.datepick);
        start_date_pick.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DialogFragment dFragment= new DatePickerFragment();
                dFragment.show(getFragmentManager(),"Date Picker");
            }
        });

        end_date_pick=(Button)findViewById(R.id.end_datepick);
        end_date_pick.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DialogFragment dFragment= new DatePickerFragments();
                dFragment.show(getFragmentManager(),"Date Picker");
                Log.d("dFragment", dFragment.getDialog().toString());
            }
        });

        cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(this);

        complete = (Button)findViewById(R.id.scheadd_complete);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
