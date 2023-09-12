package com.example.auton;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Batteries_Adapter extends RecyclerView.Adapter<Batteries_Adapter.ViewHold> {
    private final Context context;
    private final ArrayList<Batteries_ModelClass> dataList;


    public Batteries_Adapter(Context fragment, ArrayList<Batteries_ModelClass> dataList) {
        this.dataList = dataList;
        this.context = fragment;

    }

    @NonNull
    @Override
    public Batteries_Adapter.ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.batteries_layout, parent, false);
        return new Batteries_Adapter.ViewHold(view);
    }

    public void onBindViewHolder(Batteries_Adapter.ViewHold holder, int position) {
        Batteries_ModelClass ss = dataList.get(position);
        Glide.with(context).load(ss.getImage()).into(holder.imageView);
        holder.brand.setText(ss.getBrand());
        holder.warrenty.setText(ss.getYrsofWarrenty());
        holder.voltage.setText(ss.getVoltage());
        holder.price.setText(ss.getPrice());
        holder.buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), user_BookService2.class);
                i.putExtra("Service", ss.getBrand());
                i.putExtra("img", ss.getImage());
                i.putExtra("servicetype", "Batteries");
                i.putExtra("servicetype", "Batteries");
                i.putExtra("Price", ss.getPrice());
                context.startActivity(i);
            }
        });
    }

    public void filterList(ArrayList<ScreensSpeakers_ModelClass> filteredlist) {
        // below line is to add our filtered
        // list in our course array list.
        // dataList = filteredlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHold extends RecyclerView.ViewHolder {
        Button buynow;
        CardView cardView;
        ImageView imageView;
        TextView brand, voltage, warrenty, price;

        public ViewHold(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.batteriesImg);
            voltage = itemView.findViewById(R.id.batteriesVolatage);
            warrenty = itemView.findViewById(R.id.batteriesWarrenty);
            brand = itemView.findViewById(R.id.batteriesBand);
            price = itemView.findViewById(R.id.batteriesPrice);
            buynow = itemView.findViewById(R.id.btn_batteriesBuyNow);
        }
    }
}
