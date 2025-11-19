package com.hamidul.dms.View.Ui;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hamidul.dms.R;
import com.hamidul.dms.Service.Model.OrderedOutlet;
import com.hamidul.dms.Service.Model.User;
import com.hamidul.dms.Service.Model.Resource;
import com.hamidul.dms.View.Adapter.OrderedOutletAdapter;
import com.hamidul.dms.View.Manager.KeyboardVisibilityHelper;
import com.hamidul.dms.View.Manager.ToastInstance;
import com.hamidul.dms.ViewModel.DMSViewModel;

import java.util.ArrayList;

public class ViewOrderFragment extends Fragment {
    private View mainView;
    private TextView tvNoDataToFound;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private OrderedOutletAdapter adapter;
    private DMSViewModel viewModel;
    private User user;
    private KeyboardVisibilityHelper keyboardHelper;

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

        // --- when touch recyclerview item then hide keyboard ---
        /*recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                View focusedView = getActivity().getCurrentFocus();
                if (focusedView instanceof EditText) {
                    focusedView.clearFocus();
                    hideKeyboard(v);
                }
                return false;
            }
        });*/

        /*mainView.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect r = new Rect();
            mainView.getWindowVisibleDisplayFrame(r);
            int screenHeight = mainView.getRootView().getHeight();

            int keypadHeight = screenHeight - r.bottom;

            if (keypadHeight > screenHeight * 0.15) {
                // Keyboard is open
                mainView.setPadding(0, 0, 0, keypadHeight);
            } else {
                // Keyboard is closed
                mainView.setPadding(0, 0, 0, 0);
            }
        });*/

        keyboardHelper = new KeyboardVisibilityHelper(mainView, recyclerView, (visible, keyboardHeight) -> {
            // optional: adjust bottom padding if needed
            if (visible) {
                mainView.setPadding(0, 0, 0, keyboardHeight);
            } else {
                mainView.setPadding(0, 0, 0, 0);
            }
        });

        // --- when scroll recyclerview then hide keyboard ---
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                    View focusView = getActivity().getCurrentFocus();
                    if (focusView instanceof EditText) {
                        focusView.clearFocus();
                        hideKeyboard(focusView);
                    }
                }
            }
        });

        viewModel.getOrderedOutlets(user).observe(getViewLifecycleOwner(), new Observer<Resource<ArrayList<OrderedOutlet>>>() {
            @Override
            public void onChanged(Resource<ArrayList<OrderedOutlet>> resource) {
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
        mainView = myView.findViewById(R.id.productView);
        progressBar = myView.findViewById(R.id.progressBar);
        recyclerView = myView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (getArguments() != null) {
            this.user = getArguments().getParcelable("user");
        }
    }

    //************************************************************************************
    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //************************************************************************************
    @Override
    public void onResume() {
        super.onResume();
        //Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle(user.getName());
        ((MainActivity) requireActivity()).setToolbarTitle(user.getName());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (keyboardHelper != null) {
            keyboardHelper.detach();
        }
    }
}