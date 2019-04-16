package com.example.yg.sms_auto_registration;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
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

        //SMS
        //Intent intent = getIntent();
        //String autoTitle = intent.getExtras().getString("Title");

        Intent intent = getIntent();
        final int groupNum = intent.getExtras().getInt("GroupNum");
        final int scheduleNum = intent.getExtras().getInt("ScheduleNumber");

        editTitel = (EditText) findViewById(R.id.scheadd_title);
        editContent = (EditText) findViewById(R.id.edit_content);
        editPlace = (EditText) findViewById(R.id.scheadd_place);

        //editTitel.setText(autoTitle);

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
            StringBuffer buffer01 = null;
            StringBuffer buffer02 = null;
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

                    String arrText1[] = startStr.split("-");
                    startStr = "";
                    String arrText2[] = endStar.split("-");
                    endStar = "";

                    for(int i = 0 ; i < arrText1.length ; i++) {
                        startStr += arrText1[i];
                        endStar += arrText2[i];
                    }

                    int tempText1 = Integer.parseInt(startStr);
                    int tempText2 = Integer.parseInt(endStar);

                    if(tempText1 < tempText2 || tempText1 == tempText2) {
                        System.out.println("tempText1 > tempText2 : " + tempText1 + " > " + tempText2 );
                        buffer01 = new StringBuffer(Integer.toString(tempText1));
                        buffer01.insert(4, "-");
                        buffer01.insert(7, "-");
                        buffer02 = new StringBuffer(Integer.toString(tempText2));
                        buffer02.insert(4, "-");
                        buffer02.insert(7, "-");
                        ConnectFireBaseDB.postSchedule(true, scheduleNum, groupNum, 3, buffer01.toString(), buffer02.toString(), title, content, "영규", place, 4);
                        ConnectFireBaseDB.postScheduleNumber(true,scheduleNum+1);
                        Toast.makeText(getApplicationContext(),"일정추가 완료", Toast.LENGTH_LONG).show();
                    }
                    else{
                        System.out.println("tempText1 < tempText2 : " + tempText1 + " < " + tempText2 );
                        buffer01 = new StringBuffer(Integer.toString(tempText1));
                        buffer01.insert(4, "-");
                        buffer01.insert(7, "-");

                        end_date_pick.setText(buffer01.toString());
                        Toast.makeText(getApplicationContext(),"시작일 종료일 확인", Toast.LENGTH_LONG).show();
                    }

//                    ConnectFireBaseDB.postSchedule(true, scheduleNum, groupNum, 3, startStr, endStar, title, content, "영규", place, 4);
//                    ConnectFireBaseDB.postScheduleNumber(true,scheduleNum+1);
//                    Toast.makeText(getApplicationContext(),"일정추가 완료", Toast.LENGTH_LONG).show();

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
