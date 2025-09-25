package com.hamidul.dms.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hamidul.dms.Service.Model.DateWiseSummary;
import com.hamidul.dms.Service.Model.OrderedOutlet;
import com.hamidul.dms.Service.Model.Report;
import com.hamidul.dms.Service.Model.User;
import com.hamidul.dms.Service.Repository.DMSRepository;
import com.hamidul.dms.Service.Repository.Resource;

import java.util.ArrayList;

public class DMSViewModel extends AndroidViewModel implements ViewModelImpl {
    private final DMSRepository repository;

    public DMSViewModel(@NonNull Application application) {
        super(application);
        repository = DMSRepository.getRepository(application);
    }


    @Override
    public LiveData<Resource<ArrayList<User>>> getUsers() {
        return repository.getUsers();
    }

    @Override
    public LiveData<Resource<ArrayList<OrderedOutlet>>> getOrderedOutlets(User user) {
        return repository.getOrderedOutlets(user);
    }

    @Override
    public LiveData<Resource<Report>> getReport() {
        return repository.getReport();
    }

    @Override
    public LiveData<Resource<ArrayList<DateWiseSummary>>> getDailyOrderSummary() {
        return repository.getDailyOrderSummary();
    }
}
