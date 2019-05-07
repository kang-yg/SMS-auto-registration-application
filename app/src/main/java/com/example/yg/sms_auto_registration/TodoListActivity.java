package com.example.yg.sms_auto_registration;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class TodoListActivity extends AppCompatActivity {
    LocalDB database;
    private Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dolist);

        //int count = AnniverListAdapter.getCount() ;   //db에서 아이템 갯수 지정해줘야함
       /* ArrayList<TodoListData> list = new ArrayList<>();
        for (int i = 1; i < 11; i++) {              //테스트용임으로 나중에 삭제할부분
            list.add(new TodoListData("년도","제목"));
        }*/

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.do_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),TodoAddActivity.class);
                startActivity(intent);
            }
        });

        LocalDB dbs=new LocalDB(this);
        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        TodoListAdapter adapter = new TodoListAdapter(dbs.Todo_selectAll());
        recyclerView.setAdapter(adapter);
    }

    public void backWard(View view) {
        finish();
    }
}
