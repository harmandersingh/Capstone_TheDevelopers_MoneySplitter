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
        ((MainActivity) getActivity()).getCurrentLocation();

        btnViewLocation.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("dataList", tripData.expanseData);
            ExpanseListFragment expanseListFragment = new ExpanseListFragment();
            expanseListFragment.setArguments(bundle);
            ((MainActivity) getActivity()).openFragment(expanseListFragment, true);
        });

        mFirebaseInstance = FirebaseDatabase.getInstance();
        Gson gson = new Gson();
        String json = mPrefs.getString("userData", "");
        userData = gson.fromJson(json, UserData.class);

        if (userData.getCurrentTripId() == null) {
            Toast.makeText(getActivity(), "Trip not found", Toast.LENGTH_LONG).show();
            requireActivity().onBackPressed();
            return view;
        }

        getTripDataFromFirebase(userData.getCurrentTripId());

        return view;
    }

    private void openAddExpanseDialog() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_expanse);

        EditText edtExpanse = (EditText) dialog.findViewById(R.id.edtExpanse);
        EditText edtDescription = (EditText) dialog.findViewById(R.id.edtDescription);
        imgBill = (ImageView) dialog.findViewById(R.id.imgBill);
        LinearLayout lytAddImage = (LinearLayout) dialog.findViewById(R.id.lytAddImage);
        TextView dialogButton = (TextView) dialog.findViewById(R.id.txtAdd);

        lytAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtDescription.getText().toString().isEmpty()) {
                    edtDescription.requestFocus();
                    edtDescription.setError("please enter description");
                    return;
                } else if (edtExpanse.getText().toString().isEmpty()) {
                    edtExpanse.requestFocus();
                    edtExpanse.setError("please enter expanse");

                    return;
                }

                ExpanseData expanseData = new ExpanseData();
                expanseData.setUserId(userData.getUserId());
                expanseData.setUserName(userData.getName());
                expanseData.setExpanse(edtExpanse.getText().toString());
                expanseData.setImage("");
                expanseData.setDescription(edtDescription.getText().toString());
                expanseData.setLat(((MainActivity) getActivity()).latitude);
                expanseData.setLng(((MainActivity) getActivity()).longitude);
                expanseData.setAddress("");
//                uploadBillImageToFirebase(imageUrl, expanseData);
                updateExpanse(expanseData);
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void openCamera() {
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        } else {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireActivity(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(requireActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            imageView.setImageBitmap(photo);
            Uri uri = getImageUri(requireContext(), photo);
            imgBill.setImageURI(uri);
            imageUrl = uri;

        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void getTripDataFromFirebase(String value) {
        mFirebaseDatabase = mFirebaseInstance.getReference("Trips");
        Query query = mFirebaseDatabase.orderByChild("tripId").equalTo(value);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0

                    for (DataSnapshot user : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                        tripData = user.getValue(TripData.class);
                        txtTotal.setText(tripData.getTotalSpend());
                        txtDateReturn.setText(tripData.getEndDate());
                        txtDateDeparture.setText(tripData.getStartDate());
                        txtDestination.setText(tripData.getDestination());


                    }
                } else {
                    Toast.makeText(getActivity(), "Trip not found", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void updateExpanse(ExpanseData expanseData) {
        int totalAmount;
        if (tripData.getTotalSpend() == null) {
            totalAmount = 0;
        } else {
            totalAmount = Integer.parseInt(tripData.getTotalSpend());
        }
        totalAmount = totalAmount + Integer.parseInt(expanseData.getExpanse());
        updateUserData(totalAmount);
        tripData.setTotalSpend(String.valueOf(totalAmount));
        txtTotal.setText(String.valueOf(totalAmount));
        tripData.expanseData.add(expanseData);
        mFirebaseDatabase = mFirebaseInstance.getReference("Trips");
        mFirebaseDatabase.child(tripData.getTripId()).child("totalSpend").setValue(String.valueOf(totalAmount));
        mFirebaseDatabase.child(tripData.getTripId()).child("expanseData").setValue(tripData.expanseData);
    }

    private void updateUserData(int totalAmount) {
        userData.setCurrentTripTotalExpanse(String.valueOf(totalAmount));
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userData);
        prefsEditor.putString("userData", json);
        prefsEditor.apply();

    }
    void uploadBillImageToFirebase(Uri file, ExpanseData expanseData) {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseStorage storage = FirebaseStorage.getInstance();

        // [START upload_create_reference]
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();
//        StorageReference storageRef = storage.getReference();

// Create a reference to "mountains.jpg"
        final StorageReference ref = storageRef.child("images/mountains.jpg");
        UploadTask uploadTask = ref.putFile(file);

        Task<Uri> urlTask = uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                progressBar.setVisibility(View.GONE);
                throw task.getException();
            }
            progressBar.setVisibility(View.GONE);
            // Continue with the task to get the download URL
            return ref.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Uri downloadUri = task.getResult();
                Log.d("", "");
                expanseData.setImage(String.valueOf(downloadUri));
                updateExpanse(expanseData);
                Toast.makeText(getActivity(), "Image Uploaded", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);

            } else {
                progressBar.setVisibility(View.GONE);
                // Handle failures
            }
        });
    }



    }