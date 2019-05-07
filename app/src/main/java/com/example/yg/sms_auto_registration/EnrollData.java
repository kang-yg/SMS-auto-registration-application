package com.example.yg.sms_auto_registration;

public class EnrollData {
    private String en_title,en_year;

    public String getEn_title(){
        return en_title;
    }

    public void setEn_title(String en_title){
        this.en_title=en_title;
    }

    public String getEn_year(){
        return en_year;
    }

    public void setEn_year(String en_year){
        this.en_year=en_year;
    }

    public EnrollData(){        //테스트할려고 넣은거
        this.en_title=getEn_title();
        this.en_year=getEn_year();
    }
}
