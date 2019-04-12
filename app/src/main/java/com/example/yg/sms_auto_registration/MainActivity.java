
package com.example.yg.sms_auto_registration;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;


public class MainActivity extends AppCompatActivity {

    private ViewPager vp; //프래그먼트 표시 뷰 페이저
    private Button viewtype_btn, personal_btn, group_btn, today_btn;//보기방식, 개인캘린더, 그룹캘린더 버튼
    private Spinner spinner;//그룹캘린더 스피너
    private TextView topbar, viewtype_txt, today_txt;//캘린더 년월 표시 텍스트뷰
    PersonalFragment personalFragment = null;

    String providerId;
    String uid;
    String name;
    String email;
    Uri photoUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.calendar);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS) || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CALENDAR)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_CALENDAR}, 1);
            }
        }

        //Get device calendar event
        AddDeviceEvent addDeviceEvent = new AddDeviceEvent();
        addDeviceEvent.setCalendar(getApplicationContext());

        //Set local user info
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                providerId = profile.getProviderId();

                // UID specific to the provider
                uid = profile.getUid();

                // Name, email address, and profile photo Url
                name = profile.getDisplayName();
                email = profile.getEmail();
                photoUrl = profile.getPhotoUrl();
            }
        }

        Log.d("myInfoFirebase", "providerId : " + providerId);
        Log.d("myInfoFirebase", "uid : " + uid);
        Log.d("myInfoFirebase", "name : " + name);
        Log.d("myInfoFirebase", "email : " + email);

        ConnectFireBaseDB.postUser(true, name, email, uid, providerId);

        MyApplication.localUser_name = name;
        MyApplication.localUser_email = email;
        MyApplication.localUser_uid = uid;
        MyApplication.localUser_providerId = providerId;

        Log.d("myInfoLocal", "providerId : " + MyApplication.localUser_providerId);
        Log.d("myInfoLocal", "uid : " + MyApplication.localUser_uid);
        Log.d("myInfoLocal", "name : " + MyApplication.localUser_name);
        Log.d("myInfoLocal", "email : " + MyApplication.localUser_email);


        //년월바 객체
        topbar = (TextView) findViewById(R.id.txt_YearMonth);
        //주단위보기 텍스트 객체
        viewtype_txt = (TextView) findViewById(R.id.txt_ViewType);
        //오늘보기 텍스트 객체
        today_txt = (TextView) findViewById(R.id.txt_Today);

        // 전체화면인 DrawerLayout 객체 참조
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Drawer 화면(뷰) 객체 참조
        final View drawerView = (View) findViewById(R.id.drawer);


        // 드로어 화면을 열고 닫을 버튼 객체 참조
        Button OpenDrawerbtn = (Button) findViewById(R.id.OpenDrawer_btn);


        // 드로어 여는 버튼 리스너
        OpenDrawerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });

        MainSingleton mainSingleton = MainSingleton.getInstance();
        // 스피너 어뎁터 및 레이아웃 부착
        group_btn = (Button) findViewById(R.id.Group_btn);
        mainSingleton.group_btn = (Button) findViewById(R.id.Group_btn);
        mainSingleton.spinner = (Spinner) findViewById(R.id.spinner);
        mainSingleton.activity = this;

        ConnectFireBaseDB.GroupRead();

        //뷰페이저 설정
        vp = (ViewPager) findViewById(R.id.viewpager);
        personal_btn = (Button) findViewById(R.id.Personal_btn);
        viewtype_btn = (Button) findViewById(R.id.ViewType_btn);
        vp.setAdapter(new pagerAdapter(getSupportFragmentManager()));
        vp.setCurrentItem(0);


        today_btn = (Button) findViewById(R.id.ShowToday_btn);
        today_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personalFragment.SetToday();
            }
        });
        today_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personalFragment.SetToday();
            }
        });

        //개인, 그룹캘린더 버튼 클릭리스너

        personal_btn.setOnClickListener(movePageListener);
        personal_btn.setTag(0);
        group_btn.setOnClickListener(movePageListener);
        group_btn.setTag(1);
        viewtype_btn.setOnClickListener(movePageListener);
        viewtype_btn.setTag(2);
        viewtype_txt.setOnClickListener(movePageListener);
        viewtype_txt.setTag(2);


    }


    //버튼 클릭 리스너
    View.OnClickListener movePageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int tag = (int) v.getTag();
            vp.setCurrentItem(tag);
            if (tag == 0) {//개인 캘린더 버튼 클릭 시 버튼색상 변경
                personal_btn.setBackgroundResource(R.color.skyblue);
                group_btn.setBackgroundResource(R.color.lightgray);
                spinner.setBackgroundResource(R.color.lightgray);
            } else if (tag == 1) {//그룹 캘린더 버튼 클릭 시 버튼색상 변경
                personal_btn.setBackgroundResource(R.color.lightgray);
                group_btn.setBackgroundResource(R.color.skyblue);
                //spinner.setBackgroundResource(R.color.skyblue);
            }
        }
    };


    public void openSetting(View view) {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    public void openSearch(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void openEnrolledlist(View view) {
        Intent intent = new Intent(this, SmsActivity.class);
        startActivity(intent);
    }

    public void openTodolist(View view) {
        Intent intent = new Intent(this, TodoActivity.class);
        startActivity(intent);
    }

    public void openSchedulelist(View view) {
        Intent intent = new Intent(this, SchedulelistActivitiy.class);
        startActivity(intent);
    }

    public void openAnniversarylist(View view) {
        Intent intent = new Intent(this, AnniversarylistActivity.class);
        startActivity(intent);
    }


    //페이저 어댑터
    private static class pagerAdapter extends FragmentStatePagerAdapter {
        public pagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position)//프래그먼트 position 값에 따라 프래그먼트 생성
        {
            switch (position) {
                case 0:
                    return new PersonalFragment();
                case 1:
                    return new GroupFragment();
                case 2:
                    return new ViewtypeFragment();

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    public void ChangeTopbar(String text) { //캘린더의 topbar 변경 메소드
        topbar.setText(text);
    }
}
