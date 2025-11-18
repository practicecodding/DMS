package com.hamidul.dms.Service.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DeliveredOutlet {
    private String outletName;
    private String outletBanglaName;
    private String outletAddress;
    private String date;
    private ArrayList<DeliveredProduct> products;
    private double netDelivery;
    private double damage;
    private double commission;
    private double cash;
    private double due;

    private DeliveredOutlet() {
        this.products = new ArrayList<>();
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getOutletBanglaName() {
        return outletBanglaName;
    }

    public void setOutletBanglaName(String outletBanglaName) {
        this.outletBanglaName = outletBanglaName;
    }

    public String getOutletAddress() {
        return outletAddress;
    }

    public void setOutletAddress(String outletAddress) {
        this.outletAddress = outletAddress;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<DeliveredProduct> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<DeliveredProduct> products) {
        this.products = products;
    }

    public double getNetDelivery() {
        return netDelivery;
    }

    public void setNetDelivery(double netDelivery) {
        this.netDelivery = netDelivery;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getDue() {
        return due;
    }

    public void setDue(double due) {
        this.due = due;
    }

    public static DeliveredOutlet fromJson(JSONObject object) {
        DeliveredOutlet outlet = new DeliveredOutlet();
        outlet.outletName = object.optString("outlet_name");
        outlet.outletBanglaName = object.optString("outlet_bangla_name");
        outlet.outletAddress = object.optString("outlet_address");
        outlet.date = object.optString("date");
        JSONArray arrayOrders = object.optJSONArray("orders");
        if (arrayOrders != null) {
            for (int x = 0; x < arrayOrders.length(); x++) {
                JSONObject productObject = arrayOrders.optJSONObject(x);
                if (productObject != null) {
                    outlet.products.add(DeliveredProduct.fromJson(productObject));
                }
            }
        }
        outlet.netDelivery = object.optDouble("net_delivery");
        outlet.damage = object.optDouble("damage");
        outlet.commission = object.optDouble("commission");
        outlet.cash = object.optDouble("cash");
        outlet.due = object.optDouble("due");

        return outlet;
    }

}
