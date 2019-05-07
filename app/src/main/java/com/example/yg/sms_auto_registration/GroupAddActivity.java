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
    private Button cancel,confirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groupadd);
        GroupAddSingleton groupAddSingleton = GroupAddSingleton.getInstance();
        groupAddSingleton.title = (EditText)findViewById(R.id.groupadd_title);
        groupAddSingleton.inviteEdit = (EditText)findViewById(R.id.invite);
        groupAddSingleton.addGroup = (Button)findViewById(R.id.groupadd_complete);
        groupAddSingleton.activity = this;

        ConnectFireBaseDB.UserRead();
                cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        finish();
    }

    public void backWard(View view) {
        finish();
    }
}
