package com.hamidul.dms.View.Ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.hamidul.dms.R;

public class FirstFragment extends Fragment {
    private LinearLayout layoutOnTransitOrder, layoutDailyOrderSummary, layoutReport;

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

        layoutDailyOrderSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragmentWithBackStack(new DailyOrderSummaryFragment());
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
        layoutDailyOrderSummary = myView.findViewById(R.id.layoutDailyOrderSummary);
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