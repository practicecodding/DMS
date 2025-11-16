package com.hamidul.dms.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hamidul.dms.R;
import com.hamidul.dms.Service.Model.DeliveredOutlet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DeliveredOutletAdapter extends RecyclerView.Adapter {
    private final Context context;
    private final ArrayList<DeliveredOutlet> deliveredOutlets;
    private int CANCELLED_ITEM = 0;
    private int DELIVERED_ITEM = 1;

    public DeliveredOutletAdapter(Context context, ArrayList<DeliveredOutlet> deliveredOutlets) {
        this.context = context;
        this.deliveredOutlets = deliveredOutlets;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == CANCELLED_ITEM) {
            View myView = LayoutInflater.from(context).inflate(R.layout.item_cancelled, parent, false);
            return new CancelledViewHolder(myView);
        } else {
            View myView = LayoutInflater.from(context).inflate(R.layout.item_delivered, parent, false);
            return new DeliveredViewHolder(myView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        DeliveredOutlet outlet = deliveredOutlets.get(position);

        if (getItemViewType(position) == CANCELLED_ITEM) {
            CancelledViewHolder cancelledViewHolder = (CancelledViewHolder) holder;
            try {
                Date date = new SimpleDateFormat("dd-MM-yyyy").parse(outlet.getDate());
                cancelledViewHolder.tvDate.setText(new SimpleDateFormat("dd-MMMM-yyyy").format(date));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            cancelledViewHolder.tvOutletName.setText(outlet.getOutletName());
            cancelledViewHolder.tvOutletBanglaName.setText(outlet.getOutletBanglaName());
            cancelledViewHolder.tvOutletAddress.setText(outlet.getOutletAddress());
            cancelledViewHolder.tvNetDeliveryAmount.setText(String.format("%,.0f", outlet.getNetDelivery()));

        } else {
            DeliveredViewHolder deliveredViewHolder = (DeliveredViewHolder) holder;
            try {
                Date date = new SimpleDateFormat("dd-MM-yyyy").parse(outlet.getDate());
                deliveredViewHolder.tvDate.setText(new SimpleDateFormat("dd-MMMM-yyyy").format(date));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            deliveredViewHolder.tvOutletName.setText(outlet.getOutletName());
            deliveredViewHolder.tvOutletBanglaName.setText(outlet.getOutletBanglaName());
            deliveredViewHolder.tvOutletAddress.setText(outlet.getOutletAddress());
            deliveredViewHolder.tvNetDeliveryAmount.setText(String.format("%,.0f", outlet.getNetDelivery()));
            deliveredViewHolder.tvDamageAmount.setText(String.format("%,.0f", outlet.getDamage()));

        }

    }

    @Override
    public int getItemCount() {
        return deliveredOutlets.size();
    }

    private static class CancelledViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDate;
        private final TextView tvOutletName, tvOutletBanglaName, tvOutletAddress;
        private final TextView tvNetOrderAmount, tvNetDeliveryAmount;
        public CancelledViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvOutletName = itemView.findViewById(R.id.tvOutletName);
            tvOutletBanglaName = itemView.findViewById(R.id.tvOutletBanglaName);
            tvOutletAddress = itemView.findViewById(R.id.tvOutletAddress);
            tvNetOrderAmount = itemView.findViewById(R.id.tvNetOrderAmount);
            tvNetDeliveryAmount = itemView.findViewById(R.id.tvNetDeliveryAmount);
        }
    }

    private static class DeliveredViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDate;
        private final TextView tvOutletName, tvOutletBanglaName, tvOutletAddress;
        private final TextView tvNetOrderAmount, tvNetDeliveryAmount, tvDamageAmount;
        private final TextView tvCommissionAmount, tvCashAmount, tvDueAmount;
        public DeliveredViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvOutletName = itemView.findViewById(R.id.tvOutletName);
            tvOutletBanglaName = itemView.findViewById(R.id.tvOutletBanglaName);
            tvOutletAddress = itemView.findViewById(R.id.tvOutletAddress);
            tvNetOrderAmount = itemView.findViewById(R.id.tvNetOrderAmount);
            tvNetDeliveryAmount = itemView.findViewById(R.id.tvNetDeliveryAmount);
            tvDamageAmount = itemView.findViewById(R.id.tvDamageAmount);
            tvCommissionAmount = itemView.findViewById(R.id.tvCommissionAmount);
            tvCashAmount = itemView.findViewById(R.id.tvCashAmount);
            tvDueAmount = itemView.findViewById(R.id.tvDueAmount);
        }
    }

    //************************************************************************************
    @Override
    public int getItemViewType(int position) {
        if (deliveredOutlets.get(position).getNetDelivery() == 0) return CANCELLED_ITEM;
        else return DELIVERED_ITEM;
    }

    //************************************************************************************
    public void updateList(ArrayList<DeliveredOutlet> newList){
        deliveredOutlets.clear();
        if (newList != null){
            deliveredOutlets.addAll(newList);
        }
        notifyDataSetChanged();
    }
}
