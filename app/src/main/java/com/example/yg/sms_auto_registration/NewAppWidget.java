package com.example.yg.sms_auto_registration;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Calendar calendar = Calendar.getInstance();
        int year,month,day;

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DATE);

        CharSequence widgetText = MainActivity.topbar.getText();
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_text, String.valueOf(year)+"년"+String.valueOf(month)+"월"+String.valueOf(day)+"일");
        Log.d("굵",year+"");

        for(int i=0;i<PersonalFragment.todo_list.size();i++) {
            String[] year_arr = PersonalFragment.todo_list.get(i).getSearchlist_year().split("년");
            String[] month_arr = year_arr[1].split("월");
            String[] day_arr = month_arr[1].split("일");
            if(String.valueOf(year).equals(year_arr[0]) && String.format("%02d",month).equals(month_arr[0])&&String.valueOf(day).equals(day_arr[0])) {
                views.setTextViewText(R.id.text1, PersonalFragment.todo_list.get(i).getSearchlist_title());

            }
        }
        // 앱을 띄우고 MainActivity 로 이동한다.
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(new ComponentName(context, MainActivity.class));
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.appwidget_text, pi);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

