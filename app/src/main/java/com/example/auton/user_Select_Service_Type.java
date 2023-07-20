package com.example.auton;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.auton.databinding.ActivityUserSelectServiceTypeBinding;

public class user_Select_Service_Type extends AppCompatActivity {
    private ActivityUserSelectServiceTypeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserSelectServiceTypeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //PERIODIC SERVICE
        binding.periodicServices.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), user_View_PeriodicService.class);
            startActivity(i);
        });

        //AC SERVICE & REPAIR
        binding.acServiceRepair.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), user_view_ACServiceRepair.class);
            startActivity(i);
        });

        //BATTERIES
        binding.batteries.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), user_view_Batteries.class);
            startActivity(i);
        });

        //TYRES & WHEEL CARE
        binding.tyresWheelCare.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), user_view_TyresWheelcare.class);
            startActivity(i);
        });

        //DENTING & PAINTING
        binding.dentingPainting.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), user_view_DentingPainting.class);
            startActivity(i);
        });

        // DETAILING SERVICES
        binding.detailingService.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), user_view_DetailingService.class);
            startActivity(i);
        });

        //CAR SPA & CLEANING
        binding.carspaCleaning.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), user_view_CarspaCleaning.class);
            startActivity(i);
        });

        //CAR INSPECTION
        binding.carInspections.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), user_view_CarInspection.class);
            startActivity(i);
        });
    }
}