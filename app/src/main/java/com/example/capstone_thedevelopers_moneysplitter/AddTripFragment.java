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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_trip, container, false);
        recyclerView = view.findViewById(R.id.rvMembers);
        edtDestination = view.findViewById(R.id.edtDestination);
        edtBudget = view.findViewById(R.id.edtBudget);
        edtStartDate = view.findViewById(R.id.edtStartDate);
        edtEndDate = view.findViewById(R.id.edtEndDate);
        edtMembers = view.findViewById(R.id.edtMember);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnAddMember = view.findViewById(R.id.btnAddMember);
        progressBar = view.findViewById(R.id.progressBar);
        mPrefs = getActivity().getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        mPrefs = getActivity().getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        Gson gson = new Gson();
        String json = mPrefs.getString("userData", "");
        userData = gson.fromJson(json, UserData.class);


        edtEndDate.setOnClickListener(v -> openDatePicker(edtEndDate));
        edtStartDate.setOnClickListener(v -> openDatePicker(edtStartDate));

        btnAddMember.setOnClickListener(v -> {
            if (edtMembers.getText().toString().isEmpty()) {
                return;
            }
            if (userData.getEmail().equals(edtMembers.getText().toString())) {
                Toast.makeText(requireContext(), "you can't add your self", Toast.LENGTH_SHORT).show();
                return;
            }
            findUserAndAddToList(edtMembers.getText().toString());
        });
        btnSubmit.setOnClickListener(v -> {
            if (membersList.isEmpty()) {
                Toast.makeText(getContext(), "please Add members", Toast.LENGTH_LONG).show();
                return;
            }

            if (!userData.getCurrentTripId().isEmpty()) {
                Toast.makeText(getContext(), "You already on a trip", Toast.LENGTH_LONG).show();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);

            addTripData();
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void openDatePicker(EditText editText) {

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                (view, year, monthOfYear, dayOfMonth) -> editText.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year), mYear, mMonth, mDay);
        datePickerDialog.show();


    }

    private void addTripData() {
        mFirebaseDatabase = mFirebaseInstance.getReference("Trips");
        String tripId = mFirebaseDatabase.push().getKey();

        ArrayList<ExpanseData> expanseData = new ArrayList<>();
        membersList.add(userData);
        TripData tripData = new TripData();
        tripData.setTripId(tripId);
        tripData.setBudget(edtBudget.getText().toString());
        tripData.setDestination(edtDestination.getText().toString());
        tripData.setEndDate(edtEndDate.getText().toString());
        tripData.setStartDate(edtStartDate.getText().toString());
        tripData.setMembers(membersList);
        tripData.setExpanseData(expanseData);
        for (int i = 0; i < membersList.size(); i++) {
            mFirebaseInstance.getReference("Users").child(membersList.get(i).getUserId()).child("currentTripId").setValue(tripId);
        }
        mFirebaseDatabase.child(tripId).setValue(tripData);


        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                progressBar.setVisibility(View.GONE);
                resetView();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

    }




}