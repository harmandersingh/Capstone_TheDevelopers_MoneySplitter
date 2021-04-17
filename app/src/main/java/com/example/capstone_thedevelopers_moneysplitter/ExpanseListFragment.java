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
    RecyclerView rvExpanse;
    ArrayList<ExpanseData> expanseDataArrayList = new ArrayList<>();

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
        rvExpanse = view.findViewById(R.id.rvExpanse);
        setAdapter();
        return view;
    }

    void setAdapter() {
        rvExpanse.setLayoutManager(new LinearLayoutManager(requireContext()));
        ExpanseAdapter adapter = new ExpanseAdapter(requireContext(), expanseDataArrayList, index -> {

            Intent intent = new Intent(getActivity(), ExpansedetailActivity.class);
            intent.putExtra("data", expanseDataArrayList.get(index));
            startActivity(intent);
//            ExpenseDetailFragment expenseDetailFragment = new ExpenseDetailFragment();
//            expenseDetailFragment.setArguments(bundle);
//            ((MainActivity) getActivity()).openFragment(expenseDetailFragment, true);
        });
        rvExpanse.setAdapter(adapter);
    }

}
