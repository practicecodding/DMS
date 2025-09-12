package com.hamidul.dms.View.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.hamidul.dms.R;
import com.hamidul.dms.Service.Model.OrderedOutlet;
import com.hamidul.dms.Service.Model.OrderedProduct;
import com.hamidul.dms.Service.Network.ApiServices;
import com.hamidul.dms.Service.Network.VolleyInstance;
import com.hamidul.dms.View.Manager.ToastInstance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class OrderedOutletAdapter extends RecyclerView.Adapter<OrderedOutletAdapter.OrderedOutletAdapterViewHolder> {
    private final Context context;
    private final ArrayList<OrderedOutlet> orderedOutlets;
    private AlertDialog alertDialog;

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

        holder.buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    dialogConfirmCancelled(currentPosition);
                }
            }
        });

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

    //************************************************************************************
    private double safeParseDouble(String value) {
        if (value == null || value.trim().isEmpty()) return 0;
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    //************************************************************************************
    private void cancelOrder(int position) {
        orderedOutlets.get(position).setLoading(true);
        notifyItemChanged(position);

        if (position < 0 || position >= orderedOutlets.size()) return;

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("outlet_id", orderedOutlets.get(position).getOutletId());
            jsonObject.put("date", orderedOutlets.get(position).getDate());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        jsonArray.put(jsonObject);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, ApiServices.cancelOrder, jsonArray, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonObject = response.getJSONObject(0);
                    String type = jsonObject.getString("result");
                    ToastInstance.getInstance(context).setToast(type);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                if (position < orderedOutlets.size()) {
                    orderedOutlets.remove(position);
                    notifyItemRemoved(position);
                }

                if (orderedOutlets.isEmpty()) {
                    ((AppCompatActivity) context).getSupportFragmentManager().popBackStack();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                orderedOutlets.get(position).setLoading(false);
                notifyItemChanged(position);
                ToastInstance.getInstance(context).setToast("Network error");
            }
        });
        VolleyInstance.getVolleyInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    //************************************************************************************
    private void dialogConfirmCancelled(int position) {
        if (alertDialog == null || !alertDialog.isShowing()) {
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_confirm, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(view);

            TextView tvName = view.findViewById(R.id.tvName);
            Button buttonYes = view.findViewById(R.id.buttonYes);
            Button buttonNo = view.findViewById(R.id.buttonNo);

            tvName.setText("Are you sure " + orderedOutlets.get(position).getOutletName() + " did not deliver successfully ?");

            alertDialog = builder.create();

            buttonYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.cancel();
                    cancelOrder(position);
                }
            });

            buttonNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.cancel();
                }
            });

            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alertDialog.show();

        }


    }

}
