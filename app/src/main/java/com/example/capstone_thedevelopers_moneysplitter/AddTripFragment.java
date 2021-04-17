package com.example.canadaproject;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class AddTripFragment extends Fragment {

    EditText edtDestination, edtBudget, edtStartDate, edtEndDate, edtMembers;
    TextView btnSubmit, btnAddMember;
    ArrayList<UserData> membersList = new ArrayList<>();
    RecyclerView recyclerView;
    ProgressBar progressBar;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    SharedPreferences mPrefs;
    private UserData userData = new UserData();

    public AddTripFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    void setAdapter() {

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        MembersAdapter adapter = new MembersAdapter(requireContext(), membersList);
//        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }




}