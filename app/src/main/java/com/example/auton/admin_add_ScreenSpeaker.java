package com.example.auton;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class admin_add_ScreenSpeaker extends AppCompatActivity {
    Button amplifier,vacuumcleaner,speakers,basstubes,androidscreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_screen_speaker);


        amplifier=findViewById(R.id.btn_amplifier);
        vacuumcleaner=findViewById(R.id.btn_VacuumCleaners);
        speakers=findViewById(R.id.btn_Speakers);
        basstubes=findViewById(R.id.btn_BassTubes);
        androidscreen=findViewById(R.id.btn_AndroidScreens);

        androidscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),admin_add_AndroidScreen.class);
                startActivity(i);
            }
        });

        basstubes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),admin_add_BassTubes.class);
                startActivity(i);
            }
        });

        speakers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),admin_add_Speakers.class);
                startActivity(i);
            }
        });

        vacuumcleaner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),admin_add_VacuumCleaners.class);
                startActivity(i);
            }
        });

        amplifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),admin_add_Amplifiers.class);
                startActivity(i);
            }
        });
    }
}