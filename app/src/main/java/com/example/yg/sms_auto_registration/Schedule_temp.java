package com.example.yg.sms_auto_registration;

public class Schedule_temp {
    public int year;
    public int month;
    public int day;
    public String repeat;
    public String group_num, sche_title, sche_class;
    public int sche_num;

    public Schedule_temp(int year, int month, int day,String group_num,String sche_title,String sche_class,String repeat,int sche_num){
        this.year = year;
        this.month = month;
        this.day = day;
        this.group_num =group_num;
        this.sche_title = sche_title;
        this.sche_class =sche_class;
        this.repeat = repeat;
        this.sche_num = sche_num;
    }

}
