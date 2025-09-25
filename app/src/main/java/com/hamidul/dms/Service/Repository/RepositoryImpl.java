package com.hamidul.dms.Service.Repository;

import androidx.lifecycle.LiveData;

import com.hamidul.dms.Service.Model.DateWiseSummary;
import com.hamidul.dms.Service.Model.OrderedOutlet;
import com.hamidul.dms.Service.Model.Report;
import com.hamidul.dms.Service.Model.User;

import java.util.ArrayList;

public interface RepositoryImpl {
    LiveData<Resource<ArrayList<User>>> getUsers();

    LiveData<Resource<ArrayList<OrderedOutlet>>> getOrderedOutlets(User user);

    LiveData<Resource<Report>> getReport();

    LiveData<Resource<ArrayList<DateWiseSummary>>> getDailyOrderSummary();
}
