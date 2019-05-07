package com.example.yg.sms_auto_registration;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Calendar;
import java.util.Date;

/**
 * Decorate several days with a dot
 */
public class EventDecorator implements DayViewDecorator {
    private final Calendar calendar = Calendar.getInstance();
    private int[] color;
    private CalendarDay day;
    int sche_year, sche_month, sche_day;
    int flag;
    public EventDecorator(int[] color, Date dates) {
        this.color = color;
        this.day = CalendarDay.from(dates);

    }

    public EventDecorator(int[] color,int sche_year,int sche_month, int sche_day,int flag) {
        this.color = color;
        this.sche_year = sche_year;
        this.sche_month =sche_month;
        this.sche_day = sche_day;
        this.flag = flag;
    }



    @Override
    public boolean shouldDecorate(CalendarDay day) {

        day.copyTo(calendar);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int date = calendar.get(Calendar.DATE);

        if(flag ==1) {
            for (int i = 0; i < GroupFragment.schedule_date.size(); i++) {
                if (year == this.sche_year && month == this.sche_month && (date == this.sche_day || date == this.sche_day || date == this.sche_day)) {

                    return true;

                }
            }
        }
        if(flag==2) {
            for (int i = 0; i < PersonalFragment.todo_list.size(); i++) {
                if (year == this.sche_year && month == this.sche_month && (date == this.sche_day || date == this.sche_day || date == this.sche_day)) {

                    return true;

                }
            }
        }



        return false;

    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan((new CustmMultipleDotSpan(7,color)));
    }
}