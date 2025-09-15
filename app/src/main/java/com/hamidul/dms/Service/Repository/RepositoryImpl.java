package com.hamidul.dms.Service.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hamidul.dms.Service.Model.OrderedOutlet;
import com.hamidul.dms.Service.Model.Report;
import com.hamidul.dms.Service.Model.User;

import java.util.ArrayList;

import javax.xml.transform.Result;

public interface RepositoryImpl {
    LiveData<Resource<ArrayList<User>>> getUsers();
    LiveData<Resource<ArrayList<OrderedOutlet>>> getOrderedOutlets(User user);
    LiveData<Resource<Report>> getReport();
}
