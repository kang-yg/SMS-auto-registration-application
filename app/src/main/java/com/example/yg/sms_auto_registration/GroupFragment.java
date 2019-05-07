package com.example.yg.sms_auto_registration;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GroupFragment extends Fragment implements View.OnClickListener, OnDateSelectedListener, OnMonthChangedListener //그룹 캘린더 초기화면 표시 클래스
{
    DayViewDecorator eventDecorator ;

    private Button btn_create;//초기화면 그룹캘린더 생성버튼
    private FloatingActionButton fab, fab1, fab2, fab3, fab4;//플로팅 버튼
    private Boolean isFabOpen = false; //플로팅 열려있는지 판단하는 플래그
    private Animation slide_up, slide_down;//오늘날짜 클릭시 올라오고 내려오는 애니메이션
    private Date date1;//날짜 변수
    private Calendar cal;//캘린더 변수
    private int dayNum,day,year,month;//요일,일,년,월 변수
    private TextView slide_text;//오늘날짜 클릭시 올라오는 레이아웃에 표시되는 텍스트뷰
    private Date selectedDate = null;
    private RelativeLayout relativeLayout;
    public static DatabaseReference myRef1,myRef2;
    public static RelativeLayout wrap_group;
    public static ArrayList<Group_material> materialCalendarView_group = new ArrayList<>();
    public static MaterialCalendarView set_materialCalendarView;
    public static int[] color_group;
    private ArrayList<String> group_number = new ArrayList<>();
    private ArrayList<String> schedule_number = new ArrayList<>();
    public static ArrayList<Schedule_temp> schedule_date =new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    public static ArrayList<Today_text> todo_list = new ArrayList<>();
    public static ArrayList<Schedule_temp> remove_date = new ArrayList<>();
    SlidingTodayAdapter myAdapter;
    public static ChildEventListener scheduleEventListener,childEventListener;




    public GroupFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.caleandar_fragment_group, container, false);

        //초기 그룹캘린더 생성버튼
        btn_create = (Button) layout.findViewById(R.id.btn_create);
        //초기 그룹캘린더 생성버튼 클릭 리스너
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GroupAddActivity.class);
                startActivity(intent);
            }
        });

        //플로팅버튼 객체
        fab = (FloatingActionButton) layout.findViewById(R.id.fab);//+버튼
        fab1 = (FloatingActionButton) layout.findViewById(R.id.fab1);//일정등록 버튼
        fab2 = (FloatingActionButton) layout.findViewById(R.id.fab2);//할일등록 버튼
        fab3 = (FloatingActionButton) layout.findViewById(R.id.fab3);//기념일등록 버튼
        fab4 = (FloatingActionButton) layout.findViewById(R.id.fab4);//참석자명단 버튼

        //플로팅버튼 애니메이션 클릭 리스너
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);
        fab4.setOnClickListener(this);



        fab4.setOnClickListener(new View.OnClickListener() {//그룹캘린더 참여자 클릭 리스너
            @Override
            public void onClick(View v) {
                ConnectFireBaseDB.GroupRead();

                Intent intent = new Intent(getActivity(), GrouplistActivity.class);
                intent.putExtra("GroupNum", MyApplication.currentGroupNum);
                Log.d("UserRead4", String.valueOf(MyApplication.currentGroupNum));
                startActivity(intent);
            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {//기념일 등록 클릭 리스너
            @Override
            public void onClick(View v) {
//                ConnectFireBaseDB.GroupRead();
                ConnectFireBaseDB.ScheduleNumberRead();
                Intent intent = new Intent(getActivity(), AnniversaryAddActivity.class);
                intent.putExtra("GroupNum", MyApplication.currentGroupNum);
                intent.putExtra("ScheduleNumber", MyApplication.scheduleNumber);
                startActivity(intent);
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() { //할 일 등록 클릭 리스너
            @Override
            public void onClick(View v) {
                ConnectFireBaseDB.GroupRead();
                //ConnectFireBaseDB.ScheduleNumberRead();
                Intent intent = new Intent(getActivity(), Group_TodoAddActivity.class);
                intent.putExtra("GroupNum", MyApplication.currentGroupNum);
                intent.putExtra("ScheduleNumber", MyApplication.scheduleNumber);
                startActivity(intent);
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() { //일정 등록 클릭 리스너
            @Override
            public void onClick(View v) {
                //ConnectFireBaseDB.GroupRead();
                ConnectFireBaseDB.ScheduleNumberRead();
                Intent intent = new Intent(getActivity(), AddGroupSchedule.class);
                intent.putExtra("GroupNum", MyApplication.currentGroupNum);
                intent.putExtra("ScheduleNumber", MyApplication.scheduleNumber);
                startActivity(intent);
                Log.d("긁2",String.valueOf(MyApplication.scheduleNumber));
            }
        });

        //오늘날짜 선택시 올라오고 내려가고 애니메이션
        slide_up = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_up);
        slide_down = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_down);

        wrap_group=(RelativeLayout)layout.findViewById(R.id.wrap_group);
        set_materialCalendarView = (MaterialCalendarView)layout.findViewById(R.id.Mcalendar);
        slide_text = (TextView)layout.findViewById(R.id.txt_slide_today);
        relativeLayout = (RelativeLayout)layout.findViewById(R.id.layout_slide_today);

        myRef1=FirebaseDatabase.getInstance().getReference().child("Group");

         childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                group_number.clear();
                //materialCalendarView_group.clear();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    for(DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                        if (dataSnapshot2.getValue().toString().equals(MyApplication.localUser_uid)) {
                            group_number.add(dataSnapshot.getKey());//자신이 속한 그루핑 넘버 저장
                        }else{
                            continue;
                        }

                    }
                }

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){//자신이 속한 그룹의 이름을 변수에 저장
                    for(int i=0;i<group_number.size();i++) {
                        if(group_number.get(i).equals(dataSnapshot.getKey()) && dataSnapshot1.getKey().equals("GroupName")){

                            materialCalendarView_group.add(new Group_material(dataSnapshot.getKey(),dataSnapshot1.getValue().toString()));
                        }
                    }
                }
                for(int i=0;i<materialCalendarView_group.size();i++){
                    Log.d("왁",materialCalendarView_group.get(i).num+materialCalendarView_group.get(i).GroupName);
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {


            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        myRef1.addChildEventListener(childEventListener);

        myRef2=FirebaseDatabase.getInstance().getReference().child("GroupSchedule");
         scheduleEventListener = new ChildEventListener() {
            int i=0;
            String group_num,sche_title,sche_class,repeat_num;
            int sche_number;

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                for(int j=0;j<materialCalendarView_group.size();j++) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        if (dataSnapshot1.getKey().equals("ScheduleGroupNumber") && dataSnapshot1.getValue().toString().equals(materialCalendarView_group.get(j).num)) {
                            schedule_number.add(dataSnapshot.getKey());
                            group_num = dataSnapshot1.getValue().toString();
                        }
                    }
                }
                for (int i = 0; i < schedule_number.size(); i++) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        if (schedule_number.get(i).equals(dataSnapshot.getKey()) && dataSnapshot1.getKey().equals("ScheduleTitle")) {
                            sche_title = dataSnapshot1.getValue().toString();
                        }
                        if (schedule_number.get(i).equals(dataSnapshot.getKey()) && dataSnapshot1.getKey().equals("ScheduleNumber")) {
                            sche_number = Integer.parseInt(String.valueOf(dataSnapshot1.getValue()));
                        }
                        if (schedule_number.get(i).equals(dataSnapshot.getKey()) && dataSnapshot1.getKey().equals("ScheduleClassification")) {
                            sche_class = dataSnapshot1.getValue().toString();
                        }
                        if (schedule_number.get(i).equals(dataSnapshot.getKey()) && dataSnapshot1.getKey().equals("ScheduleRepeat")) {
                            repeat_num = dataSnapshot1.getValue().toString();
                        }
                        if (schedule_number.get(i).equals(dataSnapshot.getKey()) && dataSnapshot1.getKey().equals("StartDate")) {
                            String[] year_arr = dataSnapshot1.getValue().toString().split("-");
                            schedule_date.add(new Schedule_temp(Integer.parseInt(year_arr[0]), Integer.parseInt(year_arr[1]), Integer.parseInt(year_arr[2]),group_num,sche_title,sche_class,repeat_num,sche_number));
                        }

                    }
                }

                for (int i=0;i<GroupFragment.materialCalendarView_group.size();i++) {
                    if(MainActivity.mainSingleton.group_btn.getText().toString().equals(GroupFragment.materialCalendarView_group.get(i).GroupName)) {
                        for(int j=0;j<GroupFragment.schedule_date.size();j++) {
                            if(GroupFragment.materialCalendarView_group.get(i).num.equals(GroupFragment.schedule_date.get(j).group_num)) {
                                GroupFragment.color_group = new int[1];
                                eventDecorator=new EventDecorator(GroupFragment.color_group,GroupFragment.schedule_date.get(j).year,GroupFragment.schedule_date.get(j).month,GroupFragment.schedule_date.get(j).day,1);
                                GroupFragment.set_materialCalendarView.addDecorator(eventDecorator);
                            }
                        }
                    }
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                        for(int i =0;i<schedule_date.size();i++){
                            if(dataSnapshot1.getKey().equals("ScheduleNumber")) {
                                if(dataSnapshot1.getValue().toString().equals(String.valueOf(schedule_date.get(i).sche_num))){
                                    schedule_date.remove(i);
                                }

                            }
                        }
                }


            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        myRef2.addChildEventListener(scheduleEventListener);

