package com.example.capstone_thedevelopers_moneysplitter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import androidx.fragment.app.Fragment;

public class TripOptionFragment extends Fragment {

    TextView btnAddTrip, btnViewTrip, txtWelcome;
    SharedPreferences mPrefs;
    private UserData userData = new UserData();

    public TripOptionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_option, container, false);
        // Inflate the layout for this fragment
        btnAddTrip = view.findViewById(R.id.btnAddTrip);
        btnViewTrip = view.findViewById(R.id.btnViewTrip);
        txtWelcome = view.findViewById(R.id.txtWelcome);
        mPrefs = getActivity().getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("userData", "");
        userData = gson.fromJson(json, UserData.class);
        txtWelcome.setText("Welcome " + userData.getName());

        btnAddTrip.setOnClickListener(v -> ((MainActivity) getActivity()).openFragment(new AddTripFragment(), true));
        btnViewTrip.setOnClickListener(v -> ((MainActivity) getActivity()).openFragment(new ViewTripFragment(), true));

        return view;
    }
}



