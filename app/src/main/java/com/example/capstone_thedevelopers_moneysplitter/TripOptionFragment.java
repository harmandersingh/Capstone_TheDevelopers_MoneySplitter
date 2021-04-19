package com.example.capstone_thedevelopers_moneysplitter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
public class TripOptionFragment extends Fragment {

    TextView btnAddTrip, btnViewTrip;

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

        btnAddTrip.setOnClickListener(v -> ((MainActivity) getActivity()).openFragment(new AddTripFragment(), true));
        btnViewTrip.setOnClickListener(v -> ((MainActivity) getActivity()).openFragment(new ViewTripFragment(), true));

        return view;
    }
}



