package com.example.yg.sms_auto_registration;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class SlidingTodayAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<String> todo_list;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView todo_month;

        ViewHolder(View itemView) {
            super(itemView) ;


                todo_month = itemView.findViewById(R.id.txt_example);

        }
    }

    SlidingTodayAdapter(ArrayList<String> todo_list)
    {
        this.todo_list = todo_list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_todayview, parent, false);
        return new ViewHolder(v);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder myViewHolder = (ViewHolder)holder;
        myViewHolder.todo_month.setText(todo_list.get(position));

    }

    @Override
    public int getItemCount() {
        return todo_list.size();
    }
}
