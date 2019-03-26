package com.example.yg.sms_auto_registration;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.format.DayFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PersonalFragment extends Fragment implements OnDateSelectedListener, OnMonthChangedListener, View.OnClickListener//개인 캘린더 생성 및 표시 클래스
{
    public static ArrayList<String> todo_list;
    public static ArrayList<Calendar> week_list;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    private TextView slide_text;//오늘날짜 클릭시 올라오는 레이아웃에 표시되는 텍스트뷰
    private RelativeLayout relativeLayout;//프래그먼트 표시 할 레이아웃
    private Animation slide_up, slide_down;//오늘날짜 클릭시 올라오고 내려오는 애니메이션
    private FloatingActionButton fab, fab1, fab2, fab3;//플로팅 버튼
    private Boolean isFabOpen = false; //플로팅 열려있는지 판단하는 플래그
    protected static Animation fab_open;
    protected static Animation fab_close;//플로팅 버튼 열고닫기 애니메이션

    public static Date todayDate;
    private int dayNum,day,year,month;//요일,일,년,월 변수
    private Date selectedDate = null;
    private Date date1;//날짜 변수
    private Calendar cal;//캘린더 변수
    static MaterialCalendarView materialCalendarView;//캘린더 변수



    public PersonalFragment()
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


        //오늘날짜 선택시 올라오고 내려가고 애니메이션
        slide_up = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_up);
        slide_down = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_down);

        //플로팅버튼 애니메이션
        fab_open = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);



        //개인 캘린더 프래그먼트 레이아웃 설정
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.caleandar_fragment_personal, container, false);
        materialCalendarView= (MaterialCalendarView) layout.findViewById(R.id.Mcalendar);
        slide_text = (TextView)layout.findViewById(R.id.txt_slide_today);
        relativeLayout = (RelativeLayout)layout.findViewById(R.id.layout_slide_today);
        relativeLayout.setVisibility(View.GONE);



        //플로팅버튼 객체
        fab = (FloatingActionButton) layout.findViewById(R.id.fab);//+버튼
        fab1 = (FloatingActionButton) layout.findViewById(R.id.fab1);//일정등록 버튼
        fab2 = (FloatingActionButton) layout.findViewById(R.id.fab2);//할일등록 버튼
        fab3 = (FloatingActionButton) layout.findViewById(R.id.fab3);//기념일등록 버튼

        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);




        fab3.setOnClickListener(new View.OnClickListener() {//기념일 등록 클릭 리스너
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AnniversaryAddActivity.class);
                startActivity(intent);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() { //할 일 등록 클릭 리스너
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),TodoAddActivity.class);
                startActivity(intent);
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() { //일정 등록 클릭 리스너
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ScheduleAddActivity.class);
                startActivity(intent);
            }
        });


        //년월바 안보이게
        materialCalendarView.setTopbarVisible(false);
        materialCalendarView.setOnDateChangedListener(this);
        materialCalendarView.setOnMonthChangedListener(this);
        materialCalendarView.state().edit()//캘린더 상태 설정
                .setFirstDayOfWeek(Calendar.SUNDAY)//일요일 시작
                .setMinimumDate(CalendarDay.from(1900, 1, 1))//최소 년월일
                .setMaximumDate(CalendarDay.from(2100, 12, 31))//최대 년월일
                .setCalendarDisplayMode(CalendarMode.MONTHS)//캘린더 월단위 표시
                .commit();
        materialCalendarView.addDecorators(//캘린더 토요일 일요일 색상 변경
                new SundayDecorator(),
                new SaturdayDecorator(),
                new OndateDecorator()

        );


        mRecyclerView  = (RecyclerView) layout.findViewById(R.id.slide_recycler);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        todo_list = new ArrayList<>();
        todo_list.add("화장실가기");





        return layout;
    }



    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) { //달력의 Day 선택 시 동작

        SlidingTodayAdapter myAdapter = new SlidingTodayAdapter(todo_list);
        mRecyclerView.setAdapter(myAdapter);



        day= date.getDay();
        date1 =date.getDate();//날짜 얻기
        cal = Calendar.getInstance();//캘린더 인스턴스 생성
        cal.setTime(date1);//캘린더 날짜 설정

        /**날찌밑에 점찍기
        //int[] color = new int[2];
        //color[0]=Color.RED;

        //materialCalendarView.addDecorator(new EventDecorator(color,date.getDate()));*/


        dayNum=cal.get(Calendar.DAY_OF_WEEK);//요일 얻기

        selectAnim(date.getDate());


        switch (dayNum) { //숫자로 요일 판단
            case 1:
                slide_text.setText(day + ". 일");
                break;
            case 2:
                slide_text.setText(day + ". 월");
                break;
            case 3:
                slide_text.setText(day + ". 화");
                break;
            case 4:
                slide_text.setText(day + ". 수");
                break;
            case 5:
                slide_text.setText(day + ". 목");
                break;
            case 6:
                slide_text.setText(day + ". 금");
                break;
            case 7:
                slide_text.setText(day + ". 토");
                break;
        }

    }


    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) { //달력의 Month 변할 때 동작
        year = date.getYear();
        month = date.getMonth();
        todayDate = date.getDate();

        month+=1;//월이 0부터 카운트되므로 +1 필수
        ((MainActivity)getActivity()).ChangeTopbar(year+"년"+month+"월");//메인 액티비티의 ChangeTopbar메소드 호출

    }

    public void selectAnim(Date date) { //오늘날짜 클릭 여부 판단 후 슬라이드올라오고 내려가게 하는 메소드


            if (selectedDate == date) {
                relativeLayout.startAnimation(slide_down);
                relativeLayout.setVisibility(View.GONE);
                relativeLayout.setClickable(false);
                materialCalendarView.setTileHeight(300);
                selectedDate = null;
            } else {
                relativeLayout.setVisibility(View.VISIBLE);
                relativeLayout.startAnimation(slide_up);
                materialCalendarView.setTileHeight(175);
                relativeLayout.setClickable(true);
                selectedDate = date;
            }
    }

    public void anim() { // 플로팅 버튼 클릭시 호출되는 메소드

        if (isFabOpen) {
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            fab3.setClickable(false);
            isFabOpen = false;
        } else {
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            fab3.setClickable(true);
            isFabOpen = true;
        }
    }
    @Override
    public void onClick(View v) { //클릭 메소드
        int id = v.getId();


        switch (id) {
            case R.id.fab:
                anim();
                break;
            case R.id.fab1:
                anim();
                break;
            case R.id.fab2:
                anim();
                break;
            case R.id.fab3:
                anim();
                break;


        }
    }

    public static void SetToday(){
        Date date = new Date();
        materialCalendarView.setCurrentDate(date);
    }

}


