package com.example.auton;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.auton.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonSignUp.setOnClickListener(view -> {
            Intent i=new Intent(getApplicationContext(), user_Registration.class);
            startActivity(i);
        });

        binding.buttonSignIn.setOnClickListener(view -> {
            Intent i=new Intent(getApplicationContext(), user_Login.class);
            startActivity(i);
        });

        binding.buttonWorkshop.setOnClickListener(view -> {
            Intent i=new Intent(getApplicationContext(), workshop_Login.class);
            startActivity(i);
        });

    }
}