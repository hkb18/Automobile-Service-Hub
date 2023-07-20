package com.example.auton;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.auton.databinding.ActivityMechanicHomePageBinding;

public class mechanic_HomePage extends AppCompatActivity {
    private ActivityMechanicHomePageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMechanicHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toggleSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {

            } else {

            }
        });
    }
}