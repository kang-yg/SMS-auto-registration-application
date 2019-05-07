package com.example.yg.sms_auto_registration;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.annotation.Nullable;

public class AlarmVibrate extends Service {
    private static PowerManager.WakeLock mWakeLock;
    private static final String TAG = "AlarmWakeLock";
    private static final long[] VivratePattern = new long[] {500, 500};
    Vibrator vibrator;


    @SuppressLint("InvalidWakeLockTag")
    @Override
    public void onCreate() {
        super.onCreate();
        //휴대폰 power 관련 코드
        PowerManager pm = (PowerManager)getSystemService(Context.POWER_SERVICE);  // PowerManager를 얻는다.
        mWakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, TAG);
        mWakeLock.acquire();  // 휴대폰이 대기 상태로 가지 않도록 유지

        //AlarmLayout으로 화면 전환
        try {
            Intent mLayout = new Intent(this, AlarmLayout.class);
            PendingIntent pi = PendingIntent.getActivity(this,0,mLayout,0);
            pi.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    public int onStartCommand(Intent intent, int flags, int startld) {
        vibrator = (Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        if(intent==null) {
            return START_NOT_STICKY;
        }
        vibrator.vibrate(VivratePattern,0);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //진동중지
        vibrator.cancel();

        //휴대폰 power 관련 코드 해지
        if(mWakeLock != null) {
            mWakeLock.release();
            mWakeLock = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}


