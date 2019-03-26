package com.example.yg.sms_auto_registration;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;

public class NotiMainActivity extends Activity {

    private int notificationId = 0;

//    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notibtn);


        findViewById(R.id.noti_btn).setOnClickListener(new View.OnClickListener() {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            @Override
            public void onClick(View v) {

                //ntent detailsIntent = new Intent(NotiMainActivity.this, NotiActivity.class);
                //detailsIntent.putExtra("EXTRA_DETAILS_ID", 42);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel notificationChannel = new NotificationChannel("channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT);
                    notificationChannel.setDescription("channel description");
                    notificationChannel.enableLights(true);
                    notificationChannel.setLightColor(Color.GREEN);
                    notificationChannel.enableVibration(true);
                    notificationChannel.canShowBadge();
                   // notificationChannel.setVibrationPattern(new long[]{100, 200, 100, 200});
                    notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
                    notificationManager.createNotificationChannel(notificationChannel);

                }


                Bitmap mLargeIconForNoti = BitmapFactory.decodeResource(getResources(), R.drawable.sample);
                Log.e("here1","error1");

                PendingIntent mPendingIntent = PendingIntent.getActivity(NotiMainActivity.this, 0,
                        //detailsIntent,
                        new Intent(getApplicationContext(), NotiMainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
                Log.e("here2","error2");

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(NotiMainActivity.this,"channel_id")
                        .setSmallIcon(R.drawable.garu)
                        .setContentTitle("노티 제목")
                        .setContentText("노티내용!!~~")
                        .setDefaults(Notification.DEFAULT_VIBRATE)  //진동으로 푸시옴
                        .setLargeIcon(mLargeIconForNoti)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true)   //한번터치하면 사라지게함
                        .setContentIntent(mPendingIntent);    //푸시 알림창 터치시 NotiMainActivity로 돌아가게함

               mBuilder.setStyle(new NotificationCompat.BigTextStyle().setBigContentTitle("스미스 캘린더"));

                Log.e("here3","error3");
                notificationManager.notify(0, mBuilder.build());
                Log.e("here1","error4");
            }
        });
    }
}




