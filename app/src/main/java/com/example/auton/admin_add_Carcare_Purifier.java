package com.example.auton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class admin_add_Carcare_Purifier extends AppCompatActivity {
    Button airpurifier,cleaningkit,microfibers,wiperblades,cleansers,vacuumcleaners,detailing,washers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_carcare_purifier);

        airpurifier=findViewById(R.id.btn_AirPurifiers);
        cleaningkit=findViewById(R.id.btn_CleaningKits);
        microfibers=findViewById(R.id.btn_Microfibers);
        wiperblades=findViewById(R.id.btn_WiperBlades);
        cleansers=findViewById(R.id.btn_Cleaners);
        vacuumcleaners=findViewById(R.id.btn_VacuumCleaners);
        detailing=findViewById(R.id.btn_Detailing);
        washers=findViewById(R.id.btn_Washers);

        airpurifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        cleaningkit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        microfibers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        wiperblades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        cleansers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        vacuumcleaners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        detailing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        washers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}