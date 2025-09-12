package com.hamidul.dms.View.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hamidul.dms.R;
import com.hamidul.dms.Service.Model.User;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private final Context context;
    private final ArrayList<User> users = new ArrayList<>();
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(User user);
    }

    public UserAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(context).inflate(R.layout.item_user, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, @SuppressLint("RecyclerView") int position) {
        User user = users.get(position);
        holder.tvName.setText(user.getName());

        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(user);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout layoutItem;
        private final TextView tvName;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutItem = itemView.findViewById(R.id.layoutItem);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }

    public void updateList(ArrayList<User> newUsers) {
        users.clear();
        if (newUsers != null) {
            users.addAll(newUsers);
        }
        notifyDataSetChanged(); // Not the most efficient, but works fine without DiffUtil
    }

}
