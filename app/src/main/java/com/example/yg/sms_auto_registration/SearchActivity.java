package com.example.yg.sms_auto_registration;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {

    public EditText content;
    public SearchAdapter sAdapter;
    private RecyclerView sRecyclerView;
    String searchString = "";
    LocalDB database;
    private Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchlist);

        content = (EditText) findViewById(R.id.content);
        sRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        sRecyclerView.setHasFixedSize(true);
        sRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // call the adapter with argument list of items and context.
        LocalDB dbs=new LocalDB(this);
        sAdapter = new SearchAdapter(this, dbs.selectAll());
        sRecyclerView.setAdapter(sAdapter);

        addTextListener();
       /* content.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                switch (actionId) {

                    case EditorInfo.IME_ACTION_SEARCH:

                        Toast.makeText(getApplicationContext(), "검색", Toast.LENGTH_LONG).show();

                        break;

                    default:

                        Toast.makeText(getApplicationContext(), "기본", Toast.LENGTH_LONG).show();

                        return false;

                }

                return true;
            }
        });

    */

    }
    // this method is used to create list of items.

    public void addTextListener() {

        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String text = content.getText().toString().toLowerCase(Locale.getDefault());
                sAdapter.stringfilter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence query, int start, int before, int count) {

              /*  query = query.toString().toLowerCase();

                final ArrayList<SearchData> filteredList = new ArrayList<>();

                for (int i = 0; i < list.size(); i++) {
                    final String text = list.get(i).toLowerCase();
                    if (text.contains(query)) {

                        filteredList.add(list.get(i));
                    }*/
            }
        });
    }

    public void backWard(View view) {
        finish();
    }

    /*public static CharSequence highlight(String search, String originalText) {
        String normalizedText = Normalizer.normalize(originalText, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();
        int start = normalizedText.indexOf(search);
        if (start <= 0) {
            return originalText;
        } else {
            Spannable highlighted = new SpannableString(originalText);
            while (start > 0) {
                int spanStart = Math.min(start, originalText.length());
                int spanEnd = Math.min(start + search.length(), originalText.length());
                highlighted.setSpan(new BackgroundColorSpan(Color.YELLOW), spanStart, spanEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                start = normalizedText.indexOf(search, spanEnd);
            }
            return highlighted;
        }
    }*/

       /* //int count = AnniverListAdapter.getCount() ;   //db에서 아이템 갯수 지정해줘야함
        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.search_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        SearchAdapter adapter = new SearchAdapter(list);
        recyclerView.setAdapter(adapter);*/


}

