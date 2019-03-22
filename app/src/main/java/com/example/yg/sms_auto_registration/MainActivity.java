
package com.example.yg.sms_auto_registration;

import android.Manifest;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button userSendButton;
    Button userReceiveButton;
    Button groupSendButton;
    Button groupReceiveButton;
    Button sheduleSendButton;
    Button sheduleReceiveButton;
    Button revisionSendButton;
    Button revisionReceiveButton;

    String providerId;
    String uid;
    String name;
    String email;
    Uri photoUrl;

    ArrayList<String> testGroupUID = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.RECEIVE_SMS)) {
            Toast.makeText(this, "SMS 권한 설정이 필요함", Toast.LENGTH_SHORT).show();
        } else {
            // 권한이 할당되지 않았으면 해당 권한을 요청
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, 1);
        }

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

        userSendButton = (Button) findViewById(R.id.User_testSendButton);
        userSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectFireBaseDB.postUser(true, name, email, uid, providerId);
            }
        });

        userReceiveButton = (Button) findViewById(R.id.User_testReceiveButton);
        userReceiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("abc", "clicked");
                ConnectFireBaseDB.UserRead();
            }
        });


        testGroupUID.add("미나");
        testGroupUID.add("박보영");
        testGroupUID.add("한효주");

        groupSendButton = (Button) findViewById(R.id.Group_testSendButton);
        groupSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectFireBaseDB.postGroup(true, 1, testGroupUID, "이쁜이들");
            }
        });

        groupReceiveButton = (Button) findViewById(R.id.Group_testReceiveButton);
        groupReceiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectFireBaseDB.GroupRead();
            }
        });

        sheduleSendButton = (Button) findViewById(R.id.Schedule_testSendButton);
        sheduleSendButton.setOnClickListener(new View.OnClickListener() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String birth = "1997-03-24";
            String mbirth = "1995-09-18";
            Date minaBirth, myBirth;

            @Override
            public void onClick(View v) {
                try {
                    minaBirth = simpleDateFormat.parse(birth);
                    myBirth = simpleDateFormat.parse(mbirth);
                    ConnectFireBaseDB.postSchedule(true, 1, 2, 3, simpleDateFormat.format(minaBirth), simpleDateFormat.format(myBirth), "미나 생일", "이쁜이 미나", "영규", "방구석", 4);
                } catch (Exception E) {
                    Log.d("mina", "love");
                }
            }
        });

        sheduleReceiveButton = (Button) findViewById(R.id.Schedule_testReceiveButton);
        sheduleReceiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectFireBaseDB.ScheduleRead();
            }
        });

        revisionSendButton = (Button) findViewById((R.id.Revision_testSendButton));
        revisionSendButton.setOnClickListener(new View.OnClickListener() {
            String birth = "1997-03-24";
            Date minaBirth;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            @Override
            public void onClick(View v) {
                try {
                    minaBirth = simpleDateFormat.parse(birth);
                    ConnectFireBaseDB.postRevision(true, 1, 2, simpleDateFormat.format(minaBirth), "미나좋아", "강영규");
                } catch (Exception e) {
                    Log.d("mina", "love");
                }
            }
        });

        revisionReceiveButton = (Button) findViewById(R.id.Revision_testReceiveButton);
        revisionReceiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("revisionReceiveButton", "clicked");
                ConnectFireBaseDB.RevisionRead();
            }
        });

    }
}
