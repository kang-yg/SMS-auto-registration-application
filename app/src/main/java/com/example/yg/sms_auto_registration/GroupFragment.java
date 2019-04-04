package com.example.yg.sms_auto_registration;



import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

public class GroupFragment extends Fragment implements View.OnClickListener //그룹 캘린더 초기화면 표시 클래스
{
    private Button btn_create;//초기화면 그룹캘린더 생성버튼
    private FloatingActionButton fab, fab1, fab2, fab3, fab4, fab5;//플로팅 버튼
    private Boolean isFabOpen = false; //플로팅 열려있는지 판단하는 플래그


    public GroupFragment()
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
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.caleandar_fragment_group, container, false);

        //초기 그룹캘린더 생성버튼
        btn_create = (Button)layout.findViewById(R.id.btn_create);
        //초기 그룹캘린더 생성버튼 클릭 리스너
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),GroupAddActivity.class);
                startActivity(intent);
            }
        });

        //플로팅버튼 객체
        fab = (FloatingActionButton) layout.findViewById(R.id.fab);//+버튼
        fab1 = (FloatingActionButton) layout.findViewById(R.id.fab1);//일정등록 버튼
        fab2 = (FloatingActionButton) layout.findViewById(R.id.fab2);//할일등록 버튼
        fab3 = (FloatingActionButton) layout.findViewById(R.id.fab3);//기념일등록 버튼
        fab4 = (FloatingActionButton) layout.findViewById(R.id.fab4);//참석자명단 버튼
        fab5 = (FloatingActionButton) layout.findViewById(R.id.fab5);//그룹캘린더 변경내역 버튼

        //플로팅버튼 애니메이션 클릭 리스너
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);
        fab4.setOnClickListener(this);
        fab5.setOnClickListener(this);

        fab5.setOnClickListener(new View.OnClickListener() {//그룹캘린더 수정내역 클릭 리스너
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ChangelistActivity.class);
                startActivity(intent);
            }
        });

        fab4.setOnClickListener(new View.OnClickListener() {//그룹캘린더 참여자 클릭 리스너
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),GrouplistActivity.class);
                startActivity(intent);
            }
        });

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
                Log.d("mina", "aaaa");
                Intent intent = new Intent(getActivity(),AddGroupSchedule.class);
                startActivity(intent);
            }
        });



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
            fab5.startAnimation(PersonalFragment.fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            fab3.setClickable(false);
            fab4.setClickable(false);
            fab5.setClickable(false);

            isFabOpen = false;
        } else {
            fab1.startAnimation(PersonalFragment.fab_open);
            fab2.startAnimation(PersonalFragment.fab_open);
            fab3.startAnimation(PersonalFragment.fab_open);
            fab4.startAnimation(PersonalFragment.fab_open);
            fab5.startAnimation(PersonalFragment.fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            fab3.setClickable(true);
            fab4.setClickable(true);
            fab5.setClickable(true);

            isFabOpen = true;
        }
    }
}


