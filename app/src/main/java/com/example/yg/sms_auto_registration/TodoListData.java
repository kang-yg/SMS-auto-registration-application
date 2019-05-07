package com.example.yg.sms_auto_registration;

public class TodoListData {
    private String dolist_year,dolist_title;

    public String getDolist_year(){
        return dolist_year;
    }

    public void setDolist_year(String dolist_year){
        this.dolist_year=dolist_year;
    }

    public String getDolist_title(){
        return dolist_title;
    }

    public void setDolist_title(String dolist_title){
        this.dolist_title=dolist_title;
    }

    public TodoListData(){        //나중에 지울부분
        this.dolist_year=dolist_year;
        this.dolist_title=dolist_title;
    }
}
