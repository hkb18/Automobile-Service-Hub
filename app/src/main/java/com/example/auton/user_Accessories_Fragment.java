package com.example.auton;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link user_Accessories_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class user_Accessories_Fragment extends Fragment {
    CardView cvScreen_Speaker,cvCarcare_Purifier,cvFloormat_Cushion,cvHorn_Protectives,cvLights_Chargers,cvRoadsideAssistance;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public user_Accessories_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment user_Accessories_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static user_Accessories_Fragment newInstance(String param1, String param2) {
        user_Accessories_Fragment fragment = new user_Accessories_Fragment();
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
        View v=inflater.inflate(R.layout.fragment_user__accessories, container, false);

        cvScreen_Speaker=v.findViewById(R.id.screens_Speakers);
        cvCarcare_Purifier=v.findViewById(R.id.carCare_Purifier);
        cvFloormat_Cushion=v.findViewById(R.id.floorMat_Cushion);
        cvHorn_Protectives=v.findViewById(R.id.horns_Protectives);
        cvLights_Chargers=v.findViewById(R.id.lights_Chargers);
        cvRoadsideAssistance=v.findViewById(R.id.roadsideAssistance);

        cvScreen_Speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), user_View_Screens_Speakers.class);
                startActivity(i);
            }
        });

        cvCarcare_Purifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cvFloormat_Cushion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cvHorn_Protectives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cvLights_Chargers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cvRoadsideAssistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return v;
    }
}