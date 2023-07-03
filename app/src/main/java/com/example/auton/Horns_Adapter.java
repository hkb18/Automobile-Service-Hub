package com.example.auton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Horns_Adapter extends RecyclerView.Adapter<Horns_Adapter.ViewHold>{
    private ArrayList<Accessories_ModelClass> dataList;
    private final Context context;
    private String title;

    public Horns_Adapter(Context fragment, ArrayList<Accessories_ModelClass> dataList, String key) {
        this.dataList = dataList;
        this.context = fragment;
        this.title = key;

    }
    @NonNull
    @Override
    public Horns_Adapter.ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horns_layout, parent, false);
        return new Horns_Adapter.ViewHold(view);
    }

    public void onBindViewHolder(Horns_Adapter.ViewHold holder, int position){
        holder.textView.setText(title);
        holder.recyclerView.setLayoutManager(new GridLayoutManager(context,2));
        holder.recyclerView.setAdapter(new Horns_Adapter2(context,dataList));
    }

    public void filterList(ArrayList<Accessories_ModelClass> filteredlist) {
        // below line is to add our filtered
        // list in our course array list.
        dataList = filteredlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return 1;
    }
    public class ViewHold extends RecyclerView.ViewHolder {
        TextView textView;
        RecyclerView recyclerView;
        public ViewHold(@NonNull View itemView) {
            super(itemView);

            recyclerView=itemView.findViewById(R.id.rv);
            textView=itemView.findViewById(R.id.tvTitle);

        }
    }
}
