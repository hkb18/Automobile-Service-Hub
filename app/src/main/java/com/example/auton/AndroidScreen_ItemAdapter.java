package com.example.auton;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AndroidScreen_ItemAdapter extends RecyclerView.Adapter<AndroidScreen_ItemAdapter.ViewHold> {
    private ArrayList<ScreensSpeakers_ModelClass> dataList;
    private final Context context;
    public AndroidScreen_ItemAdapter(Context fragment, ArrayList<ScreensSpeakers_ModelClass> dataList) {
        this.dataList = dataList;
        this.context = fragment;
    }
    @NonNull
    @Override
    public AndroidScreen_ItemAdapter.ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.androidscreen_layout, parent, false);
        return new AndroidScreen_ItemAdapter.ViewHold(view);
    }

    public void onBindViewHolder(AndroidScreen_ItemAdapter.ViewHold holder, int position){
        ScreensSpeakers_ModelClass ss=dataList.get(position);
        holder.manufacturer.setText(ss.getManufacturer());
        holder.desc.setText(ss.getScreenSize()+ss.getDisplayType());
        holder.price.setText(ss.getPrice());
        Glide.with(context).load(ss.getImage()).into(holder.productImg);
        String model= ss.getModel();
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context.getApplicationContext(), fulldetails_AndroidScreen.class);
             //   fulldetails_AndroidScreen.androidScreen_interface.details(model);
                i.putExtra("key",model);
                context.startActivity(i);
            }
        });
    }

    public void filterList(ArrayList<ScreensSpeakers_ModelClass> filteredlist) {
        // below line is to add our filtered
        // list in our course array list.
        //dataList = filteredlist;
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
        CardView cardView;
        TextView manufacturer,desc,price;
        public ViewHold(@NonNull View itemView) {
            super(itemView);
            manufacturer=itemView.findViewById(R.id.tvManufacturer);
            desc=itemView.findViewById(R.id.tvDesc);
            productImg=itemView.findViewById(R.id.itemImage);
            price=itemView.findViewById(R.id.tvPrice);
            cardView=itemView.findViewById(R.id.cvAndroidScreen);
        }
    }
}
