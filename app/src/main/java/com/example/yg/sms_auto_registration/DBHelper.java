package com.example.yg.sms_auto_registration;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    //DBHelper 생성자로 관리할 DB이름과 버전 정보를 받음
    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    //DB를 새로 생성할 때 호출되는 함수
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user" +
                "(userNumer INTEGER PRIMARY KEY, " +
                "googleID TEXT NOT NULL)");
        db.execSQL("CREATE TABLE groupInfo" +
                "(groupNumber INTEGER PRIMARY KEY, " +
                "userNumber INTEGER NOT NULL, " +
                "groupName TEXT NOT NULL, " +
                "FOREIGN KEY(userNumber) REFERENCES user(userNumer))");
        db.execSQL("CREATE TABLE groupSchedule " +
                "(scheduleNumer INTEGER PRIMARY KEY, " +
                "scheduleClassification INTEGER NOT NULL," +
                "userNumber INTEGER NOT NULL, " +
                "startDate TEXT NOT NULL, " +
                "endDate TEXT NOT NULL, " +
                "title TEXT, " +
                "content TEXT, " +
                "groupNumber INTEGER NOT NULL, " +
                "place TEXT, " +
                "repetition INTEGER, " +
                "FOREIGN KEY(userNumber) REFERENCES user(userNumer)," +
                "FOREIGN KEY(groupNumber) REFERENCES groupInfo(groupNumber))");
        db.execSQL("CREATE TABLE schedule" +
                "(scheduleNumber INTEGER PRIMARY KEY, " +
                "userNumber INTEGER NOT NULL, " +
                "scheduleclassification INTEGER NOT NULL, " +
                "startDate TEXT NOT NULL, " +
                "endDate TEXT NOT NULL," +
                "alarm INTEGER, " +
                "repetition INTEGER, " +
                "title TEXT, " +
                "content TEXT, " +
                "place  TEXT," +
                "FOREIGN KEY(userNumber) REFERENCES user(userNumer))");
        db.execSQL("CREATE TABLE SMSRegistration" +
                "(registrationNumber INTEGER PRIMARY KEY, " +
                "scheduleNumber INTEGER NOT NULL, " +
                "registrationDate TEXT," +
                "FOREIGN KEY(scheduleNumber) REFERENCES schedule(scheduleNumber))");
    }

    @Override
    //DB업그레이드를 위해 버전이 변경될 때 호출되는 함수
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Schedule테이블 테이터 삽입
    public void insertSchedule(int _scheduleNumber, int _userNumber, int _scheduleclassification, String _startDate,
                               String _endDate, int _alarm, int _repetition, String _title, String _content, String _place){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO schedule VALUES("+_scheduleNumber+", "+_userNumber+", "+_scheduleclassification+", '"+_startDate+"', " +
                "'"+_endDate+"', "+_alarm+", "+_repetition+", "+_title+", "+_content+", "+_place+")");
        db.close();
    }

    //Schedule테이블 데이터 수정
    public void updateSchedule(String _startDate, String _endDate, int _alarm, int _repetition, String _title, String _content, String _place){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE schedule SET startDate = '"+_startDate+"', endDate = '"+_endDate+"', alarm = "+_alarm+", repetition = "+_repetition+", " +
                "title = '"+_title+"', content = '"+_content+"', place = '"+_place+"'");
        db.close();
    }

    //Schedule테이블 데이터 삭제
    public void deleteSchedule(int _scheduleNumber){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM schedule WHERE scheduleNumber = "+_scheduleNumber+"");
        db.close();
    }

    //Schedule테이블 데이터 읽기
    public String getResult(){
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        Cursor cursor = db.rawQuery("SELECT * FROM schedule",null);
        while(cursor.moveToNext()){
            result += cursor.getString(0);
        }
        return result;
    }
}
