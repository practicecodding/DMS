package com.hamidul.dms.Service.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderedOutlet implements Parcelable {
    private int dbId;
    private int userId;
    private String outletId;
    private String outletName;
    private String routeName;
    private String date;
    private String business;
    private ArrayList<OrderedProduct> orderedProducts;
    private boolean isLoading;

    public OrderedOutlet() {
        this.orderedProducts = new ArrayList<>();
        this.isLoading = false;
    }

    protected OrderedOutlet(Parcel in) {
        dbId = in.readInt();
        userId = in.readInt();
        outletId = in.readString();
        outletName = in.readString();
        routeName = in.readString();
        date = in.readString();
        business = in.readString();
        isLoading = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(dbId);
        dest.writeInt(userId);
        dest.writeString(outletId);
        dest.writeString(outletName);
        dest.writeString(routeName);
        dest.writeString(date);
        dest.writeString(business);
        dest.writeByte((byte) (isLoading ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderedOutlet> CREATOR = new Creator<OrderedOutlet>() {
        @Override
        public OrderedOutlet createFromParcel(Parcel in) {
            return new OrderedOutlet(in);
        }

        @Override
        public OrderedOutlet[] newArray(int size) {
            return new OrderedOutlet[size];
        }
    };

    // ✅ Safe JSON parser
    public static OrderedOutlet fromJson(JSONObject object) {
        OrderedOutlet outlet = new OrderedOutlet();
        outlet.dbId = object.optInt("db_id");
        outlet.userId = object.optInt("user_id");
        outlet.outletId = object.optString("outlet_id");
        outlet.outletName = object.optString("outlet_name");
        outlet.routeName = object.optString("route_name");
        outlet.date = object.optString("date");
        outlet.business = object.optString("business");

        JSONArray arrayOrders = object.optJSONArray("orders");
        if (arrayOrders != null) {
            for (int x = 0; x < arrayOrders.length(); x++) {
                JSONObject productObject = arrayOrders.optJSONObject(x);
                if (productObject != null) {
                    outlet.orderedProducts.add(OrderedProduct.fromJson(productObject));
                }
            }
        }
        return outlet;
    }

    public int getDbId() {
        return dbId;
    }

    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOutletId() {
        return outletId;
    }

    public void setOutletId(String outletId) {
        this.outletId = outletId;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public ArrayList<OrderedProduct> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(ArrayList<OrderedProduct> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

}
