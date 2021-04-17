package com.example.capstone_thedevelopers_moneysplitter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class PhoneNumberVerificationActivity extends AppCompatActivity {
    TextView btnVerify;
    ProgressBar progressBar;
    boolean isVerifyOtp = false;
    EditText edtPhone, edtOtp;
    // string for storing our verification ID
    private String verificationId;
    // [START declare_auth]
    private FirebaseAuth mAuth;

    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_verification);
        btnVerify = findViewById(R.id.btnVerify);
        edtPhone = findViewById(R.id.edtPhone);
        edtOtp = findViewById(R.id.edtOtp);
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                final String code = credential.getSmsCode();

                // checking if the code
                // is null or not.
                if (code != null) {
                    // if the code is not null then
                    // we are setting that code to
                    // our OTP edittext field.
                    edtOtp.setText(code);

                    // after setting this code
                    // to OTP edittext field we
                    // are calling our verifycode method.
                    verifyCode(code);
                }
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w("TAG", "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

                // Show a message and update the UI
            }

            @Override
            public void onCodeSent(@NonNull String vId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("TAG", "onCodeSent:" + vId);

                // Save verification ID and resending token so we can use them later
                verificationId = vId;
                mResendToken = token;
                progressBar.setVisibility(View.GONE);
            }
        };
        btnVerify.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            if (isVerifyOtp) {
                verifyCode(edtOtp.getText().toString());
            } else {
                isVerifyOtp = true;
                edtOtp.setVisibility(View.VISIBLE);
                edtPhone.setVisibility(View.GONE);
                btnVerify.setText("Verify");
                sendVerificationCode("+91" + edtPhone.getText().toString());
            }
        });
    }


    private void sendVerificationCode(String number) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    private void signInWithCredential(PhoneAuthCredential credential) {
        // inside this method we are checking if
        // the code entered is correct or not.
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // if the code is correct and the task is successful
                        // we are sending our user to new activity.
                        String id = task.getResult().getUser().getUid();
                        String number = task.getResult().getUser().getPhoneNumber();

                        UserData user = new UserData();
                        user.setUserId(id);
                        user.setPhoneNumber(edtPhone.getText().toString());
                        progressBar.setVisibility(View.GONE);
                        Intent i = new Intent(PhoneNumberVerificationActivity.this, SignUpActivity.class);
                        i.putExtra("data", user);
                        startActivity(i);
                        finish();
                    } else {
                        progressBar.setVisibility(View.GONE);
                        // if the code is not correct then we are
                        // displaying an error message to the user.
                        Toast.makeText(PhoneNumberVerificationActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }


    // below method is use to verify code from Firebase.
    private void verifyCode(String code) {
        progressBar.setVisibility(View.VISIBLE);
        // below line is used for getting getting
        // credentials from our verification id and code.
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        // after getting credential we are
        // calling sign in method.
        signInWithCredential(credential);
    }



    }