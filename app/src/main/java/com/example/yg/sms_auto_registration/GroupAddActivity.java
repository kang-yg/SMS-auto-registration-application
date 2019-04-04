package com.example.yg.sms_auto_registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class GroupAddActivity extends Activity implements View.OnClickListener { //그룹캘린더 추가 액티비티
    private Button cancel, groupadd_complete;
    private EditText inviteEdit, title;
    String groupaddTitle;
    String[] group_getEdit;
    ArrayList<String> strings = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groupadd);

        inviteEdit = (EditText) findViewById(R.id.invite);

        title = (EditText) findViewById(R.id.groupadd_title);

        groupadd_complete = (Button) findViewById(R.id.groupadd_complete);
        groupadd_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupaddTitle = title.getText().toString();
                groupaddTitle.trim();
                group_getEdit = inviteEdit.getText().toString().split(",|/|\\s");
                for (int i = 0; i < group_getEdit.length; i++) {
                    group_getEdit[i].trim();
                }
                boolean check = checkUID(group_getEdit);
                if (check) {
                    ConnectFireBaseDB.postGroup(true, 10, strings, groupaddTitle);
                    Toast.makeText(getApplicationContext(),"그룹캘린더 생성 완료",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "UID를 확인해주세요", Toast.LENGTH_LONG).show();
                }

                //Toast.makeText(getApplicationContext(),group_getEdit[0],Toast.LENGTH_SHORT).show();
            }
        });

        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    private boolean checkUID(String[] _getEdit) {
        boolean flag = false;
        strings.clear();
        ConnectFireBaseDB.UserRead();
        ArrayList<String> tempUID = new ArrayList<>();

        for (int i = 0; i < MyApplication.firebaseDB_user.size(); i++) {
            try {
                tempUID.add(MyApplication.firebaseDB_user.get(i).getUserUID());
                Log.d("tempUID", tempUID.get(i));
            } catch (Exception e) {
                Log.d("tempUID", "다시!");
            }
        }
        for (int i = 0; i < tempUID.size(); i++) {
            if (tempUID.contains(_getEdit[i])) {
                strings.add(_getEdit[i]);
                flag = true;
            } else {
                strings.clear();
                Toast.makeText(getApplicationContext(), "UID를 확인해주세요", Toast.LENGTH_LONG).show();
            }
        }
        return flag;
    }
}
