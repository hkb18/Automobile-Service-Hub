package com.example.auton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class BookedService_Adapter extends RecyclerView.Adapter<BookedService_Adapter.ViewHold> {
    ArrayList<Worshop_View_Service_modelClass> list;
    Context context;

    public BookedService_Adapter(Context context, ArrayList<Worshop_View_Service_modelClass> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public BookedService_Adapter.ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booked_service_layout, parent, false);
        return new BookedService_Adapter.ViewHold(view);
    }

    public void onBindViewHolder(@NonNull BookedService_Adapter.ViewHold holder, int position) {

        Worshop_View_Service_modelClass bookedService = list.get(position);
        if (bookedService.getACCEPT_SERVICE() == 1) {
            holder.tv.setText("SERVICE ACCEPTED");
            holder.tv.setTextColor(ContextCompat.getColor(context, R.color.lightgreen));
            holder.tv.setVisibility(View.VISIBLE);
            holder.carBrand.setText(bookedService.getCarBrand());
            holder.carModel.setText(bookedService.getCarModel());
            holder.serviceType.setText(bookedService.getServiceType());
            holder.username.setText(bookedService.getUsername());
            holder.date.setText(bookedService.getDate());
            holder.latitude.setText(bookedService.getLatitude());
            holder.longitude.setText(bookedService.getLongitude());
            holder.servicetime.setText(bookedService.getServiceTime());
            holder.serviceName.setText(bookedService.getServiceName());
            holder.workshop.setText(bookedService.getWorkshop());
            holder.mechanic.setText(bookedService.getAssignedMechanic());
            holder.contactno.setText(bookedService.getContactNo());
        } else if (bookedService.getACCEPT_SERVICE() == 2) {
            holder.llworkshop.setVisibility(View.GONE);
            holder.llMechanic.setVisibility(View.GONE);
            holder.tv.setText("SERVICE REJECTED");
            holder.tv.setTextColor(ContextCompat.getColor(context, R.color.red));
            holder.tv.setVisibility(View.VISIBLE);
            holder.carBrand.setText(bookedService.getCarBrand());
            holder.carModel.setText(bookedService.getCarModel());
            holder.serviceType.setText(bookedService.getServiceType());
            holder.username.setText(bookedService.getUsername());
            holder.date.setText(bookedService.getDate());
            holder.latitude.setText(bookedService.getLatitude());
            holder.longitude.setText(bookedService.getLongitude());
            holder.servicetime.setText(bookedService.getServiceTime());
            holder.serviceName.setText(bookedService.getServiceName());
            holder.contactno.setText(bookedService.getContactNo());
        } else if (bookedService.getACCEPT_SERVICE() == 3) {
            holder.llworkshop.setVisibility(View.GONE);
            holder.llMechanic.setVisibility(View.GONE);
            holder.tv.setText("NO WORKSHOP ACCEPTED SERVICES CURRENTLY.PLEASE WAIT !!!");
            holder.tv.setTextColor(ContextCompat.getColor(context, R.color.blue));
            holder.tv.setVisibility(View.VISIBLE);
            holder.carBrand.setText(bookedService.getCarBrand());
            holder.carModel.setText(bookedService.getCarModel());
            holder.serviceType.setText(bookedService.getServiceType());
            holder.username.setText(bookedService.getUsername());
            holder.date.setText(bookedService.getDate());
            holder.latitude.setText(bookedService.getLatitude());
            holder.longitude.setText(bookedService.getLongitude());
            holder.servicetime.setText(bookedService.getServiceTime());
            holder.serviceName.setText(bookedService.getServiceName());
            holder.contactno.setText(bookedService.getContactNo());
        } else {
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
        TextView carBrand, carModel, serviceType, date, latitude, longitude, servicetime, serviceName, username, tv, workshop, mechanic, contactno;
        LinearLayout llworkshop, llMechanic;

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
            workshop = itemView.findViewById(R.id.getWorkshop);
            mechanic = itemView.findViewById(R.id.getMechanic);
            contactno = itemView.findViewById(R.id.getContactNo);
            tv = itemView.findViewById(R.id.tv);
            llworkshop = itemView.findViewById(R.id.llWorkshop);
            llMechanic = itemView.findViewById(R.id.llMechanic);

        }
    }
}
