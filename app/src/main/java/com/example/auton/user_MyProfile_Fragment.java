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

import com.example.auton.databinding.FragmentUserMyProfileBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link user_MyProfile_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class user_MyProfile_Fragment extends Fragment {
    private FragmentUserMyProfileBinding binding;
    DatabaseReference databaseReference;
    SharedPreferences sh;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public user_MyProfile_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment user_MyProfile_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static user_MyProfile_Fragment newInstance(String param1, String param2) {
        user_MyProfile_Fragment fragment = new user_MyProfile_Fragment();
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
        binding=FragmentUserMyProfileBinding.inflate(getLayoutInflater());

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        sh=requireContext().getSharedPreferences("MySharedPreferences",MODE_PRIVATE); // to store data for temp time
        String s1=sh.getString("Username","");

        binding.btnUpdateProfile.setOnClickListener(view -> {
            Intent i = new Intent(getContext(),user_updateProfile.class);
            startActivity(i);
        });

        binding.btnMyOrders.setOnClickListener(view -> {
            Intent i = new Intent(getContext(),user_orderHistory.class);
            startActivity(i);
        });

        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        binding.btnLogout.setOnClickListener(view -> {
            builder.setMessage("Are you sure you want to logout?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            requireActivity().finishAffinity(); //ADD TO WORKSHOP N ADMIN

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

        });
       // firebaseDatabase=FirebaseDatabase.getInstance();
        //databaseReference=firebaseDatabase

        //  GOTO HELP PAGE
     /*   help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),adminprofile.class);
                i.putExtra("Username",mParam2);
                startActivity(i);
            }
        });*/

        // GOTO UPDATE PASSWORD PAGE
       /* binding.updatePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),user_Update_Password.class);
                i.putExtra("Username",s1);
                startActivity(i);
            }
        });*/

        //  TO UPDATE USER PROFILE
        /*databaseReference.child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(s1)){
                    fullnameStr=snapshot.child(s1).child("Name").getValue(String.class);
                    emailStr=snapshot.child(s1).child("EmailId").getValue(String.class);
                    contactStr=snapshot.child(s1).child("ContactNo").getValue(String.class);

                    binding.newName.setText(fullnameStr);
                    binding.newEmail.setText(emailStr);
                    binding.newContact.setText(contactStr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "Error"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/
        //  UPDATE BUTTON
        /*binding.updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(s1)){
                            fullnameStr=binding.newName.getText().toString();
                            emailStr=binding.newEmail.getText().toString();
                            contactStr=binding.newContact.getText().toString();

                            databaseReference.child("Profile").child(s1).child("Name").setValue(fullnameStr);
                            databaseReference.child("Profile").child(s1).child("EmailId").setValue(emailStr);
                            databaseReference.child("Profile").child(s1).child("ContactNo").setValue(contactStr);
                            Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(requireContext(), "Error"+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });*/

        //  LOGOUT
        //AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        binding.imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage("Are you sure you want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                requireActivity().finishAffinity(); //ADD TO WORKSHOP N ADMIN
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
        return binding.getRoot();


    }


}