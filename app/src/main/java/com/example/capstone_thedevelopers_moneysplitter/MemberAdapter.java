package com.example.capstone_thedevelopers_moneysplitter;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.ViewHolder> {

    private ArrayList<UserData> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    MembersAdapter(Context context, ArrayList<UserData> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }
//    // inflates the row layout from xml when needed
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
//        return new ViewHolder(view);
//    }
@Override
public void onBindViewHolder(@NonNull MembersAdapter.ViewHolder holder, int position) {

    holder.myTextView.setText(mData.get(position).getName());
}

// binds the data to the TextView in each row
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        String animal = mData.get(position);
//        holder.myTextView.setText(animal);
//    }
@NonNull
@Override
public MembersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = mInflater.inflate(R.layout.item_members, parent, false);
    return new ViewHolder(view);
}
    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.txtMemberName);
//            itemView.setOnClickListener(this);
        }
    }


