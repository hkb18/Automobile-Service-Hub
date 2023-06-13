package com.example.auton;

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

public class BassTubes_Adapter extends RecyclerView.Adapter<BassTubes_Adapter.ViewHold>{
    private ArrayList<BassTubes_ModelClass> dataList;
    private final Context context;

    public BassTubes_Adapter(Context fragment, ArrayList<BassTubes_ModelClass> dataList) {
        this.dataList = dataList;
        this.context = fragment;

    }
    @NonNull
    @Override
    public BassTubes_Adapter.ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.basstubes_layout, parent, false);
        return new BassTubes_Adapter.ViewHold(view);
    }

    public void onBindViewHolder(BassTubes_Adapter.ViewHold holder,int position){

        BassTubes_ModelClass ss=dataList.get(position);
        holder.manufacturer.setText(ss.getManufacturer());
        holder.desc.setText(ss.getModel());
        Glide.with(context).load(ss.getImage()).into(holder.productImg);
    }

    public void filterList(ArrayList<BassTubes_ModelClass> filteredlist) {
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
        TextView manufacturer,desc;
        public ViewHold(@NonNull View itemView) {
            super(itemView);
            manufacturer=itemView.findViewById(R.id.basstubesManufacturer);
            desc=itemView.findViewById(R.id.basstubesDesc);
            productImg=itemView.findViewById(R.id.basstubesImg);
        }
    }
}