//
//
//
//            년월바 안보이게
        set_materialCalendarView.setVisibility(View.VISIBLE);
        wrap_group.setVisibility(View.GONE);
        set_materialCalendarView.setTopbarVisible(false);
        set_materialCalendarView.setOnDateChangedListener(this);
        set_materialCalendarView.setOnMonthChangedListener(this);
        set_materialCalendarView.state().edit()//캘린더 상태 설정
                .setFirstDayOfWeek(Calendar.SUNDAY)//일요일 시작
                .setMinimumDate(CalendarDay.from(1900, 1, 1))//최소 년월일
                .setMaximumDate(CalendarDay.from(2100, 12, 31))//최대 년월일
                .setCalendarDisplayMode(CalendarMode.MONTHS)//캘린더 월단위 표시
                .commit();
        set_materialCalendarView.addDecorators(//캘린더 토요일 일요일 색상 변경
                new SundayDecorator(),
                new SaturdayDecorator(),
                new OndateDecorator()
        );


        mRecyclerView  = (RecyclerView) layout.findViewById(R.id.slide_recycler);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        color_group = new int[1];

        return layout;
    }


    @Override
    public void onClick(View v) {
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

    public void anim() { // 플로팅 버튼 클릭시 호출되는 메소드

        if (isFabOpen) {
            fab1.startAnimation(PersonalFragment.fab_close);
            fab2.startAnimation(PersonalFragment.fab_close);
            fab3.startAnimation(PersonalFragment.fab_close);
            fab4.startAnimation(PersonalFragment.fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            fab3.setClickable(false);
            fab4.setClickable(false);

            isFabOpen = false;
        } else {
            fab1.startAnimation(PersonalFragment.fab_open);
            fab2.startAnimation(PersonalFragment.fab_open);
            fab3.startAnimation(PersonalFragment.fab_open);
            fab4.startAnimation(PersonalFragment.fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            fab3.setClickable(true);
            fab4.setClickable(true);

            isFabOpen = true;
        }
    }



    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        todo_list.clear();
        for(int j=0;j<materialCalendarView_group.size();j++) {
            if(MainSingleton.getInstance().group_btn.getText().toString().equals(materialCalendarView_group.get(j).GroupName)) {
                for (int i = 0; i < schedule_date.size(); i++) {
                    if(materialCalendarView_group.get(j).num.equals(schedule_date.get(i).group_num)) {
                        if(schedule_date.get(i).repeat.equals("1")) {
                            if (date.getYear() == schedule_date.get(i).year && date.getDay() == schedule_date.get(i).day) {
                                int color = 0;
                                if (schedule_date.get(i).sche_class.equals("1")) {
                                    color = getResources().getColor(R.color.black);
                                } else if (schedule_date.get(i).sche_class.equals("2")) {
                                    color = getResources().getColor(R.color.orangered);
                                } else if (schedule_date.get(i).sche_class.equals("3")) {
                                    color = getResources().getColor(R.color.cyan);
                                }
                                todo_list.add(new Today_text(schedule_date.get(i).sche_title, color,schedule_date.get(i).sche_num));
                            }
                        }else if(schedule_date.get(i).repeat.equals("2")) {
                            if (date.getMonth()+1 == schedule_date.get(i).month && date.getDay() == schedule_date.get(i).day) {
                                int color = 0;
                                if (schedule_date.get(i).sche_class.equals("1")) {
                                    color = getResources().getColor(R.color.black);
                                } else if (schedule_date.get(i).sche_class.equals("2")) {
                                    color = getResources().getColor(R.color.orangered);
                                } else if (schedule_date.get(i).sche_class.equals("3")) {
                                    color = getResources().getColor(R.color.cyan);
                                }
                                todo_list.add(new Today_text(schedule_date.get(i).sche_title, color,schedule_date.get(i).sche_num));
                            }
                        }else{
                            if (date.getYear() == schedule_date.get(i).year &&date.getMonth()+1 == schedule_date.get(i).month && date.getDay() == schedule_date.get(i).day) {
                                int color = 0;
                                if (schedule_date.get(i).sche_class.equals("1")) {
                                    color = getResources().getColor(R.color.black);
                                } else if (schedule_date.get(i).sche_class.equals("2")) {
                                    color = getResources().getColor(R.color.orangered);
                                } else if (schedule_date.get(i).sche_class.equals("3")) {
                                    color = getResources().getColor(R.color.cyan);
                                }
                                todo_list.add(new Today_text(schedule_date.get(i).sche_title, color,schedule_date.get(i).sche_num));
                            }
                        }

                    }
                }
            }
        }
        myAdapter = new SlidingTodayAdapter(todo_list,getContext());
        mRecyclerView.setAdapter(myAdapter);




//        myAdapter = new SlidingTodayAdapter(todo_list);
//        mRecyclerView.setAdapter(myAdapter);

        day= date.getDay();
        date1 =date.getDate();//날짜 얻기
        cal = Calendar.getInstance();//캘린더 인스턴스 생성
        cal.setTime(date1);//캘린더 날짜 설정


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
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        year = date.getYear();
        month = date.getMonth();

        for(int k=0;k<schedule_date.size();k++){
            if (schedule_date.get(k).repeat.equals("1") && date.getMonth()+1>=schedule_date.get(k).month){
                set_materialCalendarView.addDecorator(new EventDecorator(color_group,schedule_date.get(k).year,month+1,schedule_date.get(k).day,1));

            }else if(schedule_date.get(k).repeat.equals("2") && date.getYear()>=schedule_date.get(k).year){
                set_materialCalendarView.addDecorator(new EventDecorator(color_group,date.getYear(),schedule_date.get(k).month,schedule_date.get(k).day,1));
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
            set_materialCalendarView.setTileHeight(240);
            selectedDate = null;
        } else {
            relativeLayout.setVisibility(View.VISIBLE);
            relativeLayout.startAnimation(slide_up);
            set_materialCalendarView.setTileHeight(160);
            relativeLayout.setClickable(true);
            selectedDate = date;
        }
    }
    public static void SetToday(){
        Date date = new Date();
        set_materialCalendarView.setCurrentDate(date);
    }


}


