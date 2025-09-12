package com.hamidul.dms.View.Adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hamidul.dms.R;
import com.hamidul.dms.Service.Model.OrderedOutlet;
import com.hamidul.dms.Service.Model.OrderedProduct;
import com.hamidul.dms.View.Manager.ToastInstance;

import java.util.ArrayList;
import java.util.Objects;

public class OrderedOutletAdapter extends RecyclerView.Adapter<OrderedOutletAdapter.OrderedOutletAdapterViewHolder> {
    private final Context context;
    private final ArrayList<OrderedOutlet> orderedOutlets;

    public OrderedOutletAdapter(Context context, ArrayList<OrderedOutlet> orderedOutlets) {
        this.context = context;
        this.orderedOutlets = orderedOutlets;
    }

    @NonNull
    @Override
    public OrderedOutletAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderedOutletAdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.item_delivery_confirm, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderedOutletAdapterViewHolder holder, int position) {

        OrderedOutlet outlet = orderedOutlets.get(position);
        holder.tvDate.setText(outlet.getDate());
        holder.tvOutletName.setText(outlet.getOutletName());

        OrderedProductAdapter adapter = new OrderedProductAdapter(context, outlet.getOrderedProducts(), () -> updateAmount(outlet, holder));
        holder.recyclerView.setAdapter(adapter);

        updateAmount(outlet, holder);

        GradientDrawable whiteDrawable = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.button_bg).mutate();
        whiteDrawable.setColor(ContextCompat.getColor(context, R.color.whiteBackground));
        holder.buttonReturn.setBackground(whiteDrawable);

        GradientDrawable grayDrawable = (GradientDrawable) Objects.requireNonNull(ContextCompat.getDrawable(context, R.drawable.button_bg)).mutate();
        grayDrawable.setColor(ContextCompat.getColor(context, R.color.shimmerBackgroundColor));
        holder.buttonDue.setBackground(grayDrawable);

        if (outlet.isLoading()) {
            holder.progressBar.setVisibility(View.VISIBLE);
            holder.buttonView.setVisibility(View.GONE);
        } else {
            holder.progressBar.setVisibility(View.GONE);
            holder.buttonView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return orderedOutlets.size();
    }

    public class OrderedOutletAdapterViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDate, tvOutletName;
        private final RecyclerView recyclerView;
        private final ProgressBar progressBar;
        private final LinearLayout buttonView;
        private final AppCompatButton buttonReturn, buttonDue, buttonPaid;
        private final TextView tvTotalAmount, tvTotalDiscount, tvNetAmount;

        public OrderedOutletAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvOutletName = itemView.findViewById(R.id.tvOutletName);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            tvTotalAmount = itemView.findViewById(R.id.tvTotalAmount);
            tvTotalDiscount = itemView.findViewById(R.id.tvTotalDiscount);
            tvNetAmount = itemView.findViewById(R.id.tvNetAmount);
            progressBar = itemView.findViewById(R.id.progressBar);
            buttonView = itemView.findViewById(R.id.buttonView);
            buttonReturn = itemView.findViewById(R.id.buttonReturn);
            buttonDue = itemView.findViewById(R.id.buttonDue);
            buttonPaid = itemView.findViewById(R.id.buttonPaid);
        }
    }

    //************************************************************************************
    public void updateList(ArrayList<OrderedOutlet> newList) {
        orderedOutlets.clear();
        if (newList != null) {
            orderedOutlets.addAll(newList);
        }
        notifyDataSetChanged();
    }

    //************************************************************************************
    private String formatDouble(double value) {
        return (value % 1 == 0) ? String.format("%,.0f", value) : String.format("%,.2f", value);
    }

    //************************************************************************************
    private void updateAmount(OrderedOutlet outlet, OrderedOutletAdapterViewHolder holder) {
        double sumAmount = 0, sumDiscount = 0, sumNetOrder = 0;
        for (OrderedProduct item : outlet.getOrderedProducts()) {
            sumAmount += item.getTotalAmount();
            sumDiscount += safeParseDouble(item.getDiscount());
            sumNetOrder += item.getNetAmount();
        }

        holder.tvTotalAmount.setText(formatDouble(sumAmount));
        holder.tvTotalDiscount.setText(formatDouble(sumDiscount));
        holder.tvNetAmount.setText(formatDouble(sumNetOrder));

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
