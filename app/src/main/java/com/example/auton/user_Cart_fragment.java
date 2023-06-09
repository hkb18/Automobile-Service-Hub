package com.example.auton;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.auton.databinding.FragmentUserCartBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link user_Cart_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class user_Cart_fragment extends Fragment implements OnClickInterface{
    private Cart_Adapter cart_adapter;
    ArrayList<cart_ModelClass> list=new ArrayList<>();
    private FragmentUserCartBinding binding;
    DatabaseReference databaseReference;
    SharedPreferences sh;
    String s1;
    public static OnClickInterface onClickInterface;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public user_Cart_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment user_Cart_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static user_Cart_fragment newInstance(String param1, String param2) {
        user_Cart_fragment fragment = new user_Cart_fragment();
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
        binding=FragmentUserCartBinding.inflate(getLayoutInflater());

        onClickInterface=this;

        sh=requireContext().getSharedPreferences("MySharedPreferences",MODE_PRIVATE); // to store data for temp time
        s1=sh.getString("Username","");

        binding.rvCart.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        cart_adapter=new Cart_Adapter(requireActivity(),list);
        binding.rvCart.setAdapter(cart_adapter);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        databaseReference.child("CART").child(s1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    list.add(dataSnapshot.getValue(cart_ModelClass.class));
                    cart_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.btnCheckout.setOnClickListener(view -> {
           if(list.size() != 0){
               int totalPrice = 0;
               for(int i=0; i<list.size(); i++){
                   totalPrice = totalPrice+ (Integer.parseInt(list.get(i).getPrice()) * Integer.parseInt(list.get(i).getQuantity()));
               }

               Intent i = new Intent(requireContext(), RazorPay.class);
               i.putExtra("price", String.valueOf(totalPrice));
               i.putExtra("activity", "cart");
               startActivity(i);
           } else {
               Toast.makeText(requireContext(), "Cart is empty", Toast.LENGTH_SHORT).show();
           }

        });

        return binding.getRoot();

    }

    @Override
    public void delmech(String delName, int position) {
        databaseReference.child("CART").child(s1).child(delName).removeValue();
        cart_adapter.notifyDataSetChanged();
    }

    @Override
    public void add(String quantity, int position) {

        Integer qty=Integer.parseInt(quantity);
        Integer totalQty=Integer.parseInt(list.get(position).getTotalQty());
        qty++;
        if(qty> totalQty){
            Toast.makeText(requireContext(), "Not enough products", Toast.LENGTH_SHORT).show();
            qty--;
        }
        list.get(position).setQuantity(""+qty);
        cart_adapter.notifyDataSetChanged();
    }

    @Override
    public void remove(String quantity, int position) {
        Integer qty=Integer.parseInt(quantity);
        qty--;
        if (qty<=1){
            qty=1;
        }
        list.get(position).setQuantity(""+qty);
        cart_adapter.notifyDataSetChanged();
    }
}