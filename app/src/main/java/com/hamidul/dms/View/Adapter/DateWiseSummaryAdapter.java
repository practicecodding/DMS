package com.hamidul.dms.View.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hamidul.dms.R;
import com.hamidul.dms.Service.Model.DateWiseSummary;
import com.hamidul.dms.Service.Model.ProductSummary;

import java.util.ArrayList;

public class DateWiseSummaryAdapter extends RecyclerView.Adapter {
    private final Context context;
    private final ArrayList<DateWiseSummary> dateWiseSummaries;
    private SummaryAdapter adapter;
    private double netAmount, sumNetKelloggsValues, sumNetPringlesValues;

    public DateWiseSummaryAdapter(Context context, ArrayList<DateWiseSummary> dateWiseSummaries) {
        this.context = context;
        this.dateWiseSummaries = dateWiseSummaries;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(context).inflate(R.layout.item_date_wise_summary, parent, false);
        return new DateWiseSummaryAdapterViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        DateWiseSummaryAdapterViewHolder dateWiseSummaryAdapterViewHolder = (DateWiseSummaryAdapterViewHolder) holder;

        dateWiseSummaryAdapterViewHolder.tvDate.setText(dateWiseSummaries.get(position).getDate());

        netAmount = 0;
        for (ProductSummary item : dateWiseSummaries.get(position).getProductSummaries()) {
            netAmount = netAmount + item.getNetAmount();
        }
        dateWiseSummaryAdapterViewHolder.tvNetAmount.setText(String.format("%,.2f", netAmount));


        if (dateWiseSummaries.get(position).isShowing()) {
            dateWiseSummaryAdapterViewHolder.layoutSummary.setVisibility(View.VISIBLE);
        } else {
            dateWiseSummaryAdapterViewHolder.layoutSummary.setVisibility(View.GONE);
        }
        dateWiseSummaryAdapterViewHolder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dateWiseSummaries.get(position).isShowing()) {
                    dateWiseSummaryAdapterViewHolder.layoutSummary.setVisibility(View.GONE);
                    dateWiseSummaries.get(position).setShowing(false);
                } else {
                    dateWiseSummaryAdapterViewHolder.layoutSummary.setVisibility(View.VISIBLE);
                    dateWiseSummaries.get(position).setShowing(true);
                }
            }
        });

        sumNetKelloggsValues = 0;
        sumNetPringlesValues = 0;
        for (ProductSummary item : dateWiseSummaries.get(position).getProductSummaries()) {
            if (item.getProductName().contains("Pringles")) {
                sumNetPringlesValues = sumNetPringlesValues + item.getNetAmount();
            } else {
                sumNetKelloggsValues = sumNetKelloggsValues + item.getNetAmount();
            }
        }
        dateWiseSummaryAdapterViewHolder.tvNetAmountKelloggS.setText(String.format("%,.2f", sumNetKelloggsValues));
        dateWiseSummaryAdapterViewHolder.tvNetAmountPringles.setText(String.format("%,.2f", sumNetPringlesValues));

        if (dateWiseSummaries.get(position).isShowingDetails()) {
            dateWiseSummaryAdapterViewHolder.layoutNetAmount.setVisibility(View.VISIBLE);
        } else {
            dateWiseSummaryAdapterViewHolder.layoutNetAmount.setVisibility(View.GONE);
        }
        dateWiseSummaryAdapterViewHolder.layoutDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dateWiseSummaries.get(position).isShowingDetails()) {
                    dateWiseSummaryAdapterViewHolder.layoutNetAmount.setVisibility(View.GONE);
                    dateWiseSummaries.get(position).setShowingDetails(false);
                } else {
                    dateWiseSummaryAdapterViewHolder.layoutNetAmount.setVisibility(View.VISIBLE);
                    dateWiseSummaries.get(position).setShowingDetails(true);
                }
            }
        });

        adapter = new SummaryAdapter(context, dateWiseSummaries.get(position).getProductSummaries());
        dateWiseSummaryAdapterViewHolder.recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return dateWiseSummaries.size();
    }

    public class DateWiseSummaryAdapterViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDate, tvNetAmount, tvNetAmountKelloggS, tvNetAmountPringles;
        private final LinearLayout layoutItem, layoutSummary, layoutNetAmount, layoutDetails;
        private final RecyclerView recyclerView;

        public DateWiseSummaryAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvNetAmount = itemView.findViewById(R.id.tvNetAmount);
            layoutItem = itemView.findViewById(R.id.layoutItem);
            layoutSummary = itemView.findViewById(R.id.layoutSummary);
            layoutNetAmount = itemView.findViewById(R.id.layoutNetAmount);
            layoutDetails = itemView.findViewById(R.id.layoutDetails);
            tvNetAmountKelloggS = itemView.findViewById(R.id.tvNetAmountKelloggS);
            tvNetAmountPringles = itemView.findViewById(R.id.tvNetAmountPringles);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
    }

    public void updateList(ArrayList<DateWiseSummary> newList) {
        dateWiseSummaries.clear();
        if (newList != null) {
            dateWiseSummaries.addAll(newList);
        }
        notifyDataSetChanged(); // Not the most efficient, but works fine without DiffUtil
    }

}
