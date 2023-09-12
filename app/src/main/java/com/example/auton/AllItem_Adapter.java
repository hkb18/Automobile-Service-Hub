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

public class AllItem_Adapter extends RecyclerView.Adapter<AllItem_Adapter.ViewHold> {
    private final Context context;
    private final ArrayList<Amplifier_ModelClass> dataList;


    public AllItem_Adapter(Context fragment, ArrayList<Amplifier_ModelClass> dataList) {
        this.dataList = dataList;
        this.context = fragment;

    }

    @NonNull
    @Override
    public AllItem_Adapter.ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.amplifier_layout_2, parent, false);
        return new AllItem_Adapter.ViewHold(view);
    }

    public void onBindViewHolder(AllItem_Adapter.ViewHold holder, int position) {
        Amplifier_ModelClass ss = dataList.get(position);
        holder.manufacturer.setText(ss.getManufacturer());
        holder.desc.setText(ss.getModel());
        holder.price.setText(ss.getPrice());
        Glide.with(context).load(ss.getImage()).into(holder.productImg);
        String model = ss.getModel();
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context.getApplicationContext(), fulldetails_AndroidScreen.class);
                i.putExtra("key", model);
                context.startActivity(i);
            }
        });
    }

    public void filterList(ArrayList<ScreensSpeakers_ModelClass> filteredlist) {
        // below line is to add our filtered
        // list in our course array list.
        // dataList = filteredlist;
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
        TextView manufacturer, desc, price;

        public ViewHold(@NonNull View itemView) {
            super(itemView);
            manufacturer = itemView.findViewById(R.id.amplifierManufacturer);
            desc = itemView.findViewById(R.id.amplifierDesc);
            productImg = itemView.findViewById(R.id.amplifierImg);
            price = itemView.findViewById(R.id.amplifier_Price);
            cardView = itemView.findViewById(R.id.cvAmplifier);
        }
    }
}
