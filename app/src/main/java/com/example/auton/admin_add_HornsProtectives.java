package com.example.auton;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.auton.databinding.ActivityAdminAddHornsProtectivesBinding;

public class admin_add_HornsProtectives extends AppCompatActivity {
    private ActivityAdminAddHornsProtectivesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminAddHornsProtectivesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnHorns.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), admin_add_Horns.class);
            startActivity(i);
        });

        binding.btnProtectives.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), admin_add_Protectives.class);
            startActivity(i);
        });
    }
}