package com.example.yg.sms_auto_registration;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class GroupUserListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static class myGroupUserListViewHolder extends RecyclerView.ViewHolder {
        TextView userName;

        myGroupUserListViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.groupUserListItemName);
        }
    }

    private ArrayList<GroupUserListItem> groupUserListItems;

    GroupUserListAdapter(ArrayList<GroupUserListItem> _groupUserListItems) {
        this.groupUserListItems = _groupUserListItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.groupuser_item, parent, false);

        return new myGroupUserListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        myGroupUserListViewHolder myGroupUserListViewHolder = (GroupUserListAdapter.myGroupUserListViewHolder)holder;

        myGroupUserListViewHolder.userName.setText(groupUserListItems.get(position).userName);
    }

    @Override
    public int getItemCount() {
        return groupUserListItems.size();
    }
}
