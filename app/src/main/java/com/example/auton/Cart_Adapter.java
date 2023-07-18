package com.example.auton;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Cart_Adapter extends RecyclerView.Adapter<Cart_Adapter.ViewHold> {
    private final Context context;
    private ArrayList<cart_ModelClass> dataList;
    private String name;


    public Cart_Adapter(Activity user_cart_fragment, ArrayList<cart_ModelClass> list) {
        this.context = user_cart_fragment;
        this.dataList = list;
    }

    @NonNull
    @Override
    public Cart_Adapter.ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout, parent, false);
        return new Cart_Adapter.ViewHold(view);
    }

    public void onBindViewHolder(Cart_Adapter.ViewHold holder, int position) {
        cart_ModelClass ss = dataList.get(position);
        if (ss.getModel().isEmpty()) {
            holder.manufacturer.setText(ss.getManufacturer());
            holder.model.setVisibility(View.GONE);
            holder.qty.setText(ss.getQuantity());
            holder.price.setText(ss.getPrice());
            Glide.with(context).load(ss.getImage()).into(holder.imageView);
        } else {
            holder.manufacturer.setText(ss.getManufacturer());
            holder.model.setText(ss.getModel());
            holder.qty.setText(ss.getQuantity());
            holder.price.setText(ss.getPrice());
            Glide.with(context).load(ss.getImage()).into(holder.imageView);
        }
        holder.imageView.setOnClickListener(view -> {

        });

        holder.delete.setOnClickListener(view -> {
            user_Cart_fragment.onClickInterface.delmech(ss.getProductKey(), position);

        });
        holder.add.setOnClickListener(view -> {
            user_Cart_fragment.onClickInterface.add(ss.getQuantity(), position);


        });
        holder.sub.setOnClickListener(view -> {
            user_Cart_fragment.onClickInterface.remove(ss.getQuantity(), position);

        });
    }

    public void filterList(ArrayList<cart_ModelClass> filteredlist) {
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
        TextView manufacturer, model, qty, price;
        ImageView imageView;
        Button add, sub, delete;

        public ViewHold(@NonNull View itemView) {
            super(itemView);
            manufacturer = itemView.findViewById(R.id.tvManufacturer);
            model = itemView.findViewById(R.id.tvModel);
            qty = itemView.findViewById(R.id.tvQuantity);
            price = itemView.findViewById(R.id.tvPrice);
            imageView = itemView.findViewById(R.id.cartImage);
            add = itemView.findViewById(R.id.btn_add);
            sub = itemView.findViewById(R.id.btn_substract);
            delete = itemView.findViewById(R.id.btn_Delete);
        }
    }
}
