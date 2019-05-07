package com.example.yg.sms_auto_registration;



import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ViewtypeFragment extends Fragment   //보기방식 화면 표시 클래스
{
    public static  ArrayList<YearMonthDate> week_list;
    public static  ArrayList<String> dayNum_list;
    public static int item_position;
    private int year,month;
    public static RecyclerView mRecyclerView;
    public static RecyclerView.LayoutManager mLayoutManager;
    private String date;
    public static Iterator<YearMonthDate> it;
    int i=0;
    public static ArrayList<Today_text> todo_list = new ArrayList<>();
    public static Context view_context;


    private int week_day=1,week_year=1900,week_month=1,dayNum;
    public ViewtypeFragment()
    {
    }
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.calendar_fragment_viewtype, container, false);

        Calendar calendar = Calendar.getInstance();

        week_list = new ArrayList<>(); // 1900 ~ 2100년 년월일 저장 클래스 어레이리스트
        dayNum_list = new ArrayList<>();// 요일 저장 String 어레이리스트

        mRecyclerView  = (RecyclerView) layout.findViewById(R.id.recycler_viewtype); // 주 단위 리사이클러 뷰 객체
        mLayoutManager = new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.HORIZONTAL);//그리드 레이아웃 매니저 생성
        mRecyclerView.setLayoutManager(mLayoutManager);//리사이클러 뷰에 레이아웃 매니저 부착

        for(week_year=1900;week_year<=2100;week_year++) {//1900~2100년
            for(week_month=1;week_month<=12;week_month++) {//1월~12월

                calendar.set(week_year,month-1,week_day);//월이 바뀔 때 마다 캘린더 날짜 변경
                switch(calendar.getActualMaximum(Calendar.DAY_OF_MONTH)){//해당 달의 마지막 일
                    case 28://마지막 일이 28일인 경우
                        while(week_day<=28) {//28번 반복
                            calendar.set(week_year,week_month-1,week_day);//일이 바뀔 때 마다 캘린더 날짜 변경
                            dayNum= calendar.get(Calendar.DAY_OF_WEEK);//캘린더 요일 구하는 변수
                            week_list.add(new YearMonthDate(week_year,week_month,week_day));//년월일 클래스 어레이리스트 삽입
                            week_day += 1;//일 수 증가
                            switch (dayNum)//요일 판별
                            {
                                case 1:
                                    dayNum_list.add("일");
                                    break;
                                case 2:
                                    dayNum_list.add("월");
                                    break;
                                case 3:
                                    dayNum_list.add("화");
                                    break;
                                case 4:
                                    dayNum_list.add("수");
                                    break;
                                case 5:
                                    dayNum_list.add("목");
                                    break;
                                case 6:
                                    dayNum_list.add("금");
                                    break;
                                case 7:
                                    dayNum_list.add("토");
                                    break;
                            }
                        }
                        week_day-=28;//일 수 초기화
                        break;
                    case 30://위와 동일
                        while(week_day<=30) {
                            calendar.set(week_year,week_month-1,week_day);
                            dayNum= calendar.get(Calendar.DAY_OF_WEEK);
                            week_list.add(new YearMonthDate(week_year,week_month,week_day));
                            week_day += 1;
                            switch (dayNum)
                            {
                                case 1:
                                    dayNum_list.add("일");
                                    break;
                                case 2:
                                    dayNum_list.add("월");
                                    break;
                                case 3:
                                    dayNum_list.add("화");
                                    break;
                                case 4:
                                    dayNum_list.add("수");
                                    break;
                                case 5:
                                    dayNum_list.add("목");
                                    break;
                                case 6:
                                    dayNum_list.add("금");
                                    break;
                                case 7:
                                    dayNum_list.add("토");
                                    break;
                            }

                        }
                        week_day-=30;
                        break;
                    case 31://위와 동일
                        while(week_day<=31) {
                            calendar.set(week_year,week_month-1,week_day);
                            dayNum= calendar.get(Calendar.DAY_OF_WEEK);
                            week_list.add(new YearMonthDate(week_year,week_month,week_day));
                            week_day += 1;
                            switch (dayNum)
                            {
                                case 1:
                                    dayNum_list.add("일");
                                    break;
                                case 2:
                                    dayNum_list.add("월");
                                    break;
                                case 3:
                                    dayNum_list.add("화");
                                    break;
                                case 4:
                                    dayNum_list.add("수");
                                    break;
                                case 5:
                                    dayNum_list.add("목");
                                    break;
                                case 6:
                                    dayNum_list.add("금");
                                    break;
                                case 7:
                                    dayNum_list.add("토");
                                    break;
                            }
                        }
                        week_day-=31;
                        break;
                    default:
                        break;
                }
            }
        }




        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd");//Material calendar의 date 형식을 dd 형식으로
        CalendarDay calendarDay = PersonalFragment.materialCalendarView.getCurrentDate();//오늘 날짜 정보 얻기
        date= dateFormat1.format(calendarDay.getDate());//date 포맷변경
        year = calendarDay.getYear();//년도 얻기
        month = calendarDay.getMonth()+1;//월 얻기 +1 필수

        it = week_list.iterator();//어레이리스트 탐색을 위한 이터레이터
        i=0;
        while (it.hasNext() && i<=week_list.size()-1){//어레이리스트 탐색
            String convert = String.valueOf(week_list.get(i).getDay());//YearMonthDay 클래스에 있는 일을 String 타입으로
            if(convert.equals("1")||convert.equals("2")||convert.equals("3")||convert.equals("4")||convert.equals("5")||convert.equals("6")||convert.equals("7")||convert.equals("8")||convert.equals("9"))
                convert = "0"+convert;//convert가 한자리 일 수면 앞에 0을 더해줌
            if(week_list.get(i).getYear() == year && week_list.get(i).getMonth()==month&&convert.equals(date)){
                //오늘 년월일 비교해서 포지션 이동을 위한 값을 얻는다.
                item_position = i;
                MainActivity.ChangeTopbar(week_list.get(i).getYear()+"년"+week_list.get(i).getMonth()+"월");
                break;
            }
            i+=1;
        }


        view_context =getContext();


//        WeekviewAdapter myAdapter = new WeekviewAdapter(ViewtypeFragment.week_list,ViewtypeFragment.dayNum_list,view_context);//어댑터 객체
//        ViewtypeFragment.mRecyclerView.setAdapter(myAdapter);//어댑터 부착
        mRecyclerView.hasFixedSize();//몰라 하면 좋데


        return layout;
    }


}


