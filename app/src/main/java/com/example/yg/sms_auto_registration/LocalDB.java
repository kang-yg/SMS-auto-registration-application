package com.example.yg.sms_auto_registration;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

public class LocalDB {

    private static LocalDB database;

    private static String DATABASE_NAME = "SMS.db";

    private static String TABLE_NAME = "SCHEDULE";

    private static final int DATABASE_VERSION = 1;

    private DatabaseHelper dbHelper;    //더 성능이 좋고

    private SQLiteDatabase db;  //

    private Context context;

    public LocalDB(Context context) {
        this.context = context;
        this.dbHelper = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = dbHelper.getWritableDatabase();
    }


    public static LocalDB getInstance(Context context) {
        if (database == null) {
            database = new LocalDB(context);
        }
        return database;
    }

   /* public boolean open() {

        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();

        return true;
    }*/

    public void close() {
        db.close();
        database = null;
    }

    public Cursor rawQuery(String SQL) {

        Cursor c1 = null;
        try {
            c1 = db.rawQuery(SQL, null);
        } catch (Exception ex) {
        }

        return c1;
    }

    public boolean execSQL(String SQL) {

        try {
            db.execSQL(SQL);
        } catch (Exception ex) {
            return false;
        }

        return true;
    }

    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context, String dbname, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, dbname, null, version);
        }

        public void onCreate(SQLiteDatabase _db) {
            // drop existing table
            String DROP_SQL = "drop table if exists " + TABLE_NAME;
            try {
                _db.execSQL(DROP_SQL);
            } catch (Exception ex) {
            }

            // create table
            String CREATE_SQL = "create table " + TABLE_NAME + "(" + " _id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, " + " USERNUM INT, " + " SCHEDULEDIVISION INT, " + " STARTTIME TEXT, " + " ENDTIME TEXT, " + " STARTDATE TEXT, " + " ENDDATE TEXT, " + " ALARMDIVISION INT, " + " REPETITION INT, " + " TITLE TEXT, " + " CONTENT TEXT, " + " PLACE TEXT, " + " ENROLL INT, " + " CREATE_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP " + ")";
            try {
                _db.execSQL(CREATE_SQL);
            } catch (Exception ex) {
            }

        }


        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (oldVersion < 2) {   // version 1
            }

        }
    }

    public void insertRecord(int USERNUM, int SCHEDULEDIVISION, String STARTTIME, String ENDTIME, String STARTDATE, String ENDDATE, int ALARMDIVISION, int REPETITION, String TITLE, String CONTENT, String PLACE, int ENROLL) {
        // try {
        db.execSQL("insert into " + TABLE_NAME + "(USERNUM,SCHEDULEDIVISION,STARTTIME,ENDTIME,STARTDATE,ENDDATE,ALARMDIVISION,REPETITION,TITLE,CONTENT,PLACE,ENROLL) values ('" + USERNUM + "', '" + SCHEDULEDIVISION + "', '" + STARTTIME + "', '" + ENDTIME + "', '" + STARTDATE + "', '" + ENDDATE + "', '" + ALARMDIVISION + "', '" + REPETITION + "', '" + TITLE + "', '" + CONTENT + "', '" + PLACE + "', '" + ENROLL + "');");
        //   } catch (Exception ex) {
        //    }
    }


    public ArrayList<SchedulelistData> Schedule_selectAll() {    //일정목록 데이터조회
        ArrayList<SchedulelistData> result = new ArrayList<SchedulelistData>();
        // try {
        /*if (database != null) { //db여는거
            database.close();
            database = null;
        }*/
        db = dbHelper.getWritableDatabase();    //데이터 읽어와서 검색하는겨 이문장은 데이터 읽어오는거
        // SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where SCHEDULEDIVISION=1 and ENROLL=0 ", null);

        // cursor.moveToFirst(); // add
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {

            SchedulelistData datas = new SchedulelistData();
            datas.setSchelist_year(cursor.getString(5));
            datas.setSchelist_title(cursor.getString(9));

            //SchedulelistData info = new SchedulelistData(STARTDATE, TITLE);
            result.add(datas);
            //  Log.i("날짜", datas.getSchelist_year());
            //  cursor.moveToNext();
            cursor.moveToNext();
        }


        // } catch (Exception ex) {
        //     ex.printStackTrace();
        //  }

        return result;
    }

    public ArrayList<TodoListData> Todo_selectAll() {    //할일 목록 데이터조회
        ArrayList<TodoListData> result = new ArrayList<TodoListData>();
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where SCHEDULEDIVISION=2", null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {

            TodoListData datas = new TodoListData();
            datas.setDolist_year(cursor.getString(5));
            datas.setDolist_title(cursor.getString(9));

            //SchedulelistData info = new SchedulelistData(STARTDATE, TITLE);
            result.add(datas);
            cursor.moveToNext();
        }
        return result;
    }

    public ArrayList<AnniverListData> Anniversary_selectAll() {    //기념일 목록데이터조회
        ArrayList<AnniverListData> result = new ArrayList<AnniverListData>();
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where SCHEDULEDIVISION=3", null);
        Cursor cursors = db.rawQuery("select STARTDATE from " + TABLE_NAME + " where SCHEDULEDIVISION=3", null);
        cursor.moveToFirst();
        cursors.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {

            AnniverListData datas = new AnniverListData();
            datas.setAnniver_year(cursor.getString(5));
            datas.setAnniver_title(cursor.getString(9));
            datas.setAnniver_dday(getDDay(cursors.getString(0)));

            result.add(datas);
            cursor.moveToNext();
            cursors.moveToNext();
        }
        return result;
    }

    //d-day계산
    public String getDDay(String s1) {
        db = dbHelper.getWritableDatabase();

        Calendar today = Calendar.getInstance();
        Calendar select_day = Calendar.getInstance();
        String ss, s2, s3 = null;
        int Year = today.get(Calendar.YEAR);
        int Month = today.get(Calendar.MONTH) + 1;
        int Day = today.get(Calendar.DAY_OF_MONTH);
        Log.d("월얼마나나왐", String.valueOf(Month));     //4
        long D_day = 0;
        ss = s1.replaceAll("년", "");
        s2 = ss.replaceAll("월", "");
        s3 = s2.replaceAll("일", "");
        Log.d("s3의값은 뭘까",s3);
        Log.d("자른s3의값은 뭘까", String.valueOf(Integer.parseInt(s3.substring(0, 4))));
//        int d_Year= oneDatePickerFragment.year;
//        int d_Month=oneDatePickerFragment.month;
//        int d_Day=oneDatePickerFragment.day;
        // Log.d("내가가져온년도", String.valueOf(d_Year));     //1
        today.set(Year, Month, Day, 0, 0, 0);
        select_day.set(Integer.parseInt(s3.substring(0, 4)), Integer.parseInt(s3.substring(4, 6)), Integer.parseInt(s3.substring(6, s3.length())), 0, 0, 0);

        // d_day.set(year,month-1, day);
        long sum = select_day.getTimeInMillis() - today.getTimeInMillis();
        Log.d("select", String.valueOf(select_day.getTimeInMillis()));
        Log.d("today", String.valueOf(today.getTimeInMillis()));

        Log.d("sum값", String.valueOf(sum));


        D_day = sum / (24 * 60 * 60 * 1000);
        if (D_day > 0) return "D-" + String.valueOf(D_day);
        if (D_day == 0) return "D-day";
        else return "지난일정";
    }

    public ArrayList<SearchData> selectAll() {    //검색에서 데이터조회
        ArrayList<SearchData> result = new ArrayList<SearchData>();
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " ", null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {

            SearchData datas = new SearchData();
            datas.setSearchlist_year(cursor.getString(5));
            datas.setSearchlist_title(cursor.getString(9));

            result.add(datas);
            cursor.moveToNext();
        }
        return result;
    }

    public ArrayList<SearchData> personal_slidingtoday() {    //검색에서 데이터조회
        ArrayList<SearchData> result = new ArrayList<SearchData>();
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " ", null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {

            SearchData datas = new SearchData();
            datas.setSearchlist_year(cursor.getString(5));//년도
            datas.setSearchlist_title(cursor.getString(9));//타이틀
            datas.setDivision(cursor.getInt(2));//일정구분
            datas.setRepeat_num(cursor.getInt(8));

            result.add(datas);
            cursor.moveToNext();
        }
        return result;
    }

    public ArrayList<EnrollData> enrollList() {    //검색에서 데이터조회
        ArrayList<EnrollData> result = new ArrayList<EnrollData>();
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where ENROLL=1", null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {

            EnrollData datas = new EnrollData();
            datas.setEn_title(cursor.getString(9));
            datas.setEn_year(cursor.getString(5));

            result.add(datas);
            cursor.moveToNext();
        }
        return result;
    }

    public void delete(String content) {
        db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE TITLE= '" + content + "';");

        //db.delete(TABLE_NAME, "TITLE=" + content, null);
    }


}





