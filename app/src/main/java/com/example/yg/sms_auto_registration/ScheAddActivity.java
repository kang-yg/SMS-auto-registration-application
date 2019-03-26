
package com.example.yg.sms_auto_registration;

import android.app.DialogFragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class ScheAddActivity extends AppCompatActivity implements View.OnClickListener {
    private Button cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    @Override
    public void onClick(View v) {
        finish();
    }
}