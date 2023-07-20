package com.example.auton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    Context context;
    ArrayList<User> list;

    public UserAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_admin_user_layout, parent, false);
        return new MyViewHolder(v);
    }

    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user = list.get(position);
        holder.username.setText(user.getUsername());
        holder.fullName.setText(user.getName());
        holder.emailid.setText(user.getEmailId());
        holder.contactno.setText(user.getContactNo());


    }

    public void filterList(ArrayList<User> filteredlist) {
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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.getFullname);
            username = itemView.findViewById(R.id.getUsername);
            emailid = itemView.findViewById(R.id.getEmailId);
            contactno = itemView.findViewById(R.id.getContactno);
        }
    }
}
