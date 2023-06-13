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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Amplifier_Adapter extends RecyclerView.Adapter<Amplifier_Adapter.ViewHold>{
    private ArrayList<Amplifier_ModelClass> dataList;
    private final Context context;

    public Amplifier_Adapter(Context fragment, ArrayList<Amplifier_ModelClass> dataList) {
        this.dataList = dataList;
        this.context = fragment;

    }
    @NonNull
    @Override
    public Amplifier_Adapter.ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.amplifier_layout, parent, false);
        return new Amplifier_Adapter.ViewHold(view);
    }

    public void onBindViewHolder(Amplifier_Adapter.ViewHold holder,int position){

        Amplifier_ModelClass ss=dataList.get(position);
        holder.manufacturer.setText(ss.getManufacturer());
        holder.desc.setText(ss.getChannel()+ss.getModel());
        Glide.with(context).load(ss.getImage()).into(holder.productImg);
    }

    public void filterList(ArrayList<Amplifier_ModelClass> filteredlist) {
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
            manufacturer=itemView.findViewById(R.id.amplifierManufacturer);
            desc=itemView.findViewById(R.id.amplifierDesc);
            productImg=itemView.findViewById(R.id.amplifierImg);
        }
    }
}
