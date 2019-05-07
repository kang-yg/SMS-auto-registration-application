package com.example.yg.sms_auto_registration;

public class AnniverListData {
    private String anniver_title,anniver_year,anniver_dday;

    public String getAnniver_title(){
        return anniver_title;
    }

    public void setAnniver_title(String anniver_title){
        this.anniver_title=anniver_title;
    }

    public String getAnniver_year(){
        return anniver_year;
    }

    public void setAnniver_year(String anniver_year){
        this.anniver_year=anniver_year;
    }

    public String getAnniver_dday(){
        return anniver_dday;
    }

    public void setAnniver_dday(String anniver_dday){
        this.anniver_dday=anniver_dday;
    }

    public AnniverListData(){
        this.anniver_year=anniver_year;
        this.anniver_title=anniver_title;
        this.anniver_dday=anniver_dday;
    }
}
