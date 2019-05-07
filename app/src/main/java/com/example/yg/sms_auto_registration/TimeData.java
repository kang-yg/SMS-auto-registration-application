package com.example.yg.sms_auto_registration;

public class TimeData {

    int hour;
    int minute;
    String state;

    public int getHourOfDay() {
        return hour;
    }

    public void setHourOfDay(int hourOfDay) {
        this.hour = hourOfDay;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getState(){
        return state;
    }

    public void setState(String state){
        this.state=state;
    }
    public TimeData(){
        this.hour=getHourOfDay();
        this.minute=getMinute();
        this.state=getState();
    }
   /* public String toString(){
        return this.hour+":"+this.minute;
    }*/
}
