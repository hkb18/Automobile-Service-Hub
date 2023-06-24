package com.example.auton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.auton.databinding.ActivityAdminAddTyresWheelcareBinding;

public class admin_add_TyresWheelcare extends AppCompatActivity {
    private ActivityAdminAddTyresWheelcareBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminAddTyresWheelcareBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}