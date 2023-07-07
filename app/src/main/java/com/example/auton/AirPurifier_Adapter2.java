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

public class AirPurifier_Adapter2 extends RecyclerView.Adapter<AirPurifier_Adapter2.ViewHold> {
    private ArrayList<Accessories_ModelClass> dataList;
    private final Context context;


    public AirPurifier_Adapter2(Context fragment, ArrayList<Accessories_ModelClass> dataList) {
        this.dataList = dataList;
        this.context = fragment;

    }
    @NonNull
    @Override
    public AirPurifier_Adapter2.ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.airpurifier_layout, parent, false);
        return new AirPurifier_Adapter2.ViewHold(view);
    }

    public void onBindViewHolder(AirPurifier_Adapter2.ViewHold holder, int position){
        Accessories_ModelClass ss=dataList.get(position);
        holder.manufacturer.setText(ss.getManufacturer());
        holder.desc.setText(ss.getModel()+ss.getOperatingVoltage());
        holder.price.setText(ss.getPrice());
        Glide.with(context).load(ss.getImage()).into(holder.productImg);
        String model= ss.getModel();

        if(Integer.parseInt(ss.getQuantity()) == 0){
            holder.tvStatus.setVisibility(View.VISIBLE);
            holder.cardView.setAlpha(0.5F);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), fulldetails_AirPurifier.class);
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
        TextView manufacturer,desc,price, tvStatus;
        public ViewHold(@NonNull View itemView) {
            super(itemView);
            manufacturer=itemView.findViewById(R.id.airpurifierManufacturer);
            desc=itemView.findViewById(R.id.airpurifierDesc);
            productImg=itemView.findViewById(R.id.airpurifierImg);
            price=itemView.findViewById(R.id.airpurifierPrice);
            cardView=itemView.findViewById(R.id.cvAirpurifier);
            tvStatus=itemView.findViewById(R.id.tvStatus);
        }
    }
}
