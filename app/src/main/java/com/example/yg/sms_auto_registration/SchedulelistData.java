package com.example.yg.sms_auto_registration;

public class SchedulelistData {
    private String schelist_year,schelist_title;

    public String getSchelist_year(){
        return schelist_year;
    }

    public void setSchelist_year(String schelist_year){
        this.schelist_year=schelist_year;
    }

    public String getSchelist_title(){
        return schelist_title;
    }

    public void setSchelist_title(String schelist_title){
        this.schelist_title=schelist_title;
    }

    @Override
    public String toString() {
        return "SchedulelistData{" + "schelist_year='" + schelist_year + '\'' + ", schelist_title='" + schelist_title + '\'' + '}';
    }


}
