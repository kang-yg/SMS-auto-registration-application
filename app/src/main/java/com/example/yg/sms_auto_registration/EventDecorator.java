package com.example.yg.sms_auto_registration;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import java.util.Date;

/**
 * Decorate several days with a dot
 */
public class EventDecorator implements DayViewDecorator {

    private int[] color;
    private CalendarDay day;
    public EventDecorator(int[] color, Date dates) {
        this.color = color;
        this.day = CalendarDay.from(dates);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        if (this.day.equals(day)){
            return true;
        }
        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan((new CustmMultipleDotSpan(10,color)));
    }
}