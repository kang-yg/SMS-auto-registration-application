package com.example.yg.sms_auto_registration;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class EnrollListActivity  extends AppCompatActivity {
    LocalDB database;
    private Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enrollist);

        /*ArrayList<EnrollData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {        //들어가나 테스트해본거 실제로 사용할땐 for문지우고 사용한다
            list.add(new EnrollData("제목","년도"));
        }*/

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.enlist_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //  recyclerView.setHasFixedSize(true);

        LocalDB dbs=new LocalDB(this);
        // 리사이클러뷰에 EnrollListAdapter 객체 지정.
        EnrollListAdapter adapter = new EnrollListAdapter(dbs.enrollList());
        // EnrollListAdapter adapter = new EnrollListAdapter(dbs.enrollList());
        recyclerView.setAdapter(adapter);
    }

    public void backWard(View view) {
        finish();
    }

}
