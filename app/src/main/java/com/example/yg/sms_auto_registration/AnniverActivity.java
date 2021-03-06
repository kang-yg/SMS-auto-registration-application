
package com.example.yg.sms_auto_registration;


import android.app.DialogFragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;

public class AnniverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anniveradd);

        final RelativeLayout rl=(RelativeLayout) findViewById(R.id.test);

        Button date_pick=(Button)findViewById(R.id.datepick);


        date_pick.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DialogFragment dFragment= new DatePickerFragment();
                dFragment.show(getFragmentManager(),"Date Picker");

            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.alram_spinner);
        String[] strs = getResources().getStringArray(R.array.alramspinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.alramspinner_item,strs);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



    }

}