package com.example.yg.sms_auto_registration;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class ChangelistActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changelist);

        //int count = AnniverListAdapter.getCount() ;   //db에서 아이템 갯수 지정해줘야함
        ArrayList<ChangelistData> list = new ArrayList<>();
        for (int i = 1; i < 11; i++) {              //테스트용임으로 나중에 삭제할부분
            list.add(new ChangelistData("년도","변경자명","내용"));
        }

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.change_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        ChangelistAdapter adapter = new ChangelistAdapter(list);
        recyclerView.setAdapter(adapter);
    }
}
