package com.example.auton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Feedback_Adapter extends RecyclerView.Adapter<Feedback_Adapter.MyViewHolder> {
    Context context;
    ArrayList<Feedback_ModelClass> list;

    public Feedback_Adapter(Context context, ArrayList<Feedback_ModelClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Feedback_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_view_feedback_layout, parent, false);
        return new MyViewHolder(v);
    }

    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Feedback_ModelClass feedback = list.get(position);
        holder.username.setText(feedback.getUsername());
        holder.feedback.setText(feedback.getFeedback());


    }

    public void filterList(ArrayList<Feedback_ModelClass> filteredlist) {
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
        TextView username, feedback;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            feedback = itemView.findViewById(R.id.getFeedback);
            username = itemView.findViewById(R.id.getUsername);

        }
    }
}
