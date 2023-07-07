package com.example.auton;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BassTubes_Adapter_2 extends RecyclerView.Adapter<BassTubes_Adapter_2.ViewHold> {
    private final Context context;
    private ArrayList<Accessories_ModelClass> dataList;


    public BassTubes_Adapter_2(Context fragment, ArrayList<Accessories_ModelClass> dataList) {
        this.dataList = dataList;
        this.context = fragment;

    }

    @NonNull
    @Override
    public BassTubes_Adapter_2.ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.basstubes_layout, parent, false);
        return new BassTubes_Adapter_2.ViewHold(view);
    }

    public void onBindViewHolder(BassTubes_Adapter_2.ViewHold holder, int position) {

        Accessories_ModelClass ss = dataList.get(position);
        holder.manufacturer.setText(ss.getManufacturer());
        holder.desc.setText(ss.getModel());
        holder.price.setText(ss.getPrice());
        Glide.with(context).load(ss.getImage()).into(holder.productImg);
        if (Integer.parseInt(ss.getQuantity()) == 0) {
            holder.tvStatus.setVisibility(View.VISIBLE);
            holder.cardView.setAlpha(0.5F);
        }
        holder.cardView.setOnClickListener(v -> {
            Intent i = new Intent(context.getApplicationContext(), fulldetails_Basstubes.class);
            i.putExtra("key", ss.getModel());
            context.startActivity(i);
        });
    }

    public void filterList(ArrayList<Accessories_ModelClass> filteredlist) {
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
        ImageView productImg;
        TextView manufacturer, desc, price, tvStatus;
        CardView cardView;

        public ViewHold(@NonNull View itemView) {
            super(itemView);
            manufacturer = itemView.findViewById(R.id.basstubesManufacturer);
            desc = itemView.findViewById(R.id.basstubesDesc);
            productImg = itemView.findViewById(R.id.basstubesImg);
            price = itemView.findViewById(R.id.basstubesPrice);
            cardView = itemView.findViewById(R.id.cvBassTubes);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}
