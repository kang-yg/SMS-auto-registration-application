package com.example.yg.sms_auto_registration;

import android.content.Context;
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

public class WeekviewAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static String date;
    private ArrayList<String> dayNum_list;
    private  ArrayList<Personal_todosliding> todo_list;
    private  ArrayList<Personal_todosliding> todo_list_inner= new ArrayList<>();
    private  int flag =0;
    private ArrayList<YearMonthDate> week_list;
    String text;
    Context context;
    WeekviewAdapter_inner adapter_inner;
    int i=0;
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView week_date,week_schedule,week_todo,week_celebrate;
        RecyclerView recyclerView_inner;
        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            week_date = itemView.findViewById(R.id.txt_Viewweek);
            recyclerView_inner = itemView.findViewById(R.id.recycler_inner);
            todo_list_inner.clear();

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            recyclerView_inner.setLayoutManager(mLayoutManager);
//             adapter_inner = new WeekviewAdapter_inner(todo_list);
//            recyclerView_inner.setAdapter(adapter_inner);
//            week_schedule=itemView.findViewById(R.id.txt_schedule);
//            week_todo=itemView.findViewById(R.id.txt_todo);
//            week_celebrate=itemView.findViewById(R.id.txt_celebrate);

        }
    }

    WeekviewAdapter(ArrayList<YearMonthDate> week_list,ArrayList<String> dayNum_list,Context context,ArrayList<Personal_todosliding> todo_list)
    {
        this.week_list = week_list;
        this.dayNum_list = dayNum_list;
        this.context = context;
        this.todo_list = todo_list;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_fragment_viewtype_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder myViewHolder = (ViewHolder)holder;

        try {
            if(position==0)  {
                myViewHolder.week_date.setText( week_list.get(position).getYear() + "년"+week_list.get(position).getMonth() + "월" + week_list.get(position).getDay() + "(" + dayNum_list.get(position)+")");
                myViewHolder.week_date.setTextSize(20);
            }

            if(week_list.get(position).getYear()!=week_list.get(position-1).getYear()&&week_list.get(position).getMonth()!=week_list.get(position-1).getMonth()){
                myViewHolder.week_date.setText( week_list.get(position).getYear() + "년"+week_list.get(position).getMonth() + "월" + week_list.get(position).getDay() + "(" + dayNum_list.get(position)+")");
                myViewHolder.week_date.setTextSize(20);
            }
            else if(week_list.get(position).getMonth()!=week_list.get(position-1).getMonth()&&week_list.get(position).getYear()==week_list.get(position-1).getYear()){
                myViewHolder.week_date.setText(week_list.get(position).getMonth() + "월" + week_list.get(position).getDay() + "(" + dayNum_list.get(position)+")");
                myViewHolder.week_date.setTextSize(20);
            }
            else{ myViewHolder.week_date.setText( week_list.get(position).getDay() + "(" + dayNum_list.get(position)+")"); myViewHolder.week_date.setTextSize(20);}
        }catch(IndexOutOfBoundsException e) {

            System.out.println(e);

        }
        for(int i=0;i<todo_list.size();i++){
            if(String.valueOf(week_list.get(position).getYear()).equals(todo_list.get(i).year)&&String.format("%02d",week_list.get(position).getMonth()).equals(todo_list.get(i).month)&&String.valueOf(week_list.get(position).getDay()).equals(todo_list.get(i).day)){
                todo_list_inner.add(new Personal_todosliding(todo_list.get(i).todo,todo_list.get(i).color));
                adapter_inner = new WeekviewAdapter_inner(todo_list_inner);
                myViewHolder.recyclerView_inner.setAdapter(adapter_inner);
                flag=1;
            }else{
                flag=0;
            }
        }

        if(flag ==1){
            Log.d("플래그1",String.valueOf(flag));
        }
        Log.d("플래그1",String.valueOf(flag));

    }


    @Override
    public int getItemCount() {
        return week_list.size();
    }

}
