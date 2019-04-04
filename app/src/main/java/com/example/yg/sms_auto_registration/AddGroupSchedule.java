package com.example.yg.sms_auto_registration;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddGroupSchedule extends Activity implements View.OnClickListener { //일정추가 액티비티

    private EditText editTitel;
    private EditText editContent;
    private EditText editPlace;

    private Button start_date_pick;
    private Button end_date_pick;
    private Button complete;
    private Button cancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scheadd);
        Log.d("mina", "aaaa");
        editTitel = (EditText) findViewById(R.id.scheadd_title);
        editContent = (EditText) findViewById(R.id.edit_content);
        editPlace = (EditText) findViewById(R.id.scheadd_place);

        start_date_pick = (Button) findViewById(R.id.sta_datepick);
        start_date_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dFragment = new DatePickerFragment();
                dFragment.show(getFragmentManager(), "Date Picker");
            }
        });


        end_date_pick = (Button) findViewById(R.id.end_datepick);
        end_date_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dFragment = new DatePickerFragments();
                dFragment.show(getFragmentManager(), "Date Picker");
            }
        });


        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(this);

        complete = (Button) findViewById(R.id.scheadd_complete);
        complete.setOnClickListener(new View.OnClickListener() {
            String title, content, place;

            @Override
            public void onClick(View v) {
                Log.d("mina", "aaaa");
                try {
                    title = editTitel.getText().toString();
                    content= editContent.getText().toString();
                    place = editPlace.getText().toString();
                    String startStr = (String) start_date_pick.getText();
                    String endStar = (String) end_date_pick.getText();
                    ConnectFireBaseDB.postSchedule(true, 1, 2, 3, startStr, endStar, title, content, "영규", place, 4);
                    Toast.makeText(getApplicationContext(),"일정추가 완료", Toast.LENGTH_LONG).show();

                } catch (Exception E) {
                    E.printStackTrace();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        finish();
    }

}
