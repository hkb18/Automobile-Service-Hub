package com.example.auton;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class VacuumCleaner_Adapter extends RecyclerView.Adapter<VacuumCleaner_Adapter.ViewHold>{
    private ArrayList<VacuumCleaner_ModelClass> dataList;
    private final Context context;


    public VacuumCleaner_Adapter(Context fragment, ArrayList<VacuumCleaner_ModelClass> dataList) {
        this.dataList = dataList;
        this.context = fragment;

    }
    @NonNull
    @Override
    public VacuumCleaner_Adapter.ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vacuumcleaner_layout, parent, false);
        return new VacuumCleaner_Adapter.ViewHold(view);
    }

    public void onBindViewHolder(VacuumCleaner_Adapter.ViewHold holder,int position){
        VacuumCleaner_ModelClass ss=dataList.get(position);
        holder.manufacturer.setText(ss.getManufacturer());
        holder.desc.setText(ss.getModel());
        holder.price.setText(ss.getPrice());
        Glide.with(context).load(ss.getImage()).into(holder.productImg);
    }

    public void filterList(ArrayList<VacuumCleaner_ModelClass> filteredlist) {
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
        TextView manufacturer,desc,price;
        public ViewHold(@NonNull View itemView) {
            super(itemView);
            manufacturer=itemView.findViewById(R.id.vacuumcleanerManufacturer);
            desc=itemView.findViewById(R.id.vacuumcleanerDesc);
            productImg=itemView.findViewById(R.id.vacuumcleanerImg);
            price=itemView.findViewById(R.id.vacuumcleanerPrice);
        }
    }}
