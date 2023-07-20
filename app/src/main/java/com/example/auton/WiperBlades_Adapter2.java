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

public class WiperBlades_Adapter2 extends RecyclerView.Adapter<WiperBlades_Adapter2.ViewHold> {
    private final Context context;
    private ArrayList<Accessories_ModelClass> dataList;


    public WiperBlades_Adapter2(Context fragment, ArrayList<Accessories_ModelClass> dataList) {
        this.dataList = dataList;
        this.context = fragment;

    }

    @NonNull
    @Override
    public WiperBlades_Adapter2.ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wiperblades_layout2, parent, false);
        return new WiperBlades_Adapter2.ViewHold(view);
    }

    public void onBindViewHolder(WiperBlades_Adapter2.ViewHold holder, int position) {
        Accessories_ModelClass ss = dataList.get(position);
        holder.manufacturer.setText(ss.getBrand());
        holder.desc.setText(ss.getModel());
        holder.price.setText(ss.getPrice());
        Glide.with(context).load(ss.getImage()).into(holder.productImg);
        String model = ss.getModel();
        if (Integer.parseInt(ss.getQuantity()) == 0) {
            holder.tvStatus.setVisibility(View.VISIBLE);
            holder.cardView.setAlpha(0.5F);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), fulldetails_WiperBlades.class);
                i.putExtra("key", model);
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
        TextView manufacturer, desc, price, tvStatus;

        public ViewHold(@NonNull View itemView) {
            super(itemView);
            manufacturer = itemView.findViewById(R.id.wiperbladesManufacturer);
            desc = itemView.findViewById(R.id.wiperbladesDesc);
            productImg = itemView.findViewById(R.id.wiperbladesImg);
            price = itemView.findViewById(R.id.wiperbladesPrice);
            cardView = itemView.findViewById(R.id.cvWiperBlades);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}
