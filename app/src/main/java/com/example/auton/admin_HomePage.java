package com.example.auton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class admin_HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav_admin);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,new admin_Add_Accessories()).commit();
    }
    private final  BottomNavigationView.OnNavigationItemSelectedListener navListener= item -> {
        Fragment selectedFragment = null;
        int itemId=item.getItemId();
        if(itemId==R.id.home){
           // selectedFragment=new admin_ManageWorkshop_Fragment();
        } else if (itemId==R.id.workshop) {
            selectedFragment = new admin_ManageWorkshop_Fragment();
        } else if (itemId==R.id.profile) {
            Intent i =new Intent(getApplicationContext(),admin_View_Users.class);
            startActivity(i);
        }else if (itemId==R.id.service) {
            selectedFragment = new admin_Add_Accessories();
        }
        if(selectedFragment!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin,selectedFragment).commit();
        }
        return true;
    };
}