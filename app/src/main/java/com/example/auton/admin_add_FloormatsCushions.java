package com.example.auton;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.auton.databinding.ActivityAdminAddFloormatsCushionsBinding;

public class admin_add_FloormatsCushions extends AppCompatActivity {
    private ActivityAdminAddFloormatsCushionsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminAddFloormatsCushionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnAirFreshner.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(),admin_add_AirFreshner.class);
            startActivity(i);
        });
        binding.btnBackCushions.setOnClickListener(view -> {

        });
        binding.btnMats.setOnClickListener(view -> {

        });
        binding.btnNeckCushions.setOnClickListener(view -> {

        });
    }
}