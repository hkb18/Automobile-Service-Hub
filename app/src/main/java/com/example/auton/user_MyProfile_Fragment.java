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

    Button update;
    EditText newname,newemail,newcontactno;
    TextView userprofilename,help,updatepasswd;
    ImageView imgview;
    String fullnameStr,emailStr,contactStr;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
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
       // return inflater.inflate(R.layout.fragment_user__my_profile, container, false);
        View v=inflater.inflate(R.layout.fragment_user__my_profile, container, false);
        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        sh=requireContext().getSharedPreferences("MySharedPreferences",MODE_PRIVATE); // to store data for temp time
        String s1=sh.getString("Username","");

        updatepasswd=v.findViewById(R.id.updatePwd);
        help=v.findViewById(R.id.Help);

        userprofilename=v.findViewById(R.id.userprofileName);
        userprofilename.setText(s1);

        newname=v.findViewById(R.id.newName);
        newemail=v.findViewById(R.id.newEmail);
        newcontactno=v.findViewById(R.id.newContact);

        imgview=v.findViewById(R.id.imgLogout);
        update=v.findViewById(R.id.updatebtn);



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
        updatepasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),user_Update_Password.class);
                i.putExtra("Username",s1);
                startActivity(i);
            }
        });

        //  TO UPDATE USER PROFILE
        databaseReference.child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(s1)){
                    fullnameStr=snapshot.child(s1).child("Name").getValue(String.class);
                    emailStr=snapshot.child(s1).child("EmailId").getValue(String.class);
                    contactStr=snapshot.child(s1).child("ContactNo").getValue(String.class);

                    newname.setText(fullnameStr);
                    newemail.setText(emailStr);
                    newcontactno.setText(contactStr);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //  UPDATE BUTTON
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(s1)){
                            fullnameStr=newname.getText().toString();
                            emailStr=newemail.getText().toString();
                            contactStr=newcontactno.getText().toString();

                            databaseReference.child("Profile").child(s1).child("Name").setValue(fullnameStr);
                            databaseReference.child("Profile").child(s1).child("EmailId").setValue(emailStr);
                            databaseReference.child("Profile").child(s1).child("ContactNo").setValue(contactStr);
                            Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        //  LOGOUT
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        imgview.setOnClickListener(new View.OnClickListener() {
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
        return v;


    }


}