package com.hamidul.dms.View.Ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.hamidul.dms.R;

import java.util.Objects;

public class FirstFragment extends Fragment {
    LinearLayout layoutOnTransitOrder, layoutCheckDelivery, layoutReport;

    public FirstFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myVeiw = inflater.inflate(R.layout.fragment_first, container, false);
        findViewById(myVeiw);

        layoutOnTransitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragmentWithBackStack(new UserFragment());
            }
        });

        layoutCheckDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragmentWithBackStack(new CheckDeliveryFragment());
            }
        });

        layoutReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragmentWithBackStack(new ReportFragment());
            }
        });

        return myVeiw;
    }

    private void findViewById(View myView) {
        layoutOnTransitOrder = myView.findViewById(R.id.layoutOnTransitOrder);
        layoutCheckDelivery = myView.findViewById(R.id.layoutCheckDelivery);
        layoutReport = myView.findViewById(R.id.layoutReport);
    }

    private void loadFragment(Fragment fragment) {
        ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }

    private void loadFragmentWithBackStack(Fragment fragment) {
        ((AppCompatActivity) getContext()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        //Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("DMS");
        ((MainActivity) requireActivity()).setToolbarTitle("DMS");
    }
}