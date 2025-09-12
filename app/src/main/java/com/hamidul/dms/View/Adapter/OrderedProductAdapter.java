package com.hamidul.dms.View.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hamidul.dms.R;
import com.hamidul.dms.Service.Model.OrderedProduct;

import java.util.ArrayList;

public class OrderedProductAdapter extends RecyclerView.Adapter<OrderedProductAdapter.OrderProductAdapterViewHolder> {
    private final Context context;
    private final ArrayList<OrderedProduct> orderedProducts;

    public OrderedProductAdapter(Context context, ArrayList<OrderedProduct> orderedProducts) {
        this.context = context;
        this.orderedProducts = orderedProducts;
    }

    @NonNull
    @Override
    public OrderProductAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderProductAdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order_sku, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderProductAdapterViewHolder holder, @SuppressLint("RecyclerView") int position) {
        OrderedProduct product = orderedProducts.get(position);
        holder.tvSkuName.setText(product.getProductName());

        if (orderedProducts.get(position).getTp() % 1 == 0) {
            holder.tvTp.setText(String.format("%,.0f", product.getTp()));
        } else {
            holder.tvTp.setText(String.format("%,.2f", product.getTp()));
        }

        /*if (holder.edQuantity instanceof TextWatcher) {
            holder.edQuantity.removeTextChangedListener((TextWatcher) holder.edQuantity.getTag());
        }
        holder.edQuantity.setText(orderedProducts.get(position).getQuantity());

        TextWatcher watcherQuantity = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                if (!s.isEmpty() && s.startsWith("0")) {
                    editable.delete(0, 1);
                }
                if (s.isEmpty()) {
                    orderedProducts.get(position).setDiscount("0");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                orderedProducts.get(position).setQuantity(charSequence.toString());
                orderedProducts.get(position).setDiscount("0");
                holder.edDiscount.setText(orderedProducts.get(position).getDiscount());
            }
        };

        holder.edQuantity.addTextChangedListener(watcherQuantity);
        holder.edQuantity.setTag(watcherQuantity);

        if (holder.edDiscount instanceof TextWatcher) {
            holder.edDiscount.removeTextChangedListener((TextWatcher) holder.edDiscount.getTag());
        }
        holder.edDiscount.setText(orderedProducts.get(position).getDiscount());

        TextWatcher watcherDiscount = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString();
                if (!s.isEmpty() && s.startsWith("0")) {
                    editable.delete(0, 1);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                orderedProducts.get(position).setDiscount(charSequence.toString());
            }
        };

        holder.edDiscount.addTextChangedListener(watcherDiscount);
        holder.edDiscount.setTag(watcherDiscount);*/

        // --- Quantity ---
        if (holder.edQuantity.getTag() instanceof TextWatcher) {
            holder.edQuantity.removeTextChangedListener((TextWatcher) holder.edQuantity.getTag());
        }

        String newQty = product.getQuantity();
        if (!holder.edQuantity.getText().toString().equals(newQty)) {
            holder.edQuantity.setText(newQty);
        }

        TextWatcher watcherQuantity = new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                product.setQuantity(s.toString());
                product.setDiscount("0");
                holder.edDiscount.setText("0");
            }
        };

        holder.edQuantity.addTextChangedListener(watcherQuantity);
        holder.edQuantity.setTag(watcherQuantity);

        // --- Discount ---
        if (holder.edDiscount.getTag() instanceof TextWatcher) {
            holder.edDiscount.removeTextChangedListener((TextWatcher) holder.edDiscount.getTag());
        }

        String newDiscount = product.getDiscount();
        if (!holder.edDiscount.getText().toString().equals(newDiscount)) {
            holder.edDiscount.setText(newDiscount);
        }

        TextWatcher watcherDiscount = new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                product.setDiscount(s.toString());
            }
        };
        holder.edDiscount.addTextChangedListener(watcherDiscount);
        holder.edDiscount.setTag(watcherDiscount);

    }

    @Override
    public int getItemCount() {
        return orderedProducts.size();
    }

    public static class OrderProductAdapterViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvSkuName, tvTp;
        private final EditText edQuantity, edDiscount;

        public OrderProductAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSkuName = itemView.findViewById(R.id.tvSkuName);
            tvTp = itemView.findViewById(R.id.tvTp);
            edQuantity = itemView.findViewById(R.id.edQuantity);
            edDiscount = itemView.findViewById(R.id.edDiscount);

        }
    }

    public abstract static class SimpleTextWatcher implements TextWatcher {
        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override public void afterTextChanged(android.text.Editable s) {
            String string = s.toString();
            if (string.equals("0")){
                s.clear();
            }
        }
    }

}
