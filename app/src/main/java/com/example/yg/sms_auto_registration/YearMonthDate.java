package com.example.yg.sms_auto_registration;

public class YearMonthDate { //주단위 보기에 사용되는 년월일 저장 클래스 1900~ 2100년 년월일 저장
    private int year;
    private int month;
    private int day;

    public YearMonthDate(int year,int month, int day){
        this.year=year;
        this.month=month;
        this.day=day;
    }

    public int getYear() {
        return year;
    }


    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }
}
