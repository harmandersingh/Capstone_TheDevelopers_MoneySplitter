package com.example.capstone_thedevelopers_moneysplitter;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class ExpanseListFragment extends Fragment {
    TextView txtEndTrip, txtPay;
    RecyclerView rvExpanse;
    ArrayList<ExpanseData> expanseDataArrayList = new ArrayList<>();
    ArrayList<UserData> userDataArrayList = new ArrayList<>();
    private FirebaseDatabase mFirebaseInstance;
    SharedPreferences mPrefs;
    private UserData userData = new UserData();
    String totalSpend;

    public ExpanseListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expanse_list, container, false);
        expanseDataArrayList = (ArrayList<ExpanseData>) getArguments().getSerializable("dataList");
        userDataArrayList = (ArrayList<UserData>) getArguments().getSerializable("userData");
        totalSpend = getArguments().getString("totalSpend");
        rvExpanse = view.findViewById(R.id.rvExpanse);
        txtEndTrip = view.findViewById(R.id.txtEndTrip);
        txtPay = view.findViewById(R.id.txtPay);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        mPrefs = getActivity().getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("userData", "");
        userData = gson.fromJson(json, UserData.class);
        for (int i = 0; i < userDataArrayList.size(); i++) {
            int totalAmount = 0;
            for (int j = 0; j < expanseDataArrayList.size(); j++) {
                if (userDataArrayList.get(i).getUserId().equals(expanseDataArrayList.get(j).getUserId())) {
                    totalAmount = totalAmount + Integer.parseInt(expanseDataArrayList.get(j).getExpanse());
                }
            }
            userDataArrayList.get(i).setCurrentTripTotalExpanse(String.valueOf(totalAmount));
        }
        txtEndTrip.setOnClickListener(v -> {
            showDialog();
        });
        txtPay.setOnClickListener(v -> {

        });
        setAdapter();
        return view;
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_end_trip);
        TextView txtEndTrip = (TextView) dialog.findViewById(R.id.txtEndTrip);
        TextView perHead = (TextView) dialog.findViewById(R.id.perHead);
        int split = Integer.parseInt(totalSpend) / userDataArrayList.size();
        perHead.setText( "$ " +split );
        RecyclerView rvPayments = (RecyclerView) dialog.findViewById(R.id.rvPayments);
        setPaymentAdapter(rvPayments);
        txtEndTrip.setOnClickListener(v -> {
            clearTripId();
            dialog.dismiss();
        });
        dialog.show();
    }
    private void clearTripId() {
        for (int i = 0; i < userDataArrayList.size(); i++) {
            mFirebaseInstance.getReference("Users").child(userDataArrayList.get(i).getUserId()).child("currentTripId").setValue("");
        }

        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        userData.setCurrentTripId("");
        String json = gson.toJson(userData);
        prefsEditor.putString("userData", json);
        prefsEditor.putBoolean("isLogin", false);
        prefsEditor.apply();

        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();

    }
    private void setPaymentAdapter(RecyclerView rvPayment) {
        rvPayment.setLayoutManager(new LinearLayoutManager(requireContext()));
        PaymentsAdapter adapter = new PaymentsAdapter(requireContext(), userDataArrayList);
        rvPayment.setAdapter(adapter);
    }

    void setAdapter() {
        rvExpanse.setLayoutManager(new LinearLayoutManager(requireContext()));
        ExpanseAdapter adapter = new ExpanseAdapter(requireContext(), expanseDataArrayList, index -> {

            Intent intent = new Intent(getActivity(), ExpansedetailActivity.class);
            intent.putExtra("data", expanseDataArrayList.get(index));
            startActivity(intent);
        });
        rvExpanse.setAdapter(adapter);
    }
}

