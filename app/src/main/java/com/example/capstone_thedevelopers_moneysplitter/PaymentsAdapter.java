package com.example.capstone_thedevelopers_moneysplitter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PaymentsAdapter extends RecyclerView.Adapter<PaymentsAdapter.ViewHolder> {

    private ArrayList<UserData> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    PaymentsAdapter(Context context, ArrayList<UserData> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentsAdapter.ViewHolder holder, int position) {

        holder.myTextView.setText(mData.get(position).getName() + " Paid $" + mData.get(position).getCurrentTripTotalExpanse());
    }


    @NonNull
    @Override
    public PaymentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_members, parent, false);
        return new PaymentsAdapter.ViewHolder(view);
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


}
