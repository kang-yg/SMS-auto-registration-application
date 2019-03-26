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
    private Button cancel, groupadd_complete ;
    private EditText inviteEdit;
    String[] group_getEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groupadd);

        inviteEdit = (EditText)findViewById(R.id.invite);

        groupadd_complete = (Button)findViewById(R.id.groupadd_complete);
        groupadd_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group_getEdit = inviteEdit.getText().toString().split(",|/|\\s");
            }
        });



        cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    private void checkUID(String[] _getEdit){
        ConnectFireBaseDB.UserRead();
        for(int i = 0 ; i < MyApplication.userCount ; i++){

        }
    }


}
