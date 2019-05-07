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

public class AnniverListActivity extends AppCompatActivity {
    LocalDB database;
    private Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anniverlist);

        //int count = AnniverListAdapter.getCount() ;   //db에서 아이템 갯수 지정해줘야함
        //ArrayList<AnniverListData> list = new ArrayList<>();

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(),AnniverAddActivity.class);
                startActivity(intent);
            }
        });
        LocalDB dbs=new LocalDB(this);
        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        AnniverListAdapter adapter = new AnniverListAdapter(dbs.Anniversary_selectAll());
        recyclerView.setAdapter(adapter);
    }


    public void backWard(View view) {
        finish();
    }
}
