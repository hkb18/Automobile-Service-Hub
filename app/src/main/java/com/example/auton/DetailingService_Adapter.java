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

public class DetailingService_Adapter extends RecyclerView.Adapter<DetailingService_Adapter.ViewHold> {
    private final Context context;
    private final ArrayList<DetailingService_ModelClass> dataList;


    public DetailingService_Adapter(Context fragment, ArrayList<DetailingService_ModelClass> dataList) {
        this.dataList = dataList;
        this.context = fragment;

    }

    @NonNull
    @Override
    public DetailingService_Adapter.ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detailing_service_layout, parent, false);
        return new DetailingService_Adapter.ViewHold(view);
    }

    public void onBindViewHolder(DetailingService_Adapter.ViewHold holder, int position) {
        DetailingService_ModelClass ss = dataList.get(position);
        holder.servicename.setText(ss.getServiceName());
        holder.valueaddedservice.setText(ss.getValueaddingService());
        holder.additionalservice.setText(ss.getAdditionalServices());
        holder.price.setText(ss.getPrice());
        holder.buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), user_Book_Service.class);
                i.putExtra("Service", ss.getServiceName());
                i.putExtra("servicetype", "Detailing Service");
                i.putExtra("Price", ss.getPrice());
                context.startActivity(i);
            }
        });
    }

    public void filterList(ArrayList<DetailingService_ModelClass> filteredlist) {
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
        TextView servicename, additionalservice, valueaddedservice, price;

        public ViewHold(@NonNull View itemView) {
            super(itemView);

            additionalservice = itemView.findViewById(R.id.detailingAdditionalServices);
            valueaddedservice = itemView.findViewById(R.id.detailingValueAddingServices);
            servicename = itemView.findViewById(R.id.detailingServiceName);
            price = itemView.findViewById(R.id.dentingpaintingPrice);
            buynow = itemView.findViewById(R.id.btn_detailingBuyNow);
        }
    }
}
