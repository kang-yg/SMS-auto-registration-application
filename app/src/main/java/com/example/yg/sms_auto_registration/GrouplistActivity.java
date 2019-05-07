package com.example.yg.sms_auto_registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class GrouplistActivity extends Activity { //그룹참여자 리스트 액티비티

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groupuserlist);

        Intent intent = getIntent();
        int groupNum = intent.getExtras().getInt("GroupNum");
        ConnectFireBaseDB.getUserNameForUserList(groupNum);

        GroupListSingleton groupListSingleton = GroupListSingleton.getInstance();
        groupListSingleton.mRecyclerView = (RecyclerView) findViewById(R.id.group_recycler_view);
        groupListSingleton.mRecyclerView.setHasFixedSize(true);
        groupListSingleton.mlayoutManager = new LinearLayoutManager(this);
        groupListSingleton.mRecyclerView.setLayoutManager(groupListSingleton.mlayoutManager);
        groupListSingleton.activity = this;




    }

    public void backWard(View view) {
        finish();
    }
}
