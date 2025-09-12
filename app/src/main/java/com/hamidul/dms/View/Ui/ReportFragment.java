package com.hamidul.dms.View.Ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hamidul.dms.R;

import java.util.Objects;

public class ReportFragment extends Fragment {

    public ReportFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_report, container, false);





        return myView;
    }

    @Override
    public void onResume() {
        super.onResume();
        //Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Report");
        ((MainActivity) requireActivity()).setToolbarTitle("Report");
    }
}