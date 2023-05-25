package com.example.auton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class workshop_HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workshop_home_page);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_workshop);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_workshop,new workshop_Dashboard_Fragment()).commit();
    }
    private final  BottomNavigationView.OnNavigationItemSelectedListener navListener= item -> {
        Fragment selectedFragment = null;
        int itemId=item.getItemId();
        if(itemId==R.id.workshopdashboard){
            selectedFragment=new workshop_Dashboard_Fragment();
        } else if (itemId==R.id.mechanic) {
            selectedFragment = new worshop_Manage_Mechanic_Fragment();
        } else if (itemId==R.id.workshop_profile) {
            selectedFragment=new workshop_Profile_Fragment();
        }
        if(selectedFragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_workshop,selectedFragment).commit();
        }
        return true;
    };

}