package com.example.yg.sms_auto_registration;

import android.support.v7.widget.RecyclerView;

public class GroupListSingleton {
    private static GroupListSingleton instance;

    public RecyclerView mRecyclerView;
    public GrouplistActivity activity;
    public RecyclerView.LayoutManager mlayoutManager;

    public static GroupListSingleton getInstance() {
        if(instance == null) {
            instance = new GroupListSingleton();
        }

        return instance;
    }
}
