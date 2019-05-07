package com.example.yg.sms_auto_registration;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WeekviewAdapter_inner  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private  ArrayList<Personal_todosliding> todo_list;


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView todo_week;
        ViewHolder(View itemView) {
            super(itemView) ;
            todo_week = itemView.findViewById(R.id.txt_example_week);



        }
    }

    WeekviewAdapter_inner(ArrayList<Personal_todosliding> todo_list)
    {
        this.todo_list = todo_list;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_fragment_viewtype_item_inner, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder myViewHolder = (ViewHolder)holder;


        myViewHolder.todo_week.setText(todo_list.get(position).todo);
//        myViewHolder.todo_week.setTextColor(Color.BLACK);

    }


    @Override
    public int getItemCount() {
        return todo_list.size();
    }

}
