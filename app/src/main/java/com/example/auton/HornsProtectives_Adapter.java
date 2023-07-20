package com.example.auton;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class HornsProtectives_Adapter extends RecyclerView.Adapter<HornsProtectives_Adapter.ViewHold> {

    private final ArrayList<materialButton> dataList;
    private final Context context;

    public HornsProtectives_Adapter(Context fragment, ArrayList<materialButton> dataList) {
        this.dataList = dataList;
        this.context = fragment;

    }

    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.screen_speaker_layout, parent, false);
        return new ViewHold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHold holder, int position) {

        holder.androidScreen.setText(dataList.get(position).getName());


        if (dataList.get(position).getSelected()) {
            holder.androidScreen.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.black));
            holder.androidScreen.setTextColor(Color.parseColor("#FFFFFF"));

        } else {
            holder.androidScreen.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.summersky));
            holder.androidScreen.setTextColor(Color.parseColor("#707070"));

        }

        holder.androidScreen.setOnClickListener(v -> {
            for (int i = 0; i < dataList.size(); i++) {
                dataList.get(i).setSelected(false);
            }
            dataList.get(position).setSelected(true);
            notifyDataSetChanged();

            user_view_HornsProtectives.hornsProtectivesInterface.onClickItem(dataList.get(position).getName());
        });

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHold extends RecyclerView.ViewHolder {
        MaterialButton androidScreen;

        public ViewHold(@NonNull View itemView) {
            super(itemView);
            androidScreen = itemView.findViewById(R.id.materialBtn);
        }
    }


}
