package com.example.auton;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link worshop_Manage_Mechanic_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class worshop_Manage_Mechanic_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView addTextView, delTextView, viewTextView;
    ImageView addImageView, delImageView, ViewImage;
    DatabaseReference databaseReference;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public worshop_Manage_Mechanic_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment worshop_Manage_Mechanic_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static worshop_Manage_Mechanic_Fragment newInstance(String param1, String param2) {
        worshop_Manage_Mechanic_Fragment fragment = new worshop_Manage_Mechanic_Fragment();
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
        View v = inflater.inflate(R.layout.fragment_worshop__manage_mechanic, container, false);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        addTextView = v.findViewById(R.id.textviewAdd_mech);
        addImageView = v.findViewById(R.id.imageviewAdd_mech);

        viewTextView = v.findViewById(R.id.textview_mech);
        ViewImage = v.findViewById(R.id.imageview_mech);

        // ADD NEW MECHANIC
        addImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), workshop_AddMechanic.class);
                startActivity(i);
            }
        });
        addTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), workshop_AddMechanic.class);
                startActivity(i);
            }
        });

        //  VIEW EXISTING MECHANIC
        viewTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), workshop_View_Mechanic.class);
                startActivity(i);
            }
        });
        ViewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), workshop_View_Mechanic.class);
                startActivity(i);
            }
        });
        return v;
    }
}