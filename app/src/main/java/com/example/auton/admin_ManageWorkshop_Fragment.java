package com.example.auton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link admin_ManageWorkshop_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class admin_ManageWorkshop_Fragment extends Fragment {
    TextView addWorkshop,delWorkshop;
    ImageView addImg,delImg;
    String addworkshopStr,delworkshopStr;
    DatabaseReference databaseReference;
    SharedPreferences sh;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public admin_ManageWorkshop_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment admin_ManageWorkshop_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static admin_ManageWorkshop_Fragment newInstance(String param1, String param2) {
        admin_ManageWorkshop_Fragment fragment = new admin_ManageWorkshop_Fragment();
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
        View v=inflater.inflate(R.layout.fragment_admin__manage_workshop, container, false);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        addWorkshop=v.findViewById(R.id.admin_Add_workshop);
        delWorkshop=v.findViewById(R.id.admin_Del_workshop);
        addImg=v.findViewById(R.id.image_viewAdd);
        delImg=v.findViewById(R.id.image_viewDel);

        // ADD NEW WORKSHOP
        addWorkshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getContext(), admin_Add_Workshop.class);
                startActivity(i);
            }
        });

        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), admin_Add_Workshop.class);
                startActivity(i);
            }
        });

        //DELETE EXISTING WORKSHOP
/*
        delWorkshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),);
                startActivity(i);
            }
        });

        delImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),);
                startActivity(i);
            }
        }); */
        return v;
    }
}