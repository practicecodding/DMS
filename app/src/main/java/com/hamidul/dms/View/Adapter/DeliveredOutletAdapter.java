package com.hamidul.dms.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hamidul.dms.R;
import com.hamidul.dms.Service.Model.DeliveredOutlet;
import com.hamidul.dms.Service.Model.DeliveredProduct;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DeliveredOutletAdapter extends RecyclerView.Adapter {
    private final Context context;
    private final ArrayList<DeliveredOutlet> deliveredOutlets;
    private final int CANCELLED_ITEM = 1;
    private final int DELIVERED_ITEM = 2;

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

            DeliveredProductAdapter adapter = new DeliveredProductAdapter(context, outlet.getProducts());
            cancelledViewHolder.recyclerView.setAdapter(adapter);

            double netOrder = 0;
            for (DeliveredProduct item : outlet.getProducts()){
                netOrder += item.getNetOrderAmount();
            }
            cancelledViewHolder.tvNetOrderAmount.setText(String.format("%,.0f", netOrder));

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

            DeliveredProductAdapter adapter = new DeliveredProductAdapter(context, outlet.getProducts());
            deliveredViewHolder.recyclerView.setAdapter(adapter);

            double netOrder = 0;
            for (DeliveredProduct item : outlet.getProducts()){
                netOrder += item.getNetOrderAmount();
            }
            deliveredViewHolder.tvNetOrderAmount.setText(String.format("%,.0f", netOrder));

            deliveredViewHolder.tvNetDeliveryAmount.setText(String.format("%,.0f", outlet.getNetDelivery()));

            if (outlet.getDamage() > 0) {
                deliveredViewHolder.cvDamage.setVisibility(View.VISIBLE);
                deliveredViewHolder.tvDamageAmount.setText(String.format("%,.0f", outlet.getDamage()));
            } else {
                deliveredViewHolder.cvDamage.setVisibility(View.GONE);
            }

            if (outlet.getCommission() > 0) {
                deliveredViewHolder.cvCommission.setVisibility(View.VISIBLE);
                deliveredViewHolder.tvCommissionAmount.setText(String.format("%,.0f", outlet.getCommission()));
            } else {
                deliveredViewHolder.cvCommission.setVisibility(View.GONE);
            }

            if (outlet.getCash() > 0) {
                deliveredViewHolder.cvCash.setVisibility(View.VISIBLE);
                deliveredViewHolder.tvCashAmount.setText(String.format("%,.0f", outlet.getCash()));
            } else {
                deliveredViewHolder.cvCash.setVisibility(View.GONE);
            }

            if (outlet.getDue() > 0) {
                deliveredViewHolder.cvDue.setVisibility(View.VISIBLE);
                deliveredViewHolder.tvDueAmount.setText(String.format("%,.0f", outlet.getDue()));
            } else {
                deliveredViewHolder.cvDue.setVisibility(View.GONE);
            }

        }

    }

    @Override
    public int getItemCount() {
        return deliveredOutlets.size();
    }

    private static class CancelledViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDate;
        private final TextView tvOutletName, tvOutletBanglaName, tvOutletAddress;
        private final RecyclerView recyclerView;
        private final TextView tvNetOrderAmount;
        public CancelledViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvOutletName = itemView.findViewById(R.id.tvOutletName);
            tvOutletBanglaName = itemView.findViewById(R.id.tvOutletBanglaName);
            tvOutletAddress = itemView.findViewById(R.id.tvOutletAddress);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            tvNetOrderAmount = itemView.findViewById(R.id.tvNetOrderAmount);
        }
    }

    private static class DeliveredViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDate;
        private final TextView tvOutletName, tvOutletBanglaName, tvOutletAddress;
        private final RecyclerView recyclerView;
        private final TextView tvNetOrderAmount, tvNetDeliveryAmount, tvDamageAmount;
        private final TextView tvCommissionAmount, tvCashAmount, tvDueAmount;
        private final CardView cvDamage, cvCommission, cvCash, cvDue;

        public DeliveredViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvOutletName = itemView.findViewById(R.id.tvOutletName);
            tvOutletBanglaName = itemView.findViewById(R.id.tvOutletBanglaName);
            tvOutletAddress = itemView.findViewById(R.id.tvOutletAddress);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            tvNetOrderAmount = itemView.findViewById(R.id.tvNetOrderAmount);
            tvNetDeliveryAmount = itemView.findViewById(R.id.tvNetDeliveryAmount);
            tvDamageAmount = itemView.findViewById(R.id.tvDamageAmount);
            tvCommissionAmount = itemView.findViewById(R.id.tvCommissionAmount);
            tvCashAmount = itemView.findViewById(R.id.tvCashAmount);
            tvDueAmount = itemView.findViewById(R.id.tvDueAmount);
            cvDamage = itemView.findViewById(R.id.cvDamage);
            cvCommission = itemView.findViewById(R.id.cvCommission);
            cvCash = itemView.findViewById(R.id.cvCash);
            cvDue = itemView.findViewById(R.id.cvDue);
        }
    }

    //************************************************************************************
    @Override
    public int getItemViewType(int position) {
        if (deliveredOutlets.get(position).getNetDelivery() == 0) return CANCELLED_ITEM;
        else return DELIVERED_ITEM;
    }

    //************************************************************************************
    public void updateList(ArrayList<DeliveredOutlet> newList) {
        deliveredOutlets.clear();
        if (newList != null) {
            deliveredOutlets.addAll(newList);
        }
        notifyDataSetChanged();
    }
}
