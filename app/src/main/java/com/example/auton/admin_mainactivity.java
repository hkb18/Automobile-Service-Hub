package com.example.auton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class admin_mainactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_mainactivity);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin2,new admin_ManageWorkshop_Fragment()).commit();
    }
}