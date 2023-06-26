package com.example.auton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TyresWheelcare_Adapter extends RecyclerView.Adapter<TyresWheelcare_Adapter.ViewHold> {
    private ArrayList<TyresWheelcare_ModelClass> dataList;
    private final Context context;


    public TyresWheelcare_Adapter(Context fragment, ArrayList<TyresWheelcare_ModelClass> dataList) {
        this.dataList = dataList;
        this.context = fragment;

    }
    @NonNull
    @Override
    public TyresWheelcare_Adapter.ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.periodicservice_layout, parent, false);
        return new TyresWheelcare_Adapter.ViewHold(view);
    }

    public void onBindViewHolder(TyresWheelcare_Adapter.ViewHold holder, int position){
        TyresWheelcare_ModelClass ss=dataList.get(position);
        /*holder.name.setText(ss.getServiceName());
        holder.performanceService.setText(ss.getPerformanceServices());
        holder.essentialService.setText(ss.getEssentialServices());
        holder.additionalService.setText(ss.getAdditionalServices());
        holder.price.setText(ss.getPrice());
        holder.buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(), user_Book_Service.class);
                i.putExtra("Service",ss.getServiceName());
                context.startActivity(i);
            }
        });*/
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
        TextView name,additionalService,essentialService,performanceService,price;
        public ViewHold(@NonNull View itemView) {
            super(itemView);
            /*name=itemView.findViewById(R.id.acservicerepairName);
            additionalService=itemView.findViewById(R.id.acservicerepairAdditionalService);
            essentialService=itemView.findViewById(R.id.acservicerepairEssentialService);
            performanceService=itemView.findViewById(R.id.acservicerepairPerformanceService);
            price=itemView.findViewById(R.id.acservicerepairPrice);
            buynow=itemView.findViewById(R.id.btn_acservicerepairBuyNow);
            cardView=itemView.findViewById(R.id.cvAcServiceRepair);*/
        }
    }
}
