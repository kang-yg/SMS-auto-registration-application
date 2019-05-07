package com.example.yg.sms_auto_registration;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

//import static com.example.yg.sms_auto_registration.DatePickerFragment.day;

public class AnniverListAdapter extends RecyclerView.Adapter<AnniverListAdapter.ViewHolder> {

    private ArrayList<AnniverListData> mData = new ArrayList<>();





    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView anniver_title, date ,anniver_dday;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            anniver_title = itemView.findViewById(R.id.anniver_title );
            date = itemView.findViewById(R.id.anniver_year);
            anniver_dday = itemView.findViewById(R.id.anniver_dday);

        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    AnniverListAdapter(ArrayList<AnniverListData> list) {
        mData = list;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public AnniverListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.anniverlist_item, parent, false);
        AnniverListAdapter.ViewHolder vh = new AnniverListAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(AnniverListAdapter.ViewHolder holder, int position) {

        AnniverListData item = mData.get(position);
        holder.anniver_title.setText(item.getAnniver_title());
        holder.date.setText(item.getAnniver_year());


        holder.anniver_dday.setText(item.getAnniver_dday());

    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }
}

