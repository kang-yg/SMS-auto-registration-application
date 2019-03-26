package com.example.yg.sms_auto_registration;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public class ChangelistActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {//그룹캘린더 변경사항리스트 액티비티
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changelist);
    }

    public void backWard(View view) {
        finish();
    }
}
