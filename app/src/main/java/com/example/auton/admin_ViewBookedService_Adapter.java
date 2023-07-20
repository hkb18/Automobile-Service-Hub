package com.example.auton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class admin_ViewBookedService_Adapter extends RecyclerView.Adapter<admin_ViewBookedService_Adapter.ViewHold> {
    ArrayList<Worshop_View_Service_modelClass> list;
    Context context;

    public admin_ViewBookedService_Adapter(Context context, ArrayList<Worshop_View_Service_modelClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public admin_ViewBookedService_Adapter.ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booked_service_layout2, parent, false);
        return new admin_ViewBookedService_Adapter.ViewHold(view);
    }

    public void onBindViewHolder(@NonNull admin_ViewBookedService_Adapter.ViewHold holder, int position) {
        Worshop_View_Service_modelClass bookedService = list.get(position);
        holder.carBrand.setText(bookedService.getCarBrand());
        holder.carModel.setText(bookedService.getCarModel());
        holder.serviceType.setText(bookedService.getServiceType());
        holder.serviceName.setText(bookedService.getServiceName());
        holder.customer.setText(bookedService.getUsername());
        holder.date.setText(bookedService.getDate());
        holder.latitude.setText(bookedService.getLatitude());
        holder.longitude.setText(bookedService.getLongitude());
        holder.servicetime.setText(bookedService.getServiceTime());
        holder.workshop.setText(bookedService.getWorkshop());
        holder.mechanic.setText(bookedService.getAssignedMechanic());

        if (bookedService.getWorkshop() == null || bookedService.getAssignedMechanic() == null) {
            holder.llMechanic.setVisibility(View.GONE);
            holder.llWorkshop.setVisibility(View.GONE);
            holder.tv.setVisibility(View.VISIBLE);
        }
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
        TextView carBrand, carModel, serviceType, date, latitude, longitude, servicetime, serviceName, customer, workshop, mechanic, tv;
        LinearLayout llWorkshop, llMechanic;

        public ViewHold(@NonNull View itemView) {
            super(itemView);
            customer = itemView.findViewById(R.id.getUsername);
            carBrand = itemView.findViewById(R.id.getCarBrand);
            carModel = itemView.findViewById(R.id.getCarModel);
            serviceType = itemView.findViewById(R.id.getServiceType);
            date = itemView.findViewById(R.id.getDate);
            latitude = itemView.findViewById(R.id.getLatitude);
            longitude = itemView.findViewById(R.id.getLongitude);
            servicetime = itemView.findViewById(R.id.getServiceTime);
            serviceName = itemView.findViewById(R.id.getServiceName);
            workshop = itemView.findViewById(R.id.getWorkshop);
            mechanic = itemView.findViewById(R.id.getMechanic);
            tv = itemView.findViewById(R.id.tv);
            llMechanic = itemView.findViewById(R.id.llMechanic);
            llWorkshop = itemView.findViewById(R.id.llWorkshop);
        }
    }
}
