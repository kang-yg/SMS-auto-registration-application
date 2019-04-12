package com.example.yg.sms_auto_registration;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddDeviceEvent {
    public static String getDate(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy/MM/dd hh:mm:ss a");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        Log.d("Calendar", Long.toString(milliSeconds));
        return formatter.format(calendar.getTime());
    }

    public void setCalendar(Context context) {

        Cursor managedCursor = context.getContentResolver()
                .query(
                        Uri.parse("content://com.android.calendar/events"),
                        new String[]{"calendar_id", "title", "description",
                                "dtstart", "dtend", "eventLocation"}, null,
                        null, null);
        ;

        if (managedCursor.moveToFirst()) {
            int[] calendar_id = new int[managedCursor.getCount()];
            String[] title = new String[managedCursor.getCount()];
            String[] eventLocation = new String[managedCursor.getCount()];
            String[] description = new String[managedCursor.getCount()];
            String[] dtstart = new String[managedCursor.getCount()];
            String[] dtend = new String[managedCursor.getCount()];



            for (int i = 0; i < title.length; i++) {
                DeviceEvent deviceEvent = new DeviceEvent();

                calendar_id[i] = managedCursor.getInt(0);
                Log.d("Calendar", "ID : " + calendar_id[i]);
                title[i] = managedCursor.getString(1);
                Log.d("Calendar", "title : " + title[i]);
                description[i] = managedCursor.getString(2);
                Log.d("Calendar", "description : " + description[i]);
                dtstart[i] = managedCursor.getString(3);
                Log.d("Calendar", "dtstart : " + getDate(Long.parseLong(dtstart[i])));
                dtend[i] = managedCursor.getString(4);
                if (dtend[i] != null) {
                    Log.d("Calendar", "dtend : " + getDate(Long.parseLong(dtend[i])));
                }
                eventLocation[i] = managedCursor.getString(5);
                Log.d("Calendar", "eventLocation : " + eventLocation[i]);

                MyApplication.deviceEvents.add(new DeviceEvent());
                MyApplication.deviceEvents.get(i).setCalendar_id(calendar_id[i]);
                MyApplication.deviceEvents.get(i).setTitle(title[i]);
                MyApplication.deviceEvents.get(i).setDescription(description[i]);
                MyApplication.deviceEvents.get(i).setDtstart(dtstart[i]);
                MyApplication.deviceEvents.get(i).setDtend(dtend[i]);
                MyApplication.deviceEvents.get(i).setEventLocation(eventLocation[i]);

                Log.d("MyApplication", "MyApplication.deviceEvents[" + i + "] : " + MyApplication.deviceEvents.get(i).getCalendar_id());
                Log.d("MyApplication", "MyApplication.deviceEvents[" + i + "] : " + MyApplication.deviceEvents.get(i).getTitle());
                Log.d("MyApplication", "MyApplication.deviceEvents[" + i + "] : " + MyApplication.deviceEvents.get(i).getDescription());
                Log.d("MyApplication", "MyApplication.deviceEvents[" + i + "] : " + MyApplication.deviceEvents.get(i).getDtstart());
                Log.d("MyApplication", "MyApplication.deviceEvents[" + i + "] : " + MyApplication.deviceEvents.get(i).getDtend());
                Log.d("MyApplication", "MyApplication.deviceEvents[" + i + "] : " + MyApplication.deviceEvents.get(i).getEventLocation());

                managedCursor.moveToNext();
            }
            managedCursor.close();
        }

    }
}
