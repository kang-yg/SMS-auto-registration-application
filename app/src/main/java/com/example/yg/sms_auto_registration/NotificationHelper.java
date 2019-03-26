package com.example.yg.sms_auto_registration;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class NotificationHelper extends ContextWrapper {

    private Context base;

    private  static final String CHANNEL_NAME="Main Noti";
    private static final String CHANNEL_DESCRIPTION="Noti description";
    private static final String CHANNEL_ID="MyNotificationChannel";

    NotificationManager notifManager        //add
            = (NotificationManager) getSystemService  (Context.NOTIFICATION_SERVICE);

    private static NotificationManager notificationManager;

    public NotificationHelper(Context base) {
        super(base);

        this.base=base;

        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            int importance = NotificationManager.IMPORTANCE_HIGH;   //add
            createChannel();
            Log.e("렉","에러1");

            NotificationChannel mChannel = new NotificationChannel(     //add
                    CHANNEL_ID, CHANNEL_NAME, importance);
            notifManager.createNotificationChannel(mChannel);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannel() {
        Log.e("렉","여긴지나감?");
        NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.setDescription(CHANNEL_DESCRIPTION);
        notificationChannel.enableVibration(true);
        notificationChannel.enableLights(true);
        notificationChannel.canShowBadge();
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
      //  notificationChannel.setLightColor(getResources().getColor(R.color.white));
        notificationChannel.setLightColor(getResources().getColor(R.color.white));
        notificationChannel.setLightColor(Color.GREEN);

        //notificationManager().createNotificationChannel(notificationChannel);
        notifManager.createNotificationChannel(notificationChannel);    //add
        Log.e("렉","에러2");
    }

  /*  public NotificationManager getNotificationManager(){

        if(notificationManager == null){

            notificationManager=(NotificationManager) base.getSystemService(Context.NOTIFICATION_SERVICE);

        }
        return notificationManager;
    }*/

    //now lets create notification

    public void notify(String message, String title){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(base,CHANNEL_ID);
       // Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSmallIcon(R.drawable.garu);      //add
        builder.setContentTitle(title);
        builder.setContentText(message);
       // builder.setSound(notificationUri);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));

       // NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
       //notificationManager.notify(0,builder.build());
        notifManager.notify(0,builder.build());
    }

}
