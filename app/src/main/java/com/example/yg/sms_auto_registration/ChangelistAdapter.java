package com.example.yg.sms_auto_registration;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ChangelistAdapter extends RecyclerView.Adapter<ChangelistAdapter.ViewHolder> {

    private ArrayList<ChangelistData> mData = new ArrayList<>();

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView change_user,change_year,change_title;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            change_user= itemView.findViewById(R.id.change_user);
            change_year = itemView.findViewById(R.id.change_year);
            change_title = itemView.findViewById(R.id.change_title);
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    ChangelistAdapter(ArrayList<ChangelistData> list) {
        mData = list;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public ChangelistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.changelist_item, parent, false);
        ChangelistAdapter.ViewHolder vh = new ChangelistAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(ChangelistAdapter.ViewHolder holder, int position) {
        ChangelistData item = mData.get(position);
        holder.change_user.setText(item.getChange_user());
        holder.change_year.setText(item.getChange_year());
        holder.change_title.setText(item.getChange_title());
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }
}
