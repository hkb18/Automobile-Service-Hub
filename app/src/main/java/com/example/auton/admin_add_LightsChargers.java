package com.example.auton;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.auton.databinding.ActivityAdminAddLightsChargersBinding;

public class admin_add_LightsChargers extends AppCompatActivity {
    private ActivityAdminAddLightsChargersBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminAddLightsChargersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnChargers.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), admin_add_Chargers.class);
            startActivity(i);
        });
        binding.btnHID.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), admin_add_HID.class);
            startActivity(i);
        });
        binding.btnProjectors.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), admin_add_Projectors.class);
            startActivity(i);
        });
        binding.btnMobileHolder.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), admin_add_MobileHolder.class);
            startActivity(i);
        });
    }
}