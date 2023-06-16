package com.example.auton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Amplifier_Adapter extends RecyclerView.Adapter<Amplifier_Adapter.ViewHold>{
    private ArrayList<Amplifier_ModelClass> dataList;
    private final Context context;
    private String title;

    public Amplifier_Adapter(Context fragment, ArrayList<Amplifier_ModelClass> dataList, String key) {
        this.dataList = dataList;
        this.context = fragment;
        this.title = key;

    }
    @NonNull
    @Override
    public Amplifier_Adapter.ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.amplifier_layout, parent, false);
        return new Amplifier_Adapter.ViewHold(view);
    }

    public void onBindViewHolder(Amplifier_Adapter.ViewHold holder,int position){
        holder.textView.setText(title);
        holder.recyclerView.setLayoutManager(new GridLayoutManager(context,2));
        holder.recyclerView.setAdapter(new Amplifier_Adapter2(context,dataList));
    }

    public void filterList(ArrayList<Amplifier_ModelClass> filteredlist) {
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
            /*manufacturer=itemView.findViewById(R.id.amplifierManufacturer);
            desc=itemView.findViewById(R.id.amplifierDesc);
            productImg=itemView.findViewById(R.id.amplifierImg);
            price=itemView.findViewById(R.id.amplifier_Price);
            cardView=itemView.findViewById(R.id.cvAmplifier);*/
            recyclerView=itemView.findViewById(R.id.rv);
            textView=itemView.findViewById(R.id.tvTitle);

        }
    }
}
