package com.hamidul.dms.View.Ui;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hamidul.dms.R;
import com.hamidul.dms.Service.Model.User;
import com.hamidul.dms.Service.Repository.Resource;
import com.hamidul.dms.View.Adapter.UserAdapter;
import com.hamidul.dms.View.Manager.ToastInstance;
import com.hamidul.dms.ViewModel.DMSViewModel;

import java.util.ArrayList;

public class UserFragment extends Fragment {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private DMSViewModel viewModel;

    public UserFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_user, container, false);
        findViewById(myView);

        adapter = new UserAdapter(getContext(), new UserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(User user) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.frameLayout, ViewOrderFragment.newInstance(user))
                        .addToBackStack(null)
                        .commit();
            }
        });
        recyclerView.setAdapter(adapter);

        viewModel.getUsers().observe(getViewLifecycleOwner(), new Observer<Resource<ArrayList<User>>>() {
            @Override
            public void onChanged(Resource<ArrayList<User>> resource) {
                if (resource.status == Resource.Status.LOADING) {
                    progressBar.setVisibility(VISIBLE);
                    recyclerView.setVisibility(GONE);
                } else if (resource.status == Resource.Status.SUCCESS) {
                    progressBar.setVisibility(GONE);
                    recyclerView.setVisibility(VISIBLE);
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
    }

    //************************************************************************************
    @Override
    public void onResume() {
        super.onResume();
        //Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setTitle("On Transit Order");
        ((MainActivity) requireActivity()).setToolbarTitle("On Transit Order");
    }
}