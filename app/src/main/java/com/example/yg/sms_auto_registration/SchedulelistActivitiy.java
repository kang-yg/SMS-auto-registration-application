package com.example.yg.sms_auto_registration;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public class SchedulelistActivitiy extends Activity { //일정목록 액티비티
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schelist);
    }

    public void backWard(View view) {
        finish();
    }
}
