package com.example.yg.sms_auto_registration;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ViewHolder> {

    private ArrayList<TodoListData> mData = new ArrayList<>();

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dolist_year, dolist_title;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            dolist_year = itemView.findViewById(R.id. do_sche_search_year);
            dolist_title = itemView.findViewById(R.id.do_sche_search_title);
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    TodoListAdapter(ArrayList<TodoListData> list) {
        mData = list;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public TodoListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.doshesearch_item, parent, false);
        TodoListAdapter.ViewHolder vh = new TodoListAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(TodoListAdapter.ViewHolder holder, int position) {
        TodoListData item = mData.get(position);
        holder.dolist_year.setText(item.getDolist_year());
        holder.dolist_title.setText(item.getDolist_title());
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }
}
