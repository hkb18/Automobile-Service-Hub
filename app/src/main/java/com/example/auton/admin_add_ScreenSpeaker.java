package com.example.auton;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.auton.databinding.ActivityAdminAddScreenSpeakerBinding;

public class admin_add_ScreenSpeaker extends AppCompatActivity {
    private ActivityAdminAddScreenSpeakerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminAddScreenSpeakerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnAmplifier.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), admin_add_Amplifiers.class);
            startActivity(i);
        });

        binding.btnAndroidScreens.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), admin_add_AndroidScreen.class);
            startActivity(i);
        });

        binding.btnBassTubes.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), admin_add_BassTubes.class);
            startActivity(i);
        });

        binding.btnSpeakers.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), admin_add_Speakers.class);
            startActivity(i);
        });

        binding.btnVacuumCleaners.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), admin_add_VacuumCleaners.class);
            startActivity(i);
        });

    }
}