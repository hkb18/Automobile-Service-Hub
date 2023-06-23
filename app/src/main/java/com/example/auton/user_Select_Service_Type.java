package com.example.auton;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.auton.databinding.ActivityUserSelectServiceTypeBinding;

public class user_Select_Service_Type extends AppCompatActivity {
    private ActivityUserSelectServiceTypeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUserSelectServiceTypeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //PERIODIC SERVICE
        binding.periodicServices.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), user_Book_Service.class);
            i.putExtra("service","");
            startActivity(i);
        });

        //AC SERVICE & REPAIR
        binding.acServiceRepair.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), user_Book_Service.class);
            i.putExtra("service","");
            startActivity(i);
        });

        //BATTERIES
        binding.batteries.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), user_Book_Service.class);
            i.putExtra("service","");
            startActivity(i);
        });

        //TYRES & WHEEL CARE
        binding.tyresWheelCare.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), user_Book_Service.class);
            i.putExtra("service","");
            startActivity(i);
        });

        //DENTING & PAINTING
        binding.dentingPainting.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), user_Book_Service.class);
            i.putExtra("service","");
            startActivity(i);
        });

        // DETAILING SERVICES
        binding.detailingService.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), user_Book_Service.class);
            i.putExtra("service","");
            startActivity(i);
        });

        //CAR SPA & CLEANING
        binding.carspaCleaning.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), user_Book_Service.class);
            i.putExtra("service","");
            startActivity(i);
        });

        //CAR INSPECTION
        binding.carInspections.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), user_Book_Service.class);
            i.putExtra("service","");
            startActivity(i);
        });
    }
}