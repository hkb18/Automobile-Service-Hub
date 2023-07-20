package com.example.auton;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.auton.databinding.ActivityAdminAddCarcarePurifierBinding;

public class admin_add_Carcare_Purifier extends AppCompatActivity {
    private ActivityAdminAddCarcarePurifierBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminAddCarcarePurifierBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnAirPurifiers.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), admin_add_AirPurifier.class);
            startActivity(i);
        });
        binding.btnCleaners.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), admin_add_Cleansers.class);
            startActivity(i);
        });
        binding.btnCleaningKits.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), admin_add_CleaningKit.class);
            startActivity(i);
        });
        binding.btnMicrofibers.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), admin_add_MicroFibres.class);
            startActivity(i);
        });
        binding.btnWiperBlades.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), admin_add_WiperBlades.class);
            startActivity(i);
        });
        binding.btnDetailing.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), admin_add_Detailing.class);
            startActivity(i);
        });
        binding.btnWashers.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), admin_add_Washers.class);
            startActivity(i);
        });
    }
}