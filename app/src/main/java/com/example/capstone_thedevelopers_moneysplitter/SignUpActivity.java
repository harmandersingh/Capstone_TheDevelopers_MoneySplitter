package com.example.capstone_thedevelopers_moneysplitter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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

public class SignUpActivity extends AppCompatActivity {

    TextView txtRegister;
    ProgressBar progressBar;
    EditText edtEmail, edtPhoneNumber, edtPassword, edtName;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    UserData userData = new UserData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        txtRegister = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.progressBar);
        edtEmail = findViewById(R.id.edtEmail);
        edtName = findViewById(R.id.edtName);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtPassword = findViewById(R.id.edtPassword);
        userData = (UserData) getIntent().getSerializableExtra("data");
        mPrefs = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        setData();
        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("Users");


        txtRegister.setOnClickListener(v -> {
            if (edtName.getText().toString().isEmpty()) {
                edtName.requestFocus();
                edtName.setError("please enter name");
                return;
            } else if (edtEmail.getText().toString().isEmpty()) {
                edtEmail.requestFocus();
                edtEmail.setError("please enter email");
                return;
            } else if (edtPhoneNumber.getText().toString().isEmpty()) {
                edtPhoneNumber.requestFocus();
                edtPhoneNumber.setError("please enter number");
                return;
            } else if (edtPassword.getText().toString().isEmpty()) {
                edtPassword.requestFocus();
                edtPassword.setError("please enter password");
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            userData.setUserId(userData.getUserId());
            userData.setName(edtName.getText().toString());
            userData.setEmail(edtEmail.getText().toString());
            userData.setPhoneNumber(edtPhoneNumber.getText().toString());
            userData.setPassword(edtPassword.getText().toString());
            userData.setCurrentTripId("");
            userData.setCurrentTripTotalExpanse("0");
            checkUserExit("phoneNumber", userData.getPhoneNumber(), true);
//            if (checkUserExit("phoneNumber", userData.getPhoneNumber())) {
//                return;
//            } else if (checkUserExit("email", userData.getEmail())) {
//                return;
//            }
        });

    }

    private void checkUserExit(String key, String value, boolean isNumber) {
        Query query = mFirebaseDatabase.orderByChild(key).equalTo(value);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0

                    for (DataSnapshot user : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                        UserData usersBean = user.getValue(UserData.class);
                        Toast.makeText(SignUpActivity.this,
                                "User Already Exits", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                        break;
                    }
                } else {
                    if (isNumber) {
                        checkUserExit("email", userData.getEmail(), false);
                        return;
                    }

                    mFirebaseDatabase.child(userData.getUserId()).setValue(userData);
                    SharedPreferences.Editor prefsEditor = mPrefs.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(userData);
                    prefsEditor.putString("userData", json);
                    prefsEditor.apply();
                    mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            progressBar.setVisibility(View.GONE);
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value

                        }
                    });

//                });


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void setData() {

        edtName.setText(userData.getName());
        edtEmail.setText(userData.getEmail());
        edtPhoneNumber.setText(userData.getPhoneNumber());

    }
}}