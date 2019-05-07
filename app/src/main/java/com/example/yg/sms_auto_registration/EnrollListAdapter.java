package com.example.yg.sms_auto_registration;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class EnrollListAdapter extends RecyclerView.Adapter<EnrollListAdapter.ViewHolder> {

    private ArrayList<EnrollData> mData = new ArrayList<>();

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView en_year, en_title;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            en_year = itemView.findViewById(R.id.en_year);
            en_title = itemView.findViewById(R.id.en_title);
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public EnrollListAdapter(ArrayList<EnrollData> list) {
        mData = list;
    }
    /*public void add (EnrollData data){
        mData.add(data);
        notifyDataSetChanged(); //어댑터에서 데이터 변화할때 알려주는 역할!

    }*/

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public EnrollListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.enroll_item, parent, false);
        EnrollListAdapter.ViewHolder vh = new EnrollListAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(EnrollListAdapter.ViewHolder holder, int position) {
        EnrollData item = mData.get(position);
        holder.en_year.setText(item.getEn_year());
        holder.en_title.setText(item.getEn_title());
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }

}