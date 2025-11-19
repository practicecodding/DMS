package com.hamidul.dms.View.Ui;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.hamidul.dms.R;
import com.hamidul.dms.Service.Model.Report;
import com.hamidul.dms.Service.Model.Resource;
import com.hamidul.dms.View.Manager.ToastInstance;
import com.hamidul.dms.ViewModel.DMSViewModel;

public class ReportFragment extends Fragment {
    private ProgressBar progressBar;
    private DMSViewModel viewModel;
    private TextView tvTotalDelivery, tvTotalDamage, tvTotalCommission, tvTotalDue, tvTotalCash;

    public ReportFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_report, container, false);
        findViewById(myView);

        viewModel.getReport().observe(getViewLifecycleOwner(), new Observer<Resource<Report>>() {
            @Override
            public void onChanged(Resource<Report> resource) {
                if (resource.status == Resource.Status.LOADING) {
                    progressBar.setVisibility(VISIBLE);
                } else if (resource.status == Resource.Status.SUCCESS) {
                    Report report = resource.data;
                    if (report != null) {
                        progressBar.setVisibility(GONE);
                        tvTotalDelivery.setText(formatDouble(report.getTotalNetAmount()));
                        tvTotalDamage.setText(formatDouble(report.getTotalDamage()));
                        tvTotalCommission.setText(formatDouble(report.getTotalCommission()));
                        tvTotalDue.setText(formatDouble(report.getTotalDue()));
                        tvTotalCash.setText(formatDouble(report.getTotalCash()));
                    }
                } else {
                    progressBar.setVisibility(VISIBLE);
                    ToastInstance.getInstance(getContext()).setToast(resource.message);
                }
            }
        });

        return myView;
    }

    //************************************************************************************
    private void findViewById(View myView) {
        viewModel = new ViewModelProvider(this).get(DMSViewModel.class);
        progressBar = myView.findViewById(R.id.progressBar);
        tvTotalDelivery = myView.findViewById(R.id.tvTotalDelivery);
        tvTotalDamage = myView.findViewById(R.id.tvTotalDamage);
        tvTotalCommission = myView.findViewById(R.id.tvTotalCommission);
        tvTotalDue = myView.findViewById(R.id.tvTotalDue);
        tvTotalCash = myView.findViewById(R.id.tvTotalCash);
    }

    //************************************************************************************
    private String formatDouble(double value) {
        return (value % 1 == 0) ? String.format("%,.0f", value) : String.format("%,.2f", value);
    }

    //************************************************************************************
    @Override
    public void onResume() {
        super.onResume();
        //Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("Report");
        ((MainActivity) requireActivity()).setToolbarTitle("Today's Report");
    }
}