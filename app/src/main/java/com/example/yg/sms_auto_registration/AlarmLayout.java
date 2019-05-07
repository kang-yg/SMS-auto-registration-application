package com.example.yg.sms_auto_registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class AlarmLayout extends Activity {
    AlarmVibrate alarmVibrate;
    TodoAddActivity todoAddActivity;
    Button cancel;
    TextView time;
    TextView year;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alrams);

        year = (TextView) findViewById(R.id.year);
        time = (TextView) findViewById(R.id.time);
        cancel = (Button) findViewById(R.id.cancel);

        //화면 띄우기
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON //화면을 켜진 상태로 유지
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD //순정 잠금 화면을 해제 할 때 사용
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON // 화면을 켠다
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED); //잠긴 화면 위에 표시할때 사용

        year.setText(MyApplication.dateForAlarm);
        time.setText(MyApplication.timeForAlarm);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent stopAlarm = new Intent(getApplicationContext(),AlarmVibrate.class);
                stopService(stopAlarm);
                cancel.setOnClickListener(this);
                finish();
            }
        });

    }
}
