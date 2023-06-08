package com.example.auton;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ScreenSpeaker_ItemAdapter extends RecyclerView.Adapter<ScreenSpeaker_ItemAdapter.ViewHold> {
    private final ArrayList<ScreensSpeakers_ModelClass> dataList;
    private final Context context;
    DatabaseReference databaseReference;

    public ScreenSpeaker_ItemAdapter(Context fragment, ArrayList<ScreensSpeakers_ModelClass> dataList) {
        this.dataList = dataList;
        this.context = fragment;

    }


    @NonNull
    @Override
    public ScreenSpeaker_ItemAdapter.ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.screen_speaker_items_layout, parent, false);
        return new ScreenSpeaker_ItemAdapter.ViewHold(view);
    }

    public void onBindViewHolder(ScreenSpeaker_ItemAdapter.ViewHold holder,int position){
        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        ScreensSpeakers_ModelClass ss=dataList.get(position);
        Log.e("", "DATALIST: "+ss );
        holder.manufacturer.setText(ss.getManufacturer());
        holder.desc.setText(ss.getScreenSize());
        Glide.with(context).load(ss.getImage()).into(holder.productImg);


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
            manufacturer=itemView.findViewById(R.id.tvManufacturer);
            desc=itemView.findViewById(R.id.tvDesc);
            productImg=itemView.findViewById(R.id.itemImage);
        }
    }
}
