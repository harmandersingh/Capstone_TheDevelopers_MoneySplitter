package com.example.capstone_thedevelopers_moneysplitter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class ExpanseDetailFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    ExpanseData expanseData = new ExpanseData();

    public ExpenseDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense_detail, container, false);
        // Inflate the layout for this fragment
        expanseData = (ExpanseData) getArguments().getSerializable("data");
        SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        long lat = Long.parseLong(expanseData.lat);
        long lng = Long.parseLong(expanseData.lng);

        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng TutorialsPoint = new LatLng(lat, lng);
        mMap.addMarker(new
                MarkerOptions().position(TutorialsPoint).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(TutorialsPoint));
    }
}
