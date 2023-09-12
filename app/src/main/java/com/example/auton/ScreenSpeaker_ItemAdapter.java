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

public class ScreenSpeaker_ItemAdapter extends RecyclerView.Adapter<ScreenSpeaker_ItemAdapter.ViewHold> {
    private final Context context;
    private ArrayList<Accessories_ModelClass> dataList;
    private final String name;


    public ScreenSpeaker_ItemAdapter(Context fragment, ArrayList<Accessories_ModelClass> dataList, String key) {
        this.dataList = dataList;
        this.context = fragment;
        name = key;

    }

    @NonNull
    @Override
    public ScreenSpeaker_ItemAdapter.ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.screen_speaker_items_layout, parent, false);
        return new ScreenSpeaker_ItemAdapter.ViewHold(view);
    }

    public void onBindViewHolder(ScreenSpeaker_ItemAdapter.ViewHold holder, int position) {

        holder.textView.setText(name);
        holder.recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        holder.recyclerView.setAdapter(new AndroidScreen_ItemAdapter(context, dataList));
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
