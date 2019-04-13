package com.example.yg.sms_auto_registration;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TodoAddActivity extends Activity implements View.OnClickListener { //할일 추가 액티비티
    private Button cancel, complete;
    private Button layoutDate;
    private EditText layoutTitle, layoutContent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doadd);

        Intent intent = getIntent();
        final int groupNum = intent.getExtras().getInt("GroupNum");
        final int scheduleNum = intent.getExtras().getInt("ScheduleNumber");

        layoutDate = (Button)findViewById(R.id.datepick);
        layoutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dFragment = new TodoAddDatePickerFragment();
                dFragment.show(getFragmentManager(), "Date Picker");
            }
        });

        layoutTitle = (EditText)findViewById(R.id.doAdd_title);
        layoutContent = (EditText)findViewById(R.id.doAdd_content);

        cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(this);

        complete = (Button)findViewById(R.id.doAdd_complete);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = layoutTitle.getText().toString();
                String content = layoutContent.getText().toString();
                String date = (String) layoutDate.getText();
                ConnectFireBaseDB.postSchedule(true, scheduleNum, groupNum, 3, date, null, title, content, "영규", null, 4);
                ConnectFireBaseDB.postScheduleNumber(true,scheduleNum+1);
                Toast.makeText(getApplicationContext(),"할일추가 완료", Toast.LENGTH_LONG).show();
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
