package com.hamidul.dms.Service.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class OrderedProduct implements Parcelable {
    private int productId;
    private String productName;
    private double tp;
    private String quantity, discount;
    private double totalAmount, netAmount;

    public OrderedProduct() {
    }

    protected OrderedProduct(Parcel in) {
        productId = in.readInt();
        productName = in.readString();
        tp = in.readDouble();
        quantity = in.readString();
        discount = in.readString();
        totalAmount = in.readDouble();
        netAmount = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(productId);
        dest.writeString(productName);
        dest.writeDouble(tp);
        dest.writeString(quantity);
        dest.writeString(discount);
        dest.writeDouble(totalAmount);
        dest.writeDouble(netAmount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderedProduct> CREATOR = new Creator<OrderedProduct>() {
        @Override
        public OrderedProduct createFromParcel(Parcel in) {
            return new OrderedProduct(in);
        }

        @Override
        public OrderedProduct[] newArray(int size) {
            return new OrderedProduct[size];
        }
    };

    public static OrderedProduct fromJson(JSONObject object) {
        OrderedProduct product = new OrderedProduct();
        product.productId = object.optInt("product_id");
        product.productName = object.optString("product_name");
        product.quantity = object.optString("quantity");
        product.tp = object.optDouble("rate");
        product.discount = object.optString("discount");
        product.totalAmount = Double.parseDouble(product.getQuantity()) * product.getTp();
        product.netAmount = product.getTotalAmount() - Double.parseDouble(product.getDiscount());
        return product;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(double netAmount) {
        this.netAmount = netAmount;
    }
}
