package com.example.auton;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class Workshop_BookedService_Adapter extends RecyclerView.Adapter<Workshop_BookedService_Adapter.ViewHold> {
    ArrayList<Worshop_View_Service_modelClass> list;
    Context context;
    public Workshop_BookedService_Adapter(Context context, ArrayList<Worshop_View_Service_modelClass> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public Workshop_BookedService_Adapter.ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_booked_service_layout, parent, false);
        return new Workshop_BookedService_Adapter.ViewHold(view);
    }

    public void onBindViewHolder(@NonNull Workshop_BookedService_Adapter.ViewHold holder, int position){
        Log.e("TAG", "onBindViewHolder: "+list );
        Worshop_View_Service_modelClass bookedService=list.get(position);
        holder.carBrand.setText(bookedService.getCarBrand());
        holder.carModel.setText(bookedService.getCarModel());
        holder.serviceType.setText(bookedService.getServiceType());
        holder.username.setText(bookedService.getUsername());
        holder.date.setText(bookedService.getDate());
        holder.latitude.setText(bookedService.getLatitude());
        holder.longitude.setText(bookedService.getLongitude());
        holder.servicetime.setText(bookedService.getServiceTime());
        holder.paymentMethod.setText(bookedService.getPaymentMode());

        String key=bookedService.getSYSTIME();
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workshop_Dashboard_Fragment.viewBookedService_interface.view(key,position);

                Toast.makeText(context, "KEY: "+key, Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void filterList(ArrayList<BookedService> filteredlist) {
        // below line is to add our filtered
        // list in our course array list.

        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHold extends RecyclerView.ViewHolder {
        TextView carBrand,carModel,serviceType,date,latitude,longitude,servicetime,paymentMethod,username;
        Button accept,delete;


        public ViewHold(@NonNull View itemView) {
            super(itemView);
            username=itemView.findViewById(R.id.getUsername);
            carBrand= itemView.findViewById(R.id.getCarBrand);
            carModel= itemView.findViewById(R.id.getCarModel);
            serviceType= itemView.findViewById(R.id.getServiceType);
            date= itemView.findViewById(R.id.getDate);
            latitude= itemView.findViewById(R.id.getLatitude);
            longitude= itemView.findViewById(R.id.getLongitude);
            servicetime= itemView.findViewById(R.id.getServiceTime);
            paymentMethod= itemView.findViewById(R.id.getPaymentMode);
            accept= itemView.findViewById(R.id.btn_Accept);
            delete= itemView.findViewById(R.id.btn_Delete);
        }
    }
}
