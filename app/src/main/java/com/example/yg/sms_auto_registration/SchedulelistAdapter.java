package com.example.yg.sms_auto_registration;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class SchedulelistAdapter extends RecyclerView.Adapter<SchedulelistAdapter.ViewHolder> {

    private ArrayList<SchedulelistData> mData = new ArrayList<>();
    private Cursor cursor;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView schelist_year, schelist_title;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            schelist_year = itemView.findViewById(R.id. do_sche_search_year);
            schelist_title = itemView.findViewById(R.id.do_sche_search_title);
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    SchedulelistAdapter(ArrayList<SchedulelistData> list) {
        mData = list;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public SchedulelistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.doshesearch_item, parent, false);
        SchedulelistAdapter.ViewHolder vh = new SchedulelistAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(SchedulelistAdapter.ViewHolder holder, int position) {
        SchedulelistData item = mData.get(position);
        //String year=cursor.getString(2);
        // String title=cursor.getString(6);
        // holder.schelist_year.setText(year);
        //holder.schelist_title.setText(title);
        holder.schelist_year.setText(item.getSchelist_year());
        holder.schelist_title.setText(item.getSchelist_title());
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }
}
