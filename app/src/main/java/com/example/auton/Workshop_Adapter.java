package com.example.auton;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Workshop_Adapter extends RecyclerView.Adapter<Workshop_Adapter.MyViewHolder> {
    Context context;
    ArrayList<Workshop_ModelClass> list;

    public Workshop_Adapter(Context context, ArrayList<Workshop_ModelClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Workshop_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_admin_workshop_layout, parent, false);
        return new MyViewHolder(v);
    }

    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Workshop_ModelClass workshop = list.get(position);
        holder.username.setText(workshop.getUsername());
        holder.fullName.setText(workshop.getName());
        holder.emailid.setText(workshop.getEmail_Id());
        holder.contactno.setText(workshop.getContactNo());
        holder.cardView.setOnClickListener(view -> {
            Intent i = new Intent(context.getApplicationContext(), admin_view_Mechanics.class);
            i.putExtra("key", workshop.getUsername());
            context.startActivity(i);
        });

    }

    public void filterList(ArrayList<Workshop_ModelClass> filteredlist) {
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
        TextView username, fullName, emailid, contactno;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.getFullname);
            username = itemView.findViewById(R.id.getUsername);
            emailid = itemView.findViewById(R.id.getEmailId);
            contactno = itemView.findViewById(R.id.getContactno);
            cardView = itemView.findViewById(R.id.cvWorkshop);
        }
    }
}
