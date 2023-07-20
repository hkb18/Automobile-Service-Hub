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

public class MechanicAdapter extends RecyclerView.Adapter<MechanicAdapter.MyViewHolder> {
    Context context;
    ArrayList<Mechanic> list;

    public MechanicAdapter(Context context, ArrayList<Mechanic> list) {
        this.context = context;
        this.list = list;
    }

    public MechanicAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_workshop_mechanic_layout, parent, false);

        return new MyViewHolder(v);
    }

    public void onBindViewHolder(@NonNull MechanicAdapter.MyViewHolder holder, int position) {
        Mechanic mechanic = list.get(position);
        holder.username.setText(mechanic.getWorkshop());
        holder.fullName.setText(mechanic.getName());
        holder.emailid.setText(mechanic.getEmail());
        holder.contactno.setText(mechanic.getContactNo());

        String item_todel = mechanic.getEmail();
        holder.btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workshop_View_Mechanic.onClickInterface.delmech(item_todel, position);

                Toast.makeText(context, "Deleted " + item_todel, Toast.LENGTH_SHORT).show();

            }
        });
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
        TextView username, fullName, emailid, contactno;
        Button btn_del;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.getMechName);
            username = itemView.findViewById(R.id.getWorkshopUsername);
            emailid = itemView.findViewById(R.id.getMechEmailId);
            contactno = itemView.findViewById(R.id.getMechContactno);
            btn_del = itemView.findViewById(R.id.btn_Del_Mech);
        }
    }
}
