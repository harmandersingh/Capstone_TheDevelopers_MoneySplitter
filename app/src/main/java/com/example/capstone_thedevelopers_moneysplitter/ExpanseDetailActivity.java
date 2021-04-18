package com.example.capstone_thedevelopers_moneysplitter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ExpanseDetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    ExpanseData expanseData = new ExpanseData();
    private long lat,lng;
    TextView txtName,txtAmount,txtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expansedetail);
        txtName = findViewById(R.id.txtName);
        txtAmount = findViewById(R.id.txtAmount);
        txtDescription = findViewById(R.id.txtDescription);
        expanseData = (ExpanseData) getIntent().getSerializableExtra("data");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        txtName.setText(expanseData.getUserName());
        txtAmount.setText(expanseData.getExpanse());
        txtDescription.setText(expanseData.getDescription());
//       lat = Long.parseLong(expanseData.getLat());
//        lng = Long.parseLong(expanseData.getLng());

    }

   }

