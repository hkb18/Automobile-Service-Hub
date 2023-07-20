package com.example.auton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Workshop_Adapter2 extends RecyclerView.Adapter<Workshop_Adapter2.MyViewHolder> {
    Context context;
    ArrayList<Workshop_ModelClass> list;

    public Workshop_Adapter2(Context context, ArrayList<Workshop_ModelClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Workshop_Adapter2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_workshop_layout, parent, false);
        return new MyViewHolder(v);
    }

    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Workshop_ModelClass workshop = list.get(position);
        holder.username.setText(workshop.getUsername());
        holder.fullName.setText(workshop.getName());
        holder.emailid.setText(workshop.getEmail_Id());
        holder.contactno.setText(workshop.getContactNo());
        String item_todel = workshop.getUsername();
        holder.button.setOnClickListener(view -> {
            admin_DeleteWorkshop.onClickInterface.delmech(item_todel, position);

            Toast.makeText(context, "Deleted " + item_todel, Toast.LENGTH_SHORT).show();
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
        Button button;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.getWorkshopName);
            username = itemView.findViewById(R.id.getUsername);
            emailid = itemView.findViewById(R.id.getEmailId);
            contactno = itemView.findViewById(R.id.getContactno);
            button = itemView.findViewById(R.id.btn_Del_Workshop);
        }
    }
}
