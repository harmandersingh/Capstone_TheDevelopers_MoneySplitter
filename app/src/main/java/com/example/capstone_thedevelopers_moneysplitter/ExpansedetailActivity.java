package com.example.capstone_thedevelopers_moneysplitter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ExpansedetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    ExpanseData expanseData = new ExpanseData();
    private double lat,lng;
    TextView txtName,txtAmount,txtDescription;
    ImageView imgBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expansedetail);
        txtName = findViewById(R.id.txtName);
        txtAmount = findViewById(R.id.txtAmount);
        txtDescription = findViewById(R.id.txtDescription);
        imgBill = findViewById(R.id.imgBill);
        expanseData = (ExpanseData) getIntent().getSerializableExtra("data");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        txtName.setText(expanseData.getUserName());
        txtAmount.setText(expanseData.getExpanse());
        txtDescription.setText(expanseData.getDescription());
        lat = Double.parseDouble(expanseData.getLat());
        lng = Double.parseDouble(expanseData.getLng());
        Log.d("", "");
        Glide
                .with(this)
                .load(expanseData.getImage())
                .centerCrop()
                .into(imgBill);


    }
    @Override
    public void onMapReady(GoogleMap googleMap) {


       mMap = googleMap;
        LatLng latLng = new LatLng(lat, lng);
        mMap.addMarker(new
                MarkerOptions().position(latLng).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        final CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)      // Sets the center of the map to Mountain View
                .zoom(13)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   //
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

   }

