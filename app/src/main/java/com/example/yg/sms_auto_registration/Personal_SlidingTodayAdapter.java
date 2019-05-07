package com.example.yg.sms_auto_registration;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Personal_SlidingTodayAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    LocalDB database;
    private ArrayList<Personal_todosliding> todo_list;
    private Context context;
    static String content;
   // CheckBox checkBox;
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView todo_month;


        ViewHolder(View itemView) {
            super(itemView) ;
            final int[] color = {0};
            todo_month = itemView.findViewById(R.id.txt_example);
           // checkBox = itemView.findViewById(R.id.check_example);
            /*checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked == true){
                        todo_month.setPaintFlags(todo_month.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                        color[0] =todo_month.getCurrentTextColor();
                        todo_month.setTextColor(Color.BLACK);
                    }else if(isChecked ==false){
                        todo_month.setPaintFlags(0);
                        todo_month.setTextColor(color[0]);
                    }
                }
            });*/

            todo_month.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    for(int i=0;i<todo_list.size();i++) {
                        if (todo_month.getText().equals(todo_list.get(i).todo)){
                            Log.d("month내용", String.valueOf(todo_month.getText()));
                            content= (String) todo_month.getText();
                            // remove_num=String.valueOf(todo_list.get(i).sche_num);
                            show(i,todo_month);
//                            myRef.addChildEventListener(childEventListener);
//                            Toast.makeText(context, todo_list.get(i).todo+" (이)가 삭제되었습니다.", Toast.LENGTH_LONG).show();
//                            todo_month.setPaintFlags(todo_month.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
//                            todo_month.setTextColor(Color.BLACK);
                        }
                    }
                    return false;
                }
            });

        }
    }

    Personal_SlidingTodayAdapter(ArrayList<Personal_todosliding> todo_list,Context context)
    {
        this.todo_list = todo_list;
        this.context=context;
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
        myViewHolder.todo_month.setText(todo_list.get(position).todo);
        myViewHolder.todo_month.setTextColor(todo_list.get(position).color);


    }

    @Override
    public int getItemCount() {
        return todo_list.size();
    }
    void show(final int i, final TextView textView)
    {
        //Log.d("극",remove_num);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("일정삭제");
        builder.setMessage(todo_list.get(i).todo+" (을)를 삭제하시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // myRef.addChildEventListener(childEventListener);
                        database=new LocalDB(context);
                        database.delete(content);
                        Toast.makeText(context, "삭제되었습니다.", Toast.LENGTH_LONG).show();
                        textView.setPaintFlags(textView.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                        textView.setTextColor(Color.BLACK);
                        //checkBox.setVisibility(View.GONE);

                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context,"취소되었습니다.",Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }
}

