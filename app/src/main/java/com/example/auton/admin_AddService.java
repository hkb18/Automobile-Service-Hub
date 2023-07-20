package com.example.auton;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.auton.databinding.ActivityAdminAddServiceBinding;
import com.google.firebase.database.DatabaseReference;

public class admin_AddService extends AppCompatActivity {
    DatabaseReference databaseReference;
    String serviceStr, descStr, priceStr;
    private ActivityAdminAddServiceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminAddServiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ADD PERIODIC SERVICE
        binding.btnAddPeriodicService.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), admin_add_PeriodicService.class);
            startActivity(i);
        });

        //  ADD AC SERVICE & REPAIR
        binding.btnAddAcserviceRepair.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), admin_add_ACserviceRepair.class);
            startActivity(i);
        });

        //  ADD BATTERIES
        binding.btnAddBatteries.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), admin_add_Batteries.class);
            startActivity(i);
        });

        //  ADD TYRES & WHEEL CARE
        binding.btnAddTyresWheelcare.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), admin_add_TyresWheelcare.class);
            startActivity(i);
        });

        //  ADD DENTING & PAINTING
        binding.btnAddDentingPainting.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), admin_add_DentingPainting.class);
            startActivity(i);
        });

        //  ADD DETAILING SERVICE
        binding.btnAddDetailingService.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), admin_add_DetailingService.class);
            startActivity(i);
        });

        //  ADD CAR SPA & CLEANING
        binding.btnAddCarspaCleaning.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), admin_add_CarspaCleaning.class);
            startActivity(i);
        });

        //  ADD CAR INSPECTION
        binding.btnAddCarInspection.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), admin_add_CarInspections.class);
            startActivity(i);
        });
    }
}