package com.example.yg.sms_auto_registration;

import android.widget.Button;
import android.widget.EditText;

public class GroupAddSingleton {
    private  static GroupAddSingleton instance;

    public Button addGroup;
    public EditText title, inviteEdit;
    public GroupAddActivity activity;

    public static GroupAddSingleton getInstance(){
        if(instance == null){
            instance = new GroupAddSingleton();
        }



        return instance;
    }
}
