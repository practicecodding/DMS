package com.hamidul.dms.View.Ui;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hamidul.dms.R;
import com.hamidul.dms.Service.Model.DeliveredOutlet;
import com.hamidul.dms.Service.Model.User;
import com.hamidul.dms.Service.Repository.Resource;
import com.hamidul.dms.View.Adapter.DeliveredOutletAdapter;
import com.hamidul.dms.View.Manager.ToastInstance;
import com.hamidul.dms.ViewModel.DMSViewModel;

import java.util.ArrayList;

public class OutletWiseReportFragment extends Fragment {
    private TextView tvNoDataToFound;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private DeliveredOutletAdapter adapter;
    private DMSViewModel viewModel;
    private User user;

    public OutletWiseReportFragment() {
    }

    public static OutletWiseReportFragment newInstance(User user) {
        OutletWiseReportFragment fragment = new OutletWiseReportFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("user", user);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_outlet_wise_report, container, false);
        findViewById(myView);

        adapter = new DeliveredOutletAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(adapter);

        viewModel.getDeliveredOutlets(user).observe(getViewLifecycleOwner(), new Observer<Resource<ArrayList<DeliveredOutlet>>>() {
            @Override
            public void onChanged(Resource<ArrayList<DeliveredOutlet>> resource) {
                if (resource.status == Resource.Status.LOADING) {
                    tvNoDataToFound.setVisibility(GONE);
                    progressBar.setVisibility(VISIBLE);
                    recyclerView.setVisibility(GONE);
                } else if (resource.status == Resource.Status.EMPTY) {
                    tvNoDataToFound.setVisibility(VISIBLE);
                    progressBar.setVisibility(GONE);
                    recyclerView.setVisibility(GONE);
                } else if (resource.status == Resource.Status.SUCCESS) {
                    tvNoDataToFound.setVisibility(GONE);
                    progressBar.setVisibility(GONE);
                    recyclerView.setVisibility(VISIBLE);
                    /*adapter = new OrderedOutletAdapter(getContext(), resource.data);
                    recyclerView.setAdapter(adapter);*/
                    adapter.updateList(resource.data);
                } else {
                    tvNoDataToFound.setVisibility(GONE);
                    progressBar.setVisibility(VISIBLE);
                    recyclerView.setVisibility(GONE);
                    ToastInstance.getInstance(getContext()).setToast(resource.message);
                }
            }
        });

        return myView;
    }

    //************************************************************************************
    private void findViewById(View myView) {
        viewModel = new ViewModelProvider(this).get(DMSViewModel.class);
        tvNoDataToFound = myView.findViewById(R.id.tvNoDataToFound);
        progressBar = myView.findViewById(R.id.progressBar);
        recyclerView = myView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (getArguments() != null) {
            this.user = getArguments().getParcelable("user");
        }
    }

    //************************************************************************************
    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) requireActivity()).setToolbarTitle(user.getName());
    }
}