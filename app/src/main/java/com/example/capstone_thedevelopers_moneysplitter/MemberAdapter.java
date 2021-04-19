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
