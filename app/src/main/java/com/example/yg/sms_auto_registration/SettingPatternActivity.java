package com.example.yg.sms_auto_registration;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public class SettingPatternActivity extends Activity { //sms 등록패턴 액티비티
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setpattern);
    }

    public void backWard(View view) {
        finish();
    }
}
