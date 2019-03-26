package com.example.yg.sms_auto_registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

public class SettingActivity extends Activity { //설정창 액티비티
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
    }

    public void backWard(View view) {//뒤로가기 버튼시 액티비티 종료
        finish();
    }

    public void openSmsPattern(View view) {//SMS 등록 패턴 열기
        Intent intent = new Intent(this,SettingPatternActivity.class);
        startActivity(intent);
    }
}
