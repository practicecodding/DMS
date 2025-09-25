package com.hamidul.dms.Service.Model;

import org.json.JSONObject;

public class ProductSummary {

    private String productName;
    private int quantity;
    private double netAmount;

    public ProductSummary() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }

    public static ProductSummary fromJson(JSONObject object) {
        ProductSummary productSummary = new ProductSummary();
        productSummary.productName = object.optString("product_name");
        productSummary.quantity = object.optInt("quantity");
        productSummary.netAmount = object.optInt("net_amount");

        return productSummary;
    }
}
