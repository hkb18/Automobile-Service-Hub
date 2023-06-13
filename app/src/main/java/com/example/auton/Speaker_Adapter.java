package com.example.auton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Speaker_Adapter extends RecyclerView.Adapter<Speaker_Adapter.ViewHold>{
    private ArrayList<Speaker_ModelClass> dataList;
    private final Context context;

    public Speaker_Adapter(Context fragment, ArrayList<Speaker_ModelClass> dataList) {
        this.dataList = dataList;
        this.context = fragment;

    }
    @NonNull
    @Override
    public Speaker_Adapter.ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.speaker_layout, parent, false);
        return new Speaker_Adapter.ViewHold(view);
    }

    public void onBindViewHolder(Speaker_Adapter.ViewHold holder,int position){

        Speaker_ModelClass ss=dataList.get(position);
        holder.manufacturer.setText(ss.getManufacturer());
        holder.desc.setText(ss.getPowerOutput()+ss.getSpeakerType());
        Glide.with(context).load(ss.getImage()).into(holder.productImg);
    }

    public void filterList(ArrayList<Speaker_ModelClass> filteredlist) {
        // below line is to add our filtered
        // list in our course array list.
        dataList = filteredlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHold extends RecyclerView.ViewHolder {
        ImageView productImg;
        TextView manufacturer,desc;
        public ViewHold(@NonNull View itemView) {
            super(itemView);
            manufacturer=itemView.findViewById(R.id.speakerManufacturer);
            desc=itemView.findViewById(R.id.speakerDesc);
            productImg=itemView.findViewById(R.id.speakerImg);
        }
    }
}
