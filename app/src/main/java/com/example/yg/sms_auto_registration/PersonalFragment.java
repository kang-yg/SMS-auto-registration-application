package com.example.yg.sms_auto_registration;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PersonalFragment extends Fragment implements OnDateSelectedListener, OnMonthChangedListener, View.OnClickListener//개인 캘린더 생성 및 표시 클래스
{
    public static ArrayList<SearchData> todo_list = new ArrayList<>();
    public static ArrayList<Personal_todosliding> todo_arr = new ArrayList<>();
    RecyclerView mRecyclerView;
    public static RecyclerView.LayoutManager mLayoutManager;
    private TextView slide_text;//오늘날짜 클릭시 올라오는 레이아웃에 표시되는 텍스트뷰
    public static RelativeLayout relativeLayout;//프래그먼트 표시 할 레이아웃
    private Animation slide_up, slide_down;//오늘날짜 클릭시 올라오고 내려오는 애니메이션
    private FloatingActionButton fab, fab1, fab2, fab3;//플로팅 버튼
    private Boolean isFabOpen = false; //플로팅 열려있는지 판단하는 플래그
    protected static Animation fab_open;
    protected static Animation fab_close;//플로팅 버튼 열고닫기 애니메이션
    Personal_SlidingTodayAdapter adapter;
    public static LocalDB dbs;
    public static Date todayDate;
    private int dayNum,day,year,month;//요일,일,년,월 변수
    private Date selectedDate = null;
    private Date date1;//날짜 변수
    private Calendar cal;//캘린더 변수
    public static MaterialCalendarView materialCalendarView;//캘린더 변수
    public static int[] color_group;
    DayViewDecorator event;




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
                Intent intent = new Intent(getActivity(),AnniverAddActivity.class);
                startActivity(intent);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() { //할 일 등록 클릭 리스너
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TodoAddActivity.class);
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




        dbs=new LocalDB(getContext());
        todo_list = dbs.personal_slidingtoday();
        color_group = new int[1];
        PersonalFragment.materialCalendarView.removeDecorators();
        PersonalFragment.materialCalendarView.addDecorator(new SaturdayDecorator());
        PersonalFragment.materialCalendarView.addDecorator(new SundayDecorator());
        PersonalFragment.materialCalendarView.addDecorator(new OndateDecorator());
        PersonalFragment.todo_list = PersonalFragment.dbs.personal_slidingtoday();

        for(int i=0;i<PersonalFragment.todo_list.size();i++){
            String[] year_arr = PersonalFragment.todo_list.get(i).getSearchlist_year().split("년");
            String[] month_arr = year_arr[1].split("월");
            String[] day_arr = month_arr[1].split("일");

            PersonalFragment.color_group = new int[1];
            DayViewDecorator eventDecorator=new EventDecorator(PersonalFragment.color_group,Integer.parseInt(year_arr[0]),Integer.parseInt(month_arr[0]),Integer.parseInt(day_arr[0]),2);
            PersonalFragment.materialCalendarView.addDecorator(eventDecorator);
        }


        return layout;
    }



    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) { //달력의 Day 선택 시 동작

        todo_list = dbs.personal_slidingtoday();
        adapter = null;
        int color=0;
        todo_arr.clear();
        for (int i=0;i<todo_list.size();i++){
            Log.d("각",todo_list.get(i).getSearchlist_year());
            String[] year_arr = todo_list.get(i).getSearchlist_year().split("년");
            String[] month_arr = year_arr[1].split("월");
            String[] day_arr = month_arr[1].split("일");
            Log.d("긁",todo_list.get(i).getRepeat_num()+"");
            if(todo_list.get(i).getRepeat_num()==2) {
                if ( String.format("%02d", date.getMonth() + 1).equals(month_arr[0]) && String.valueOf(date.getDay()).equals(day_arr[0])) {
                    if (todo_list.get(i).getDivision() == 1) {
                        color = getResources().getColor(R.color.black);
                    }
                    if (todo_list.get(i).getDivision() == 2) {
                        color = getResources().getColor(R.color.orangered);
                    }
                    if (todo_list.get(i).getDivision() == 3) {
                        color = getResources().getColor(R.color.cyan);
                    }
                    todo_arr.add(new Personal_todosliding(todo_list.get(i).getSearchlist_title(), color));
                    adapter = new Personal_SlidingTodayAdapter(todo_arr,getContext());
                }
            }else if(todo_list.get(i).getRepeat_num()==1) {
                if ( String.valueOf(date.getYear()).equals(year_arr[0]) && String.valueOf(date.getDay()).equals(day_arr[0])) {
                    if (todo_list.get(i).getDivision() == 1) {
                        color = getResources().getColor(R.color.black);
                    }
                    if (todo_list.get(i).getDivision() == 2) {
                        color = getResources().getColor(R.color.orangered);
                    }
                    if (todo_list.get(i).getDivision() == 3) {
                        color = getResources().getColor(R.color.cyan);
                    }
                    todo_arr.add(new Personal_todosliding(todo_list.get(i).getSearchlist_title(), color));
                    adapter = new Personal_SlidingTodayAdapter(todo_arr,getContext());
                }
            }else{
                if ( String.valueOf(date.getYear()).equals(year_arr[0]) &&String.format("%02d", date.getMonth() + 1).equals(month_arr[0]) &&String.valueOf(date.getDay()).equals(day_arr[0])) {
                    if (todo_list.get(i).getDivision() == 1) {
                        color = getResources().getColor(R.color.black);
                    }
                    if (todo_list.get(i).getDivision() == 2) {
                        color = getResources().getColor(R.color.orangered);
                    }
                    if (todo_list.get(i).getDivision() == 3) {
                        color = getResources().getColor(R.color.cyan);
                    }
                    todo_arr.add(new Personal_todosliding(todo_list.get(i).getSearchlist_title(), color));
                    adapter = new Personal_SlidingTodayAdapter(todo_arr,getContext());
                }
            }
        }
        mRecyclerView.setAdapter(adapter);



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
        materialCalendarView.removeDecorator(event);
        for(int k=0;k<todo_list.size();k++){
            String[] year_arr = todo_list.get(k).getSearchlist_year().split("년");
            String[] month_arr = year_arr[1].split("월");
            String[] day_arr = month_arr[1].split("일");
            if (todo_list.get(k).getRepeat_num()==1&& date.getMonth()+1>=Integer.parseInt(month_arr[0])){
                event=new EventDecorator(color_group,Integer.parseInt(year_arr[0]),date.getMonth()+1,Integer.parseInt(day_arr[0]),2);
                materialCalendarView.addDecorator(event);
            }
            if (todo_list.get(k).getRepeat_num()==2&& date.getYear()>=Integer.parseInt(year_arr[0])){
                event=new EventDecorator(color_group,date.getYear(),Integer.parseInt(month_arr[0]),Integer.parseInt(day_arr[0]),2);
                materialCalendarView.addDecorator(event);
            }
        }

        month+=1;//월이 0부터 카운트되므로 +1 필수
        ((MainActivity)getActivity()).ChangeTopbar(year+"년"+month+"월");//메인 액티비티의 ChangeTopbar메소드 호출

    }

    public void selectAnim(Date date) { //오늘날짜 클릭 여부 판단 후 슬라이드올라오고 내려가게 하는 메소드


        if (selectedDate == date) {
            relativeLayout.startAnimation(slide_down);
            relativeLayout.setVisibility(View.GONE);
            relativeLayout.setClickable(false);
            materialCalendarView.setTileHeight(240);
            selectedDate = null;
        } else {
            relativeLayout.setVisibility(View.VISIBLE);
            relativeLayout.startAnimation(slide_up);
            materialCalendarView.setTileHeight(160);
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


