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
    private final OnProductChangeListener listener;

    public interface OnProductChangeListener {
        void onProductChanged();
    }

    public OrderedProductAdapter(Context context, ArrayList<OrderedProduct> orderedProducts, OnProductChangeListener listener) {
        this.context = context;
        this.orderedProducts = orderedProducts;
        this.listener = listener;
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

        holder.tvTp.setText(formatDouble(product.getTp()));

        // --- Quantity ---
        if (holder.edQuantity instanceof TextWatcher) {
            holder.edQuantity.removeTextChangedListener((TextWatcher) holder.edQuantity.getTag());
        }
        holder.edQuantity.setText(orderedProducts.get(position).getQuantity());

        TextWatcher watcherQuantity = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString().trim();

                /*if (!s.isEmpty() && s.startsWith("0")) {
                    editable.delete(0, 1);
                }

                if (s.isEmpty()) {
                    product.setQuantity("0");
                    holder.edQuantity.setText("0");
                    product.setDiscount("0");
                }*/

                if (s.isEmpty()) {
                    s = "0"; // default if empty
                    holder.edQuantity.setText(s);
                    holder.edQuantity.setSelection(s.length()); // move cursor to end
                }

                product.setQuantity(s);
                product.setDiscount("0");
                holder.edDiscount.setText("0");

                product.setTotalAmount(Double.parseDouble(product.getQuantity()) * product.getTp());
                product.setNetAmount(product.getTotalAmount() - safeParseDouble(product.getDiscount()));

                if (listener != null) listener.onProductChanged();

            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                /*product.setQuantity(charSequence.toString());
                product.setDiscount("0");
                product.setTotalAmount(Double.parseDouble(product.getQuantity()) * product.getTp());
                product.setNetAmount(product.getTotalAmount() - Double.parseDouble(product.getDiscount()));
                holder.edDiscount.setText(product.getDiscount());

                if (listener != null) listener.onProductChanged();*/

            }
        };

        holder.edQuantity.addTextChangedListener(watcherQuantity);
        holder.edQuantity.setTag(watcherQuantity);

        // --- Discount ---
        if (holder.edDiscount instanceof TextWatcher) {
            holder.edDiscount.removeTextChangedListener((TextWatcher) holder.edDiscount.getTag());
        }
        holder.edDiscount.setText(product.getDiscount());

        TextWatcher watcherDiscount = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                String s = editable.toString().trim();

                /*if (!s.isEmpty() && s.startsWith("0")) {
                    editable.delete(0, 1);
                }

                if (s.isEmpty()) {
                    holder.edDiscount.setText(s);
                }*/

                if (s.isEmpty()) {
                    s = "0"; // default if empty
                    holder.edDiscount.setText(s);
                    holder.edDiscount.setSelection(s.length());
                }

                product.setDiscount(s);

                product.setNetAmount(product.getTotalAmount() - safeParseDouble(product.getDiscount()));

                if (listener != null) listener.onProductChanged();

            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                /*product.setDiscount(charSequence.toString());
                product.setNetAmount(product.getTotalAmount() - Double.parseDouble(product.getDiscount()));
                if (listener != null) listener.onProductChanged();*/
            }
        };

        holder.edDiscount.addTextChangedListener(watcherDiscount);
        holder.edDiscount.setTag(watcherDiscount);

        // --- Quantity ---
        /*if (holder.edQuantity.getTag() instanceof TextWatcher) {
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
        holder.edDiscount.setTag(watcherDiscount);*/

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

    //************************************************************************************
    private String formatDouble(double value) {
        return (value % 1 == 0) ? String.format("%,.0f", value) : String.format("%,.2f", value);
    }

    //************************************************************************************
    public abstract static class SimpleTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(android.text.Editable s) {
            String string = s.toString();
            if (string.equals("0")) {
                s.clear();
            }
        }
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
