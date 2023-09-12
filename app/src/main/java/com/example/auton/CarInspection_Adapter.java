package com.example.auton;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CarInspection_Adapter extends RecyclerView.Adapter<CarInspection_Adapter.ViewHold> {
    private final Context context;
    private final ArrayList<CarInspection_ModelClass> dataList;


    public CarInspection_Adapter(Context fragment, ArrayList<CarInspection_ModelClass> dataList) {
        this.dataList = dataList;
        this.context = fragment;

    }

    @NonNull
    @Override
    public CarInspection_Adapter.ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.denting_painting_layout, parent, false);
        return new CarInspection_Adapter.ViewHold(view);
    }

    public void onBindViewHolder(CarInspection_Adapter.ViewHold holder, int position) {
        CarInspection_ModelClass ss = dataList.get(position);
        holder.servicename.setText(ss.getServiceName());
        holder.wtsincluded.setText(ss.getWtsIncluded());
        holder.price.setText(ss.getPrice());
        holder.buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), user_Book_Service.class);
                i.putExtra("Service", ss.getServiceName());
                i.putExtra("servicetype", "Denting and Painting");
                i.putExtra("Price", ss.getPrice());
                context.startActivity(i);
            }
        });
    }

    public void filterList(ArrayList<CarInspection_ModelClass> filteredlist) {
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
        TextView servicename, wtsincluded, price;

        public ViewHold(@NonNull View itemView) {
            super(itemView);

            wtsincluded = itemView.findViewById(R.id.dentingpaintingWtsIncluded);
            servicename = itemView.findViewById(R.id.dentingpaintingServiceName);
            price = itemView.findViewById(R.id.dentingpaintingPrice);
            buynow = itemView.findViewById(R.id.btn_dentingpaintingBuyNow);
        }
    }
}
