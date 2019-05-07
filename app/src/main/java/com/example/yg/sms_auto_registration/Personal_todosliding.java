package com.example.yg.sms_auto_registration;

public class Personal_todosliding {
    public String todo;
    public int color;
    public String year,month,day;

    public Personal_todosliding(String todo, int color){
        this.todo=todo;
        this.color = color;
    }
    public Personal_todosliding(String todo, int color,String year,String month,String day){
        this.year= year;
        this.month = month;
        this.day = day;
        this.todo=todo;
        this.color = color;
    }

}
