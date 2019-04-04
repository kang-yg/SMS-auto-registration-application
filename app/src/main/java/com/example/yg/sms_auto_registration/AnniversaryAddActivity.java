package com.example.yg.sms_auto_registration;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AnniversaryAddActivity extends Activity implements View.OnClickListener { //기념일 추가 액티비티
    private Button cancle, complete, layoutDate;
    private EditText layoutTitle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anniveradd);

        layoutTitle = (EditText)findViewById(R.id.anniver_title);

        layoutDate = (Button)findViewById(R.id.datepick);
        layoutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dFragment = new AnniversaryAddDatePickerFragment();
                dFragment.show(getFragmentManager(), "Date Picker");
            }
        });

        complete = (Button)findViewById(R.id.anniver_complete);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = layoutTitle.getText().toString();
                String date = (String) layoutDate.getText();
                ConnectFireBaseDB.postSchedule(true, 3, 3, 3, date, null, title, null, "영규", null, 4);
                Toast.makeText(getApplicationContext(),"기념일 추가 완료", Toast.LENGTH_LONG).show();
            }
        });

        cancle = (Button)findViewById(R.id.cancel);
        cancle.setOnClickListener(this);

    }

    public void backWard(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
