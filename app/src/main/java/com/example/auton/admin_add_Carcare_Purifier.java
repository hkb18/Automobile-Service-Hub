package com.example.auton;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class admin_add_Carcare_Purifier extends AppCompatActivity {
    Button airpurifier,cleaningkit,microfibers,wiperblades,cleansers,detailing,washers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_carcare_purifier);

        airpurifier=findViewById(R.id.btn_AirPurifiers);
        cleaningkit=findViewById(R.id.btn_CleaningKits);
        microfibers=findViewById(R.id.btn_Microfibers);
        wiperblades=findViewById(R.id.btn_WiperBlades);
        cleansers=findViewById(R.id.btn_Cleaners);
        detailing=findViewById(R.id.btn_Detailing);
        washers=findViewById(R.id.btn_Washers);

        airpurifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),admin_add_AirPurifier.class);
                startActivity(i);
            }
        });
        cleaningkit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),admin_add_CleaningKit.class);
                startActivity(i);
            }
        });
        microfibers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),admin_add_MicroFibres.class);
                startActivity(i);
            }
        });
        wiperblades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),admin_add_WiperBlades.class);
                startActivity(i);
            }
        });
        cleansers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),admin_add_Cleansers.class);
                startActivity(i);
            }
        });

        detailing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),admin_add_Detailing.class);
                startActivity(i);
            }
        });
        washers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),admin_add_Washers.class);
                startActivity(i);
            }
        });
    }
}