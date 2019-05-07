package com.example.yg.sms_auto_registration;

public class ChangelistData {
    private String change_year,change_user,change_title;

    public String getChange_year(){
        return change_year;
    }

    public void setChange_year(String change_year){
        this.change_year=change_year;
    }

    public String getChange_user(){
        return change_user;
    }

    public void setChange_user(String change_user){
        this.change_user=change_user;
    }

    public String getChange_title(){
        return change_title;
    }

    public void setChange_title(String change_title){
        this.change_title=change_title;
    }

    public ChangelistData(String change_year,String change_user, String change_title){
        this.change_year=change_year;
        this.change_user=change_user;
        this.change_title=change_title;
    }
}
