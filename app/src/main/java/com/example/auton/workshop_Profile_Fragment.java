package com.example.auton;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.auton.databinding.FragmentWorkshopProfileBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link workshop_Profile_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class workshop_Profile_Fragment extends Fragment {
    private FragmentWorkshopProfileBinding binding;
    DatabaseReference databaseReference;
    SharedPreferences sh;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public workshop_Profile_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment workshop_Profile_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static workshop_Profile_Fragment newInstance(String param1, String param2) {
        workshop_Profile_Fragment fragment = new workshop_Profile_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentWorkshopProfileBinding.inflate(getLayoutInflater());

        sh=requireContext().getSharedPreferences("MySharedPreferences",MODE_PRIVATE); // to store data for temp time
        String s1=sh.getString("Username","");

        binding.userprofileName.setText("Hi "+ s1);

        binding.btnUpdateProfile.setOnClickListener(view -> {
            Intent i = new Intent(getContext(), workshop_UpdateProfile.class);
            startActivity(i);
        });
        binding.btnFeedback.setOnClickListener(view -> {
            Intent i = new Intent(getContext(),feedback.class);
            i.putExtra("activity","workshop");
            startActivity(i);
        });

        //  LOGOUT
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage("Are you sure you want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                requireActivity().finishAffinity();

                                SharedPreferences loginPref;
                                SharedPreferences.Editor loginPrefEditor;
                                loginPref = requireActivity().getSharedPreferences("login", MODE_PRIVATE);
                                loginPrefEditor =loginPref.edit();
                                loginPrefEditor.putBoolean("isLogin", false);
                                loginPrefEditor.apply();

                                Toast.makeText(getContext(), "You have been Logged Out", Toast.LENGTH_SHORT).show();
                                Intent i =new Intent(getContext(),MainActivity.class);
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                Toast.makeText(getContext(), "You choose no action for Alertbox", Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog alert=builder.create();
                alert.setTitle("User Logout");
                alert.show();
            }
            private void finish() {
            }
        });

        return  binding.getRoot();
    }
}