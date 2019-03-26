package com.example.yg.sms_auto_registration;



import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.RelativeLayout;


import java.util.ArrayList;


public class ViewtypeFragment extends Fragment   //보기방식 화면 표시 클래스
{
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

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


        PersonalFragment.week_list = new ArrayList<>();
        for(int i =1;i<=300;i++) {
/*            PersonalFragment.week_list.add("11.화요일");
            PersonalFragment.week_list.add("12.수요일");*/
        }







        mRecyclerView  = (RecyclerView) layout.findViewById(R.id.recycler_viewtype);
        mLayoutManager = new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
/*        WeekviewAdapter myAdapter = new WeekviewAdapter(PersonalFragment.week_list);
        mRecyclerView.setAdapter(myAdapter);*/
        mRecyclerView.hasFixedSize();

        return layout;
    }


}


