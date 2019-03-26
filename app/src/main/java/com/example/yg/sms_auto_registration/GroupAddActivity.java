package com.example.yg.sms_auto_registration;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

public class GroupAddActivity extends Activity implements View.OnClickListener { //그룹캘린더 추가 액티비티
    private Button cancel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groupadd);

        cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
    }

    public void backWard(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
