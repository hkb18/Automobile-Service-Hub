package com.example.auton;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class workshop_HomePage extends AppCompatActivity {

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        Fragment selectedFragment = null;
        int itemId = item.getItemId();
        if (itemId == R.id.workshopdashboard) {
            selectedFragment = new workshop_Dashboard_Fragment();
        } else if (itemId == R.id.mechanic) {
            selectedFragment = new worshop_Manage_Mechanic_Fragment();
        } else if (itemId == R.id.workshop_profile) {
            selectedFragment = new workshop_Profile_Fragment();
        }
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_workshop, selectedFragment).commit();
        }
        return true;
    };
    private ActivityResultLauncher<IntentSenderRequest> resolutionForResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_home_page);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_workshop);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_workshop, new workshop_Dashboard_Fragment()).commit();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }

        resolutionForResult = registerForActivityResult(new ActivityResultContracts.StartIntentSenderForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {

            } else {
                /* permissions not Granted */
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
            }
        });

    }

}