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

public class TyresWheelcare_Adapter extends RecyclerView.Adapter<TyresWheelcare_Adapter.ViewHold> {
    private final Context context;
    private final ArrayList<TyresWheelcare_ModelClass> dataList;


    public TyresWheelcare_Adapter(Context fragment, ArrayList<TyresWheelcare_ModelClass> dataList) {
        this.dataList = dataList;
        this.context = fragment;

    }

    @NonNull
    @Override
    public TyresWheelcare_Adapter.ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tyres_wheelcare_layout, parent, false);
        return new TyresWheelcare_Adapter.ViewHold(view);
    }

    public void onBindViewHolder(TyresWheelcare_Adapter.ViewHold holder, int position) {
        TyresWheelcare_ModelClass ss = dataList.get(position);
        Glide.with(context).load(ss.getImage()).into(holder.imageView);
        holder.brand.setText(ss.getBrand());
        holder.model.setText(ss.getModel());
        holder.features.setText(ss.getFeatures());
        holder.width.setText(ss.getWidth());
        holder.rimsize.setText(ss.getRimSize());
        holder.price.setText(ss.getPrice());
        holder.buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), user_BookService2.class);
                i.putExtra("Service", ss.getModel());
                i.putExtra("img", ss.getImage());
                i.putExtra("servicetype", "Tyres and Wheelcare");
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
        TextView brand, features, model, width, rimsize, price;

        public ViewHold(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.tyrewheelcareImg);
            features = itemView.findViewById(R.id.tyrewheelcareFeatures);
            width = itemView.findViewById(R.id.tyrewheelcareWidth);
            model = itemView.findViewById(R.id.tyrewheelcareModel);
            rimsize = itemView.findViewById(R.id.tyrewheelcareRimSize);
            brand = itemView.findViewById(R.id.tyrewheelcareBand);
            price = itemView.findViewById(R.id.tyrewheelcarePrice);
            buynow = itemView.findViewById(R.id.btn_tyrewheelcareBuyNow);
        }
    }
}
