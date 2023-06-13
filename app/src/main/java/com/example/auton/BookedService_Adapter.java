package com.example.auton;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/*
public class BookedService_Adapter extends RecyclerView.Adapter<BookedService_Adapter.MyViewHolder> {
    Context context;
    ArrayList<BookedService> list;
    public BookedService_Adapter(Context context, ArrayList<BookedService> list) {
        this.context = context;
        this.list = list;
    }
    public BookedService_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.booked_service_layout,parent,false);
        return new BookedService_Adapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,int position) {
        Log.e("TAG", "onBindViewHolder: "+list);
        BookedService bookedService=list.get(position);
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
        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_View_Booked_Service.viewBookedService_interface.view(key,position);

                Toast.makeText(context, "KEY: "+key, Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void filterList(ArrayList<BookedService> filteredlist) {
        // below line is to add our filtered
        // list in our course array list.
        list = filteredlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    public int getItemCount() {

        return list.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView carBrand,carModel,serviceType,date,latitude,longitude,servicetime,paymentMethod,username;
        Button btn_edit;
        public MyViewHolder(@NonNull View itemView) {
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
            btn_edit= itemView.findViewById(R.id.btn_Edit);

        }
    }
}
*/


public class BookedService_Adapter extends RecyclerView.Adapter<BookedService_Adapter.ViewHold> {
    ArrayList<BookedService> list;
    Context context;
    public BookedService_Adapter(Context context, ArrayList<BookedService> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public BookedService_Adapter.ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booked_service_layout, parent, false);
        return new BookedService_Adapter.ViewHold(view);
    }

    public void onBindViewHolder(@NonNull BookedService_Adapter.ViewHold holder, int position){
        Log.e("TAG", "onBindViewHolder: "+list );
        BookedService bookedService=list.get(position);
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
        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_View_Booked_Service.viewBookedService_interface.view(key,position);

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
        Button btn_edit;


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
            btn_edit= itemView.findViewById(R.id.btn_Edit);
        }
    }
}
