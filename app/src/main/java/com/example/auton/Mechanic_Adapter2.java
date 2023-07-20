package com.example.auton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Mechanic_Adapter2 extends RecyclerView.Adapter<Mechanic_Adapter2.MyViewHolder> {
    Context context;
    ArrayList<Mechanic> list;

    public Mechanic_Adapter2(Context context, ArrayList<Mechanic> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Mechanic_Adapter2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_workshop_mechanic_layout2, parent, false);
        return new MyViewHolder(v);
    }

    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Mechanic user = list.get(position);
        holder.workshopName.setText(user.getWorkshop());
        holder.fullName.setText(user.getName());
        holder.emailid.setText(user.getEmail());
        holder.contactno.setText(user.getContactNo());


    }

    public void filterList(ArrayList<Mechanic> filteredlist) {
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
        TextView workshopName, fullName, emailid, contactno;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.getMechName);
            workshopName = itemView.findViewById(R.id.getWorkshopUsername);
            emailid = itemView.findViewById(R.id.getMechEmailId);
            contactno = itemView.findViewById(R.id.getMechContactno);
        }
    }
}
