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

public class Washers_Adapter2 extends RecyclerView.Adapter<Washers_Adapter2.ViewHold> {
    private ArrayList<Accessories_ModelClass> dataList;
    private final Context context;


    public Washers_Adapter2(Context fragment, ArrayList<Accessories_ModelClass> dataList) {
        this.dataList = dataList;
        this.context = fragment;

    }
    @NonNull
    @Override
    public Washers_Adapter2.ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.washers_layout2, parent, false);
        return new Washers_Adapter2.ViewHold(view);
    }

    public void onBindViewHolder(Washers_Adapter2.ViewHold holder, int position){
        Accessories_ModelClass ss=dataList.get(position);
        holder.manufacturer.setText(ss.getManufacturer());
        holder.desc.setText(ss.getModel()+ss.getPowerOutput());
        holder.price.setText(ss.getPrice());
        Glide.with(context).load(ss.getImage()).into(holder.productImg);
        String model= ss.getModel();
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), fulldetails_Washers.class);
                i.putExtra("key",model);
                context.startActivity(i);
            }
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
        CardView cardView;
        TextView manufacturer,desc,price;
        public ViewHold(@NonNull View itemView) {
            super(itemView);
            manufacturer=itemView.findViewById(R.id.washersManufacturer);
            desc=itemView.findViewById(R.id.washersDesc);
            productImg=itemView.findViewById(R.id.washersImg);
            price=itemView.findViewById(R.id.washersPrice);
            cardView=itemView.findViewById(R.id.cvWashers);
        }
    }
}
