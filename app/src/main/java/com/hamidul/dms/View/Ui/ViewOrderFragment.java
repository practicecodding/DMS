package com.hamidul.dms.View.Ui;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hamidul.dms.R;
import com.hamidul.dms.Service.Model.OrderedOutlet;
import com.hamidul.dms.Service.Model.User;
import com.hamidul.dms.Service.Repository.Resource;
import com.hamidul.dms.View.Adapter.OrderedOutletAdapter;
import com.hamidul.dms.View.Manager.ToastInstance;
import com.hamidul.dms.ViewModel.DMSViewModel;

import java.util.ArrayList;

public class ViewOrderFragment extends Fragment {
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private OrderedOutletAdapter adapter;
    private DMSViewModel viewModel;
    private User user;

    public ViewOrderFragment() {

    }

    public static ViewOrderFragment newInstance(User user) {
        ViewOrderFragment fragment = new ViewOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("user", user);
        fragment.setArguments(bundle);
        return fragment;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_view_order, container, false);
        findViewById(myView);

        adapter = new OrderedOutletAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(adapter);

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                View focusedView = getActivity().getCurrentFocus();
                if (focusedView instanceof EditText) {
                    focusedView.clearFocus();
                    hideKeyboard(v);
                }
                return false;
            }
        });

        viewModel.getOrderedOutlets(user).observe(getViewLifecycleOwner(), new Observer<Resource<ArrayList<OrderedOutlet>>>() {
            @Override
            public void onChanged(Resource<ArrayList<OrderedOutlet>> resource) {
                if (resource.status == Resource.Status.LOADING) {
                    progressBar.setVisibility(VISIBLE);
                    recyclerView.setVisibility(GONE);
                } else if (resource.status == Resource.Status.SUCCESS) {
                    progressBar.setVisibility(GONE);
                    recyclerView.setVisibility(VISIBLE);
                    /*adapter = new OrderedOutletAdapter(getContext(), resource.data);
                    recyclerView.setAdapter(adapter);*/
                    adapter.updateList(resource.data);
                } else {
                    progressBar.setVisibility(GONE);
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
        recyclerView = myView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (getArguments() != null) {
            this.user = getArguments().getParcelable("user");
        }
    }

    //************************************************************************************
    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //************************************************************************************
    @Override
    public void onResume() {
        super.onResume();
        //Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(user.getName());
        ((MainActivity) requireActivity()).setToolbarTitle(user.getName());
    }

}