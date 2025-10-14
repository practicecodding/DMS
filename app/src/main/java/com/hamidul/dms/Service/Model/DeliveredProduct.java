package com.hamidul.dms.Service.Model;

import org.json.JSONObject;

public class DeliveredProduct {
    private int productId;
    private String productName;
    private double tp;
    private String orderDiscount, deliveryDiscount;
    private int orderQuantity, deliveryQuantity;
    private double netOrderAmount;

    private DeliveredProduct() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getTp() {
        return tp;
    }

    public void setTp(double tp) {
        this.tp = tp;
    }

    public String getOrderDiscount() {
        return orderDiscount;
    }

    public void setOrderDiscount(String orderDiscount) {
        this.orderDiscount = orderDiscount;
    }

    public String getDeliveryDiscount() {
        return deliveryDiscount;
    }

    public void setDeliveryDiscount(String deliveryDiscount) {
        this.deliveryDiscount = deliveryDiscount;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public int getDeliveryQuantity() {
        return deliveryQuantity;
    }

    public void setDeliveryQuantity(int deliveryQuantity) {
        this.deliveryQuantity = deliveryQuantity;
    }

    public double getNetOrderAmount() {
        return netOrderAmount;
    }

    public void setNetOrderAmount(double netOrderAmount) {
        this.netOrderAmount = netOrderAmount;
    }

    public static DeliveredProduct fromJson(JSONObject object) {
        DeliveredProduct product = new DeliveredProduct();
        product.productId = object.optInt("product_id");
        product.productName = object.optString("product_name");
        product.tp = object.optDouble("rate");
        product.orderDiscount = object.optString("order_discount");
        product.deliveryDiscount = object.optString("delivery_discount");
        product.orderQuantity = object.optInt("order_quantity");
        product.deliveryQuantity = object.optInt("delivery_quantity");
        product.netOrderAmount = (product.orderQuantity * product.getTp()) - product.safeParseDouble(product.getOrderDiscount());
        return product;
    }

    private double safeParseDouble(String value) {
        if (value == null || value.trim().isEmpty()) return 0;
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}
