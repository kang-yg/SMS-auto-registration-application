package com.example.yg.sms_auto_registration;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;


import java.util.ArrayList;

public class WeekviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> week_list;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView week_date;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            week_date = itemView.findViewById(R.id.txt_Viewweek);
        }
    }

    WeekviewAdapter(ArrayList<String> week_list) {
        this.week_list = week_list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_fragment_viewtype_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder myViewHolder = (ViewHolder) holder;
        myViewHolder.week_date.setText(week_list.get(position));

    }

    @Override
    public int getItemCount() {
        return week_list.size();
    }
}
