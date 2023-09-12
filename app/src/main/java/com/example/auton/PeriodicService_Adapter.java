package com.example.auton;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PeriodicService_Adapter extends RecyclerView.Adapter<PeriodicService_Adapter.ViewHold> {
    private final Context context;
    private final ArrayList<PeriodicService_ModelClass> dataList;


    public PeriodicService_Adapter(Context fragment, ArrayList<PeriodicService_ModelClass> dataList) {
        this.dataList = dataList;
        this.context = fragment;

    }

    @NonNull
    @Override
    public PeriodicService_Adapter.ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.periodicservice_layout, parent, false);
        return new PeriodicService_Adapter.ViewHold(view);
    }

    public void onBindViewHolder(PeriodicService_Adapter.ViewHold holder, int position) {
        PeriodicService_ModelClass ss = dataList.get(position);
        holder.name.setText(ss.getServiceName());
        holder.performanceService.setText(ss.getPerformanceServices());
        holder.essentialService.setText(ss.getEssentialServices());
        holder.additionalService.setText(ss.getAdditionalServices());
        holder.price.setText(ss.getPrice());
        holder.buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), user_Book_Service.class);
                i.putExtra("Service", ss.getServiceName());
                i.putExtra("servicetype", "Periodic Service");
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
        TextView name, additionalService, essentialService, performanceService, price;

        public ViewHold(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.periodicservieName);
            additionalService = itemView.findViewById(R.id.peridoicserviceAdditionalService);
            essentialService = itemView.findViewById(R.id.periodicserviceEssentialService);
            performanceService = itemView.findViewById(R.id.periodicservicePerformanceService);
            price = itemView.findViewById(R.id.periodicserviePrice);
            buynow = itemView.findViewById(R.id.btn_peridocserviceBuyNow);
            cardView = itemView.findViewById(R.id.cvPeriodicService);
        }
    }
}
