package com.example.yg.sms_auto_registration;

public class GrouplistData {
    private String user_name;

    public String getUser_name(){
        return user_name;
    }
    public void setUser_name(String user_name){
        this.user_name=user_name;
    }

    public GrouplistData(String user_name){
        this.user_name=user_name;
    }
}
