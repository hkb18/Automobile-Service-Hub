package com.example.auton;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.auton.databinding.ActivityAdminAddFloormatsCushionsBinding;

public class admin_add_FloormatsCushions extends AppCompatActivity {
    private ActivityAdminAddFloormatsCushionsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminAddFloormatsCushionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnAirFreshner.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), admin_add_AirFreshner.class);
            startActivity(i);
        });
        binding.btnBackCushions.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), admin_add_BackCushions.class);
            startActivity(i);
        });
        binding.btnMats.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), admin_add_Mats.class);
            startActivity(i);
        });
        binding.btnNeckCushions.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), admin_add_NeckCushions.class);
            startActivity(i);
        });
    }
}