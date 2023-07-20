package com.example.auton;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class admin_HomePage extends AppCompatActivity {
    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        Fragment selectedFragment = null;
        int itemId = item.getItemId();
        if (itemId == R.id.home) {
            selectedFragment = new admin_Home_Fragment();
        } else if (itemId == R.id.workshop) {
            selectedFragment = new admin_ManageWorkshop_Fragment();
        } else if (itemId == R.id.profile) {
            Intent i = new Intent(getApplicationContext(), admin_View_Users.class);
            startActivity(i);
        } else if (itemId == R.id.service) {
            selectedFragment = new admin_Add_Accessories();
        }
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, selectedFragment).commit();
        }
        return true;
    };
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_admin);
        imageView = findViewById(R.id.imageviewLogout);

        AlertDialog.Builder builder = new AlertDialog.Builder(admin_HomePage.this);

        imageView.setOnClickListener(view -> {
            builder.setMessage("Are you sure you want to logout?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            finishAffinity(); //ADD TO WORKSHOP N ADMIN

                            SharedPreferences loginPref;
                            SharedPreferences.Editor loginPrefEditor;
                            loginPref = getSharedPreferences("login", MODE_PRIVATE);
                            loginPrefEditor = loginPref.edit();
                            loginPrefEditor.putBoolean("isLogin", false);
                            loginPrefEditor.apply();
                            Toast.makeText(admin_HomePage.this, "You have been Logged Out", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(admin_HomePage.this, MainActivity.class);
                            startActivity(i);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            Toast.makeText(admin_HomePage.this, "You choose no action for Alertbox", Toast.LENGTH_SHORT).show();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.setTitle("User Logout");
            alert.show();

        });

        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, new admin_Home_Fragment()).commit();
    }
}