package com.example.yg.sms_auto_registration;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent mServiceintent = new Intent(context,AlarmVibrate.class);
        context.startService(mServiceintent);
    }
}

