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

public class VacuumCleaner_Adapter extends RecyclerView.Adapter<VacuumCleaner_Adapter.ViewHold> {
    private final Context context;
    private ArrayList<Accessories_ModelClass> dataList;
    private final String name;

    public VacuumCleaner_Adapter(Context fragment, ArrayList<Accessories_ModelClass> dataList, String key) {
        this.dataList = dataList;
        this.context = fragment;
        name = key;
    }

    @NonNull
    @Override
    public VacuumCleaner_Adapter.ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.screen_speaker_items_layout, parent, false);
        return new VacuumCleaner_Adapter.ViewHold(view);
    }

    public void onBindViewHolder(VacuumCleaner_Adapter.ViewHold holder, int position) {
        holder.textView.setText(name);
        holder.recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        holder.recyclerView.setAdapter(new VacuumCleaner_Adapter_2(context, dataList));
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
            textView = itemView.findViewById(R.id.tvTitle);
            recyclerView = itemView.findViewById(R.id.rv);
        }
    }
}
