package com.hamidul.dms.Service.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DateWiseSummary {
    String date;
    ArrayList<ProductSummary> productSummaries;
    boolean isShowing;
    boolean isShowingDetails;

    public DateWiseSummary() {
        this.productSummaries = new ArrayList<>();
        this.isShowing = false;
        this.isShowingDetails = false;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<ProductSummary> getProductSummaries() {
        return productSummaries;
    }

    public void setProductSummaries(ArrayList<ProductSummary> productSummaries) {
        this.productSummaries = productSummaries;
    }

    public boolean isShowing() {
        return isShowing;
    }

    public void setShowing(boolean showing) {
        isShowing = showing;
    }

    public boolean isShowingDetails() {
        return isShowingDetails;
    }

    public void setShowingDetails(boolean showingDetails) {
        isShowingDetails = showingDetails;
    }

    public static DateWiseSummary fromJson(JSONObject object) {
        DateWiseSummary dateWiseSummary = new DateWiseSummary();
        dateWiseSummary.date = object.optString("date");

        JSONArray jsonSummaries = object.optJSONArray("summaries");
        if (jsonSummaries != null) {
            for (int x = 0; x < jsonSummaries.length(); x++) {
                JSONObject summaryObject = jsonSummaries.optJSONObject(x);
                if (summaryObject != null) {
                    dateWiseSummary.productSummaries.add(ProductSummary.fromJson(summaryObject));
                }
            }
        }

        return dateWiseSummary;
    }
}
