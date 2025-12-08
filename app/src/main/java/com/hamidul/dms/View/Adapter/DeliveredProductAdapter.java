package com.hamidul.dms.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hamidul.dms.R;
import com.hamidul.dms.Service.Model.DeliveredProduct;

import java.util.ArrayList;

public class DeliveredProductAdapter extends RecyclerView.Adapter {
    private final Context context;
    private final ArrayList<DeliveredProduct> products;
    private final boolean delivered;

    public DeliveredProductAdapter(Context context, ArrayList<DeliveredProduct> products, boolean delivered) {
        this.context = context;
        this.products = products;
        this.delivered = delivered;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (delivered) {
            View myView = LayoutInflater.from(context).inflate(R.layout.item_delivered_sku, parent, false);
            return new DeliveredProductViewHolder(myView);
        } else {
            View myView = LayoutInflater.from(context).inflate(R.layout.item_cancelled_sku, parent, false);
            return new CancelledProductViewHolder(myView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        DeliveredProduct product = products.get(position);

        if (delivered) {

            DeliveredProductViewHolder deliveredProductViewHolder = (DeliveredProductViewHolder) holder;
            deliveredProductViewHolder.tvSkuName.setText(product.getProductName());

            if (product.getTp() % 1 == 0) {
                deliveredProductViewHolder.tvTp.setText(String.format("%,.0f", product.getTp()));
            } else {
                deliveredProductViewHolder.tvTp.setText(String.format("%,.2f", product.getTp()));
            }

            if (product.getOrderDiscount() % 1 == 0) {
                deliveredProductViewHolder.tvOrderDiscount.setText(String.format("%,.0f", product.getOrderDiscount()));
            } else {
                deliveredProductViewHolder.tvOrderDiscount.setText(String.format("%,.2f", product.getOrderDiscount()));
            }

            if (product.getDeliveryDiscount() % 1 == 0) {
                deliveredProductViewHolder.tvDeliveryDiscount.setText(String.format("%,.0f", product.getDeliveryDiscount()));
            } else {
                deliveredProductViewHolder.tvDeliveryDiscount.setText(String.format("%,.2f", product.getDeliveryDiscount()));
            }

            deliveredProductViewHolder.tvOrderQuantity.setText(String.valueOf(product.getOrderQuantity()));
            deliveredProductViewHolder.tvDeliveryQuantity.setText(String.valueOf(product.getDeliveryQuantity()));

        } else {

            CancelledProductViewHolder cancelledProductViewHolder = (CancelledProductViewHolder) holder;
            cancelledProductViewHolder.tvSkuName.setText(product.getProductName());

            if (product.getOrderDiscount() % 1 == 0) {
                cancelledProductViewHolder.tvDiscount.setText(String.format("%,.0f", product.getOrderDiscount()));
            } else {
                cancelledProductViewHolder.tvDiscount.setText(String.format("%,.2f", product.getOrderDiscount()));
            }

            if (product.getTp() % 1 == 0) {
                cancelledProductViewHolder.tvTp.setText(String.format("%,.0f", product.getTp()));
            } else {
                cancelledProductViewHolder.tvTp.setText(String.format("%,.2f", product.getTp()));
            }

            cancelledProductViewHolder.tvOrderQuantity.setText(String.valueOf(product.getOrderQuantity()));

        }

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class CancelledProductViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvSkuName, tvDiscount, tvTp, tvOrderQuantity;

        public CancelledProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSkuName = itemView.findViewById(R.id.tvSkuName);
            tvDiscount = itemView.findViewById(R.id.tvDiscount);
            tvTp = itemView.findViewById(R.id.tvTp);
            tvOrderQuantity = itemView.findViewById(R.id.tvOrderQuantity);
        }
    }

    public static class DeliveredProductViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvSkuName, tvTp, tvOrderDiscount, tvDeliveryDiscount, tvOrderQuantity, tvDeliveryQuantity;

        public DeliveredProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSkuName = itemView.findViewById(R.id.tvSkuName);
            tvTp = itemView.findViewById(R.id.tvTp);
            tvOrderDiscount = itemView.findViewById(R.id.tvOrderDiscount);
            tvDeliveryDiscount = itemView.findViewById(R.id.tvDeliveryDiscount);
            tvOrderQuantity = itemView.findViewById(R.id.tvOrderQuantity);
            tvDeliveryQuantity = itemView.findViewById(R.id.tvDeliveryQuantity);
        }
    }

}
