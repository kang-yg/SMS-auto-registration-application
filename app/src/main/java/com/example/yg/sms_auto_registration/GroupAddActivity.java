package com.example.yg.sms_auto_registration;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;

public class GroupAddActivity extends Activity implements View.OnClickListener { //그룹캘린더 추가 액티비티
    private Button cancel;
    private EditText inviteEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groupadd);

        inviteEdit = (EditText)findViewById(R.id.invite);
        String[] getEdit = inviteEdit.getText().toString().split(",|/|\\s");

        ConnectFireBaseDB.UserRead();

        cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    private void checkUID(String[] _getEdit){
        ConnectFireBaseDB.UserRead();
    }


}
