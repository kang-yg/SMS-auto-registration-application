package com.example.yg.sms_auto_registration;

public class SearchData {
    private String searchlist_year,searchlist_title;
    private int division;
    private int repeat_num;


    public int getDivision(){
        return division;
    }

    public void setDivision(int division){
        this.division=division;
    }
    public int getRepeat_num(){
        return repeat_num;
    }

    public void setRepeat_num(int repeat_num){
        this.repeat_num=repeat_num;
    }

    public String getSearchlist_year(){
        return searchlist_year;
    }

    public void setSearchlist_year(String searchlist_year){
        this.searchlist_year=searchlist_year;
    }

    public String getSearchlist_title(){
        return searchlist_title;
    }

    public void setSearchlist_title(String searchlist_title){
        this.searchlist_title=searchlist_title;
    }

    public SearchData(){        //나중에 지울부분
        this.searchlist_year=searchlist_year;
        this.searchlist_title=searchlist_title;
    }
}
