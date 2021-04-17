package com.example.capstone_thedevelopers_moneysplitter;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;

import static android.content.Context.MODE_PRIVATE;

public class ViewTripFragment extends Fragment {
    Uri imageUrl;
    private static final int CAMERA_REQUEST = 1888;

    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    TextView btnAdd, btnViewLocation, txtDateReturn,
            txtDateDeparture, txtDestination, txtTotal;

    ProgressBar progressBar;
    EditText edtAmount;
    SharedPreferences mPrefs;
    private UserData userData = new UserData();

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private TripData tripData = new TripData();
    private ImageView imgBill;

    public ViewTripFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_trip, container, false);
        // Inflate the layout for this fragment
        edtAmount = view.findViewById(R.id.edtAmount);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnViewLocation = view.findViewById(R.id.btnViewLocation);
        txtDateReturn = view.findViewById(R.id.txtDateReturn);
        txtDateDeparture = view.findViewById(R.id.txtDateDeparture);
        txtDestination = view.findViewById(R.id.txtDestination);
        txtTotal = view.findViewById(R.id.txtTotal);
        progressBar = view.findViewById(R.id.progressBar);
        mPrefs = getActivity().getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        btnAdd.setOnClickListener(v -> {
            openAddExpanseDialog();
            /*updateExpanse()*/
        });


    }