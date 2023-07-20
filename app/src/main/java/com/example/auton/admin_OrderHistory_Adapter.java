package com.example.auton;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class admin_OrderHistory_Adapter extends RecyclerView.Adapter<admin_OrderHistory_Adapter.ViewHold> {
    private final Context context;
    private ArrayList<OrderHistory_ModelClass> dataList;


    public admin_OrderHistory_Adapter(Activity user_cart_fragment, ArrayList<OrderHistory_ModelClass> list) {
        this.context = user_cart_fragment;
        this.dataList = list;
    }

    @NonNull
    @Override
    public admin_OrderHistory_Adapter.ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderhistory_admin_layout, parent, false);
        return new admin_OrderHistory_Adapter.ViewHold(view);
    }

    public void onBindViewHolder(admin_OrderHistory_Adapter.ViewHold holder, int position) {
        OrderHistory_ModelClass ss = dataList.get(position);
        holder.manufacturer.setText(ss.getManufacturer());
        holder.model.setText(ss.getModel());
        holder.qty.setText(ss.getQuantity());
        holder.price.setText(ss.getPrice());
        holder.username.setText(ss.getUsername());
        Glide.with(context).load(ss.getImage()).into(holder.imageView);

    }

    public void filterList(ArrayList<OrderHistory_ModelClass> filteredlist) {
        // below line is to add our filtered
        // list in our course array list.
        dataList = filteredlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHold extends RecyclerView.ViewHolder {
        TextView manufacturer, model, qty, price, username;
        ImageView imageView;

        public ViewHold(@NonNull View itemView) {
            super(itemView);
            manufacturer = itemView.findViewById(R.id.tvManufacturer);
            model = itemView.findViewById(R.id.tvModel);
            qty = itemView.findViewById(R.id.tvQuantity);
            price = itemView.findViewById(R.id.tvPrice);
            username = itemView.findViewById(R.id.tvUsername);
            imageView = itemView.findViewById(R.id.cartImage);
        }
    }
}
