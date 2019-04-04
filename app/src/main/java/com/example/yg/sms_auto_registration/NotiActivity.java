package com.example.yg.sms_auto_registration;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class NotiActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notibtn);

        final NotificationHelper notificationHelper = new NotificationHelper(this);

        Button notify_btn = findViewById(R.id.noti_btn);

        notify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationHelper.notify("메시지", "제목이얌");

            }
        });
    }


}
