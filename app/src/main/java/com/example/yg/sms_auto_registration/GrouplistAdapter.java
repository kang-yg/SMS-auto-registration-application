package com.example.yg.sms_auto_registration;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class GrouplistAdapter extends RecyclerView.Adapter<GrouplistAdapter.ViewHolder> {

    private ArrayList<GrouplistData> mData = new ArrayList<>();

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView user_name;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            user_name = itemView.findViewById(R.id.groupUserListItemName);
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    GrouplistAdapter(ArrayList< GrouplistData> list) {
        mData = list;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public  GrouplistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.groupuser_item, parent, false);
        GrouplistAdapter.ViewHolder vh = new  GrouplistAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder( GrouplistAdapter.ViewHolder holder, int position) {
        GrouplistData item = mData.get(position);
        holder.user_name.setText(item.getUser_name());
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }
}
