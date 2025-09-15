package com.hamidul.dms.ViewModel;

import androidx.lifecycle.LiveData;

import com.hamidul.dms.Service.Model.OrderedOutlet;
import com.hamidul.dms.Service.Model.Report;
import com.hamidul.dms.Service.Model.User;
import com.hamidul.dms.Service.Repository.Resource;

import java.util.ArrayList;

public interface ViewModelImpl {
    LiveData<Resource<ArrayList<User>>> getUsers();

    LiveData<Resource<ArrayList<OrderedOutlet>>> getOrderedOutlets(User user);

    LiveData<Resource<Report>> getReport();
}
