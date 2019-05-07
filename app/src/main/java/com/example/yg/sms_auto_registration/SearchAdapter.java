package com.example.yg.sms_auto_registration;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private Context mCtx;
    String searchString = "";
    private List<SearchData> items= new ArrayList<>();
    private ArrayList<SearchData> arrayList;


    public SearchAdapter(Context context, List<SearchData> items) {
        this.mCtx=context;
        this.items=items;
        arrayList = new ArrayList<SearchData>();
        arrayList.addAll(items);
    }

    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.doshesearch_item, parent, false);
       SearchAdapter.ViewHolder vh = new SearchAdapter.ViewHolder(view);
       // View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.doshesearch_item,null);
        return vh;
        //return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SearchData item=items.get(position);

       // holder.searchlist_title.setText(item.getSearchlist_title());
        holder.searchlist_year.setText(item.getSearchlist_year());
        holder.searchlist_title.setText(item.getSearchlist_title());



        String highlight = item.getSearchlist_title().toLowerCase(Locale.getDefault());     //색깔이 안들어가ㅠㅠ
        if (highlight.contains(searchString)) {
            int startPos =  highlight.indexOf(searchString);
            int endPos = startPos + searchString.length();

            Spannable spanText = Spannable.Factory.getInstance().newSpannable(item.getSearchlist_title());
            spanText.setSpan(new ForegroundColorSpan(Color.YELLOW), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.searchlist_title.setText(spanText, TextView.BufferType.SPANNABLE);
        }
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public void stringfilter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());

        items.clear();
        if (charText.length() == 0) {
            items.addAll(arrayList);
        } else {
            for (SearchData recent : arrayList) {
                String name = recent.getSearchlist_title();
                String year= recent.getSearchlist_year();

                if (name.toLowerCase().contains(charText)) {    //제목부분 대소문자 검색 상관없게 하기
                    items.add(recent);
                }
                if (year.toLowerCase().contains(charText)) {    //년도부분 대소문자 검색 상관없게 하기
                    items.add(recent);
                }
            }
        }
        notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView searchlist_year;
        TextView searchlist_title;

        public ViewHolder(View itemView) {
            super(itemView);

            searchlist_title= (TextView) itemView.findViewById(R.id.do_sche_search_title);
            searchlist_year = (TextView) itemView.findViewById(R.id.do_sche_search_year);

        }


    }



  /*  //private List<String> item ;
    //public Context mcontext;
    public ArrayList<SearchData> mData = new ArrayList<>();

    public SearchAdapter(ArrayList<SearchData> mData) {

        mData = mData;
    }

    // Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a layout
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.doshesearch_item, null);

        SearchAdapter.ViewHolder ViewHolder = new  SearchAdapter.ViewHolder(view);
        return ViewHolder;
    }

    // Called by RecyclerView to display the data at the specified position.
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position ) {
        SearchData item = mData.get(position);
        //viewHolder.search_title.setText((CharSequence)item.get(position));
        viewHolder.searchlist_year.setText(item.getSearchlist_year());
        viewHolder.searchlist_title.setText(item.getSearchlist_title());
       //클릭시 이동하게하자
        viewHolder.searchlist_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            //    Toast.makeText(this,mData.get(position),
                        //Toast.LENGTH_LONG).show();
            }
        });

    }

    // initializes textview in this class
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView searchlist_title;
        public TextView searchlist_year;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            searchlist_title = (TextView) itemLayoutView.findViewById(R.id.do_sche_search_title);
            searchlist_year = (TextView)itemView.findViewById(R.id. do_sche_search_year);
        }
    }

    //Returns the total number of items in the data set hold by the adapter.
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mData.clear();
        if (charText.length() == 0) {
            mData.addAll(arrayList);
        } else {
            for (Recent recent : arrayList) {
                String name = recent.getAddress();
                if (name.toLowerCase().contains(charText)) {
                    items.add(recent);
                }
            }
        }
        notifyDataSetChanged();
    }*/
  //  private ArrayList<SearchData> mData = new ArrayList<>();

/*    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView searchlist_year, searchlist_title;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            searchlist_year = itemView.findViewById(R.id. do_sche_search_year);
            searchlist_title = itemView.findViewById(R.id.do_sche_search_title);
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    SearchAdapter(ArrayList<SearchData> list) {
        mData = list;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.doshesearch_item, parent, false);
        SearchAdapter.ViewHolder vh = new SearchAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, int position) {
       SearchData item = mData.get(position);
        holder.searchlist_year.setText(item.getSearchlist_year());
        holder.searchlist_title.setText(item.getSearchlist_title());
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }*/
}
