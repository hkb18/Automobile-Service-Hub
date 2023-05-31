package com.example.auton;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link admin_Add_Accessories#newInstance} factory method to
 * create an instance of this fragment.
 */
public class admin_Add_Accessories extends Fragment {

    ImageView ivscreenSpeaker,ivcarcarePurifier,ivfloormatCushion,ivlightsCharger,ivroadsideAssist;
    TextView tvscreenSpeaker,tvcarcarePurifier,tvfloormatCushion,tvlightsCharger,tvroadsideAssist;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public admin_Add_Accessories() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment admin_Add_Accessories.
     */
    // TODO: Rename and change types and number of parameters
    public static admin_Add_Accessories newInstance(String param1, String param2) {
        admin_Add_Accessories fragment = new admin_Add_Accessories();
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
        View v=inflater.inflate(R.layout.fragment_admin__add__accessories, container, false);
        ivscreenSpeaker=v.findViewById(R.id.imageviewAdd_screenSpeaker);
        ivcarcarePurifier=v.findViewById(R.id.imageviewAdd_carCare);
        ivfloormatCushion=v.findViewById(R.id.imageviewAdd_floormatCushion);
        ivlightsCharger=v.findViewById(R.id.imageviewAdd_lightsChargers);
        ivroadsideAssist=v.findViewById(R.id.imageviewAdd_roadsideAssistance);

        tvscreenSpeaker=v.findViewById(R.id.textviewAdd_screenSpeaker);
        tvcarcarePurifier=v.findViewById(R.id.textviewAdd_carCare);
        tvfloormatCushion=v.findViewById(R.id.textviewAdd_floormatCushion);
        tvlightsCharger=v.findViewById(R.id.textviewAdd_lightsChargers);
        tvroadsideAssist=v.findViewById(R.id.textviewAdd_roadsideAssistance);

        ivscreenSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), admin_add_ScreenSpeaker.class);
                startActivity(i);
            }
        });
        tvscreenSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), admin_add_ScreenSpeaker.class);
                startActivity(i);
            }
        });
        ivcarcarePurifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),admin_add_Carcare_Purifier.class);
                startActivity(i);
            }
        });
        tvcarcarePurifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),admin_add_Carcare_Purifier.class);
                startActivity(i);
            }
        });


        return v;
    }
}