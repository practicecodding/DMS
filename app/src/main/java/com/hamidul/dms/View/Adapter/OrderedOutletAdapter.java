package com.hamidul.dms.View.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hamidul.dms.R;
import com.hamidul.dms.Service.Model.OrderedOutlet;

import java.util.ArrayList;

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


        OrderedProductAdapter adapter = new OrderedProductAdapter(context, outlet.getOrderedProducts());
        holder.recyclerView.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return orderedOutlets.size();
    }

    public class OrderedOutletAdapterViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDate, tvOutletName;
        private final RecyclerView recyclerView;

        public OrderedOutletAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvOutletName = itemView.findViewById(R.id.tvOutletName);
            recyclerView = itemView.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
    }

    public void updateList(ArrayList<OrderedOutlet> newList) {
        orderedOutlets.clear();
        if (newList != null){
            orderedOutlets.addAll(newList);
        }
        notifyDataSetChanged();
    }

}
