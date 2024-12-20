package com.example.team3_expenseapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.team3_expenseapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutFragment #newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutFragment extends Fragment {

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getActivity() != null && isAdded()) {
            View fab = getActivity().findViewById(R.id.fab);
            if (fab != null) {
                // Make the FloatingActionButton invisible
                fab.setVisibility(View.INVISIBLE);
            }
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }
}