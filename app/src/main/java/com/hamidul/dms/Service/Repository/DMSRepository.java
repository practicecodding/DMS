package com.hamidul.dms.Service.Repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.hamidul.dms.Service.Model.DateWiseSummary;
import com.hamidul.dms.Service.Model.OrderedOutlet;
import com.hamidul.dms.Service.Model.Report;
import com.hamidul.dms.Service.Model.User;
import com.hamidul.dms.Service.Network.ApiServices;
import com.hamidul.dms.Service.Network.VolleyInstance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DMSRepository implements RepositoryImpl {
    private static DMSRepository repository;
    private static Context ctx;

    public static DMSRepository getRepository(Context context) {
        if (repository == null) {
            ctx = context.getApplicationContext();
            repository = new DMSRepository();
        }
        return repository;
    }

    @Override
    public LiveData<Resource<ArrayList<User>>> getUsers() {
        MutableLiveData<Resource<ArrayList<User>>> liveData = new MutableLiveData<>();

        liveData.setValue(Resource.loading());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, ApiServices.getUser, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<User> users = new ArrayList<>();
                boolean hasError = false;

                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        users.add(User.fromJson(object));
                    } catch (JSONException e) {
                        hasError = true;
                    }
                }

                if (users.isEmpty()){
                    liveData.setValue(Resource.empty());
                } else if (hasError) {
                    liveData.setValue(Resource.error("Some items failed to parse"));
                } else {
                    liveData.setValue(Resource.success(users));
                }

                /*try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        users.add(User.fromJson(object));
                    }
                    liveData.setValue(Resource.success(users));

                } catch (JSONException e) {
                    liveData.setValue(Resource.error("Parse error", null));
                }*/

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                liveData.setValue(Resource.error("Network error"));
            }
        });

        VolleyInstance.getVolleyInstance(ctx).addToRequestQueue(jsonArrayRequest);

        return liveData;
    }

    @Override
    public LiveData<Resource<ArrayList<OrderedOutlet>>> getOrderedOutlets(User user) {
        MutableLiveData<Resource<ArrayList<OrderedOutlet>>> liveData = new MutableLiveData<>();

        liveData.setValue(Resource.loading());
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", user.getId());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        jsonArray.put(jsonObject);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, ApiServices.getOrderedOutlet, jsonArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<OrderedOutlet> outlets = new ArrayList<>();
                boolean hasError = false;

                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        outlets.add(OrderedOutlet.fromJson(object));
                    } catch (JSONException e) {
                        hasError = true;
                    }
                }

                if (outlets.isEmpty()){
                    liveData.setValue(Resource.empty());
                } else if (hasError) {
                    liveData.setValue(Resource.error("Some items failed to parse"));
                } else {
                    liveData.setValue(Resource.success(outlets));
                }

                /*try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        outlets.add(OrderedOutlet.fromJson(object));
                    }
                    liveData.setValue(Resource.success(outlets));

                } catch (JSONException e) {
                    liveData.setValue(Resource.error("Parse error", null));
                }*/

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                liveData.setValue(Resource.error("Network error"));
            }
        });

        VolleyInstance.getVolleyInstance(ctx).addToRequestQueue(jsonArrayRequest);

        return liveData;
    }

    @Override
    public LiveData<Resource<Report>> getReport() {
        MutableLiveData<Resource<Report>> liveData = new MutableLiveData<>();

        liveData.setValue(Resource.loading());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiServices.getReport, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                try {
                    JSONObject object = new JSONObject(s);
                    if (object.length() == 0){
                        liveData.setValue(Resource.empty());
                    } else {
                        liveData.setValue(Resource.success(Report.fromJson(object)));
                    }
                } catch (JSONException e) {
                    liveData.setValue(Resource.error("Parse error"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                liveData.setValue(Resource.error("Network error"));
            }
        });

        VolleyInstance.getVolleyInstance(ctx).addToRequestQueue(stringRequest);

        return liveData;
    }

    @Override
    public LiveData<Resource<ArrayList<DateWiseSummary>>> getDailyOrderSummary() {
        MutableLiveData<Resource<ArrayList<DateWiseSummary>>> liveData = new MutableLiveData<>();

        liveData.setValue(Resource.loading());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, ApiServices.getDailyOrderSummary, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<DateWiseSummary> summaries = new ArrayList<>();
                boolean hasError = false;

                for (int i = 0; i < response.length(); i++){
                    try {
                        JSONObject object = response.getJSONObject(i);
                        summaries.add(DateWiseSummary.fromJson(object));
                    } catch (JSONException e) {
                        hasError = true;
                    }
                }

                if (summaries.isEmpty()){
                    liveData.setValue(Resource.empty());
                } else if (hasError) {
                    liveData.setValue(Resource.error("Some items failed to parse"));
                } else {
                    liveData.setValue(Resource.success(summaries));
                }
                /*try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        summaries.add(DateWiseSummary.fromJson(object));
                    }
                    liveData.setValue(Resource.success(summaries));

                } catch (JSONException e) {
                    liveData.setValue(Resource.error("Parse error"));
                }*/

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                liveData.setValue(Resource.error("Network error"));
            }
        });

        VolleyInstance.getVolleyInstance(ctx).addToRequestQueue(jsonArrayRequest);

        return liveData;
    }

}
