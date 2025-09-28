package com.hamidul.dms.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hamidul.dms.R;
import com.hamidul.dms.Service.Model.ProductSummary;

import java.util.ArrayList;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.SummaryViewHolder> {

    private final Context context;
    private final ArrayList<ProductSummary> summaries;

    public SummaryAdapter(Context context, ArrayList<ProductSummary> summaries) {
        this.context = context;
        this.summaries = summaries;
    }

    @NonNull
    @Override
    public SummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(context).inflate(R.layout.item_summary, parent, false);
        return new SummaryViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull SummaryViewHolder holder, int position) {

        int sn = position + 1;

        holder.tvSerialNumber.setText(String.valueOf(sn));
        holder.tvProductName.setText(summaries.get(position).getProductName());
        holder.tvQuantity.setText(String.valueOf(summaries.get(position).getQuantity()));
        //holder.productAmount.setText("Amount\n"+summaries.get(position).getTotalPrice());

        if (summaries.get(position).getNetAmount() % 1 == 0) {
            holder.tvAmount.setText(String.format("%,.0f", summaries.get(position).getNetAmount()));
        } else {
            holder.tvAmount.setText(String.format("%,.2f", summaries.get(position).getNetAmount()));
        }
    }

    @Override
    public int getItemCount() {
        return summaries.size();
    }

    public static class SummaryViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvSerialNumber, tvProductName, tvQuantity, tvAmount;

        public SummaryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSerialNumber = itemView.findViewById(R.id.tvSerialNumber);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvAmount = itemView.findViewById(R.id.tvAmount);

        }
    }

}
