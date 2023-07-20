package com.example.auton;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.auton.databinding.ActivityAdminAddRoadsideAssistanceBinding;

public class admin_add_RoadsideAssistance extends AppCompatActivity {
    private ActivityAdminAddRoadsideAssistanceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminAddRoadsideAssistanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnToolkits.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), admin_add_Toolkits.class);
            startActivity(i);
        });
        binding.btnTyreInfators.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), admin_add_TyreInflator.class);
            startActivity(i);
        });
    }
}