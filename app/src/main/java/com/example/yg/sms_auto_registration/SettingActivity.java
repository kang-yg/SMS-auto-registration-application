package com.example.yg.sms_auto_registration;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class SettingActivity extends Activity { //설정창 액티비티
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        Fragment fragment = new SettingsScreen();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        if (savedInstanceState == null) {
            fragmentTransaction.add(R.id.relative_layout, fragment, "setting_fragment");
            fragmentTransaction.commit();
        } else {
            fragment = getFragmentManager().findFragmentByTag("settings_fragment");
        }
    }

    public static class SettingsScreen extends PreferenceFragment {
        Preference copyUID;
        Preference makeInquiry;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference);
            copyUID = (Preference) findPreference("copyUID");
            copyUID.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(getContext().CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("MyUID", MyApplication.localUser_uid);
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(getContext(), "UID가 클립보드에 복사되었습니다." + "\n나의 UID : " + MyApplication.localUser_uid, Toast.LENGTH_LONG).show();
                    return false;
                }
            });

            makeInquiry = (Preference) findPreference("makeInquiry");
            makeInquiry.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(getContext().CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText("Administrator e-mail", "aaa@naver.com");
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(getContext(), "문의하실 메일주소가 클립보드에 복사되었습니다." + "\n보내실 주소 : " + "aaa@naver.com", Toast.LENGTH_LONG).show();
                    return false;
                }
            });

        }
    }

//    public void backWard(View view) {//뒤로가기 버튼시 액티비티 종료
//        finish();
//    }
//
//    public void openSmsPattern(View view) {//SMS 등록 패턴 열기
//        Intent intent = new Intent(this,SettingPatternActivity.class);
//        startActivity(intent);
//    }
}
