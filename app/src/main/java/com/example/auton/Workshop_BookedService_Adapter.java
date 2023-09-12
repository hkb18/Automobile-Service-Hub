package com.example.auton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class Workshop_BookedService_Adapter extends RecyclerView.Adapter<Workshop_BookedService_Adapter.ViewHold> {
    ArrayList<Worshop_View_Service_modelClass> list;
    Context context;
    DatabaseReference databaseReference;

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

    public void onBindViewHolder(@NonNull Workshop_BookedService_Adapter.ViewHold holder, int position) {
        Worshop_View_Service_modelClass bookedService = list.get(position);
        holder.carBrand.setText(bookedService.getCarBrand());
        holder.carModel.setText(bookedService.getCarModel());
        holder.serviceType.setText(bookedService.getServiceType());
        holder.serviceName.setText(bookedService.getServiceName());
        holder.username.setText(bookedService.getUsername());
        holder.date.setText(bookedService.getDate());
        holder.latitude.setText(bookedService.getLatitude());
        holder.longitude.setText(bookedService.getLongitude());
        holder.servicetime.setText(bookedService.getServiceTime());
        holder.contactno.setText(bookedService.getContactNo());
        String key = bookedService.getKey();
        String username = bookedService.getUsername();
        if (bookedService.getACCEPT_SERVICE() == 3) {
            holder.ServiceReject.setVisibility(View.GONE);
            holder.linearLayout.setVisibility(View.VISIBLE);
        } else if (bookedService.getACCEPT_SERVICE() == 1) {
            holder.ServiceReject.setText("SERVICE ACCEPTED");
            holder.ServiceReject.setTextColor(ContextCompat.getColor(context, R.color.lightgreen));
            holder.linearLayout.setVisibility(View.GONE);
            holder.ServiceReject.setVisibility(View.VISIBLE);
        } else {
            holder.ServiceReject.setVisibility(View.VISIBLE);
            holder.linearLayout.setVisibility(View.GONE);
        }
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workshop_Dashboard_Fragment.viewBookedService_interface.accept(username, key, position, bookedService.getLatitude(), bookedService.getLongitude());


            }
        });
        holder.delete.setOnClickListener(v -> {
            workshop_Dashboard_Fragment.viewBookedService_interface.delete(username, key, position);


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
        TextView carBrand, carModel, serviceType, date, latitude, longitude, servicetime, serviceName, username, ServiceReject, contactno;
        Button accept, delete;
        LinearLayout linearLayout;

        public ViewHold(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.getUsername);
            carBrand = itemView.findViewById(R.id.getCarBrand);
            carModel = itemView.findViewById(R.id.getCarModel);
            serviceType = itemView.findViewById(R.id.getServiceType);
            date = itemView.findViewById(R.id.getDate);
            latitude = itemView.findViewById(R.id.getLatitude);
            longitude = itemView.findViewById(R.id.getLongitude);
            servicetime = itemView.findViewById(R.id.getServiceTime);
            serviceName = itemView.findViewById(R.id.getServiceName);
            accept = itemView.findViewById(R.id.btn_Accept);
            delete = itemView.findViewById(R.id.btn_Delete);
            ServiceReject = itemView.findViewById(R.id.tvStatus);
            linearLayout = itemView.findViewById(R.id.Linear_btn);
            contactno = itemView.findViewById(R.id.getContactNo);
        }
    }
}
