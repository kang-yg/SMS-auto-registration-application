package com.example.yg.sms_auto_registration;

import android.widget.Button;
import android.widget.Spinner;

public class MainSingleton {
    private static MainSingleton instance;

    public Button group_btn;
    public Spinner spinner;
    public MainActivity activity;

    public static MainSingleton getInstance() {
        if(instance == null) {
            instance = new MainSingleton();
        }

        return instance;
    }
}
