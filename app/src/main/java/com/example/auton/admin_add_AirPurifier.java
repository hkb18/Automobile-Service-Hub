package com.example.auton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.example.auton.databinding.ActivityAdminAddAirPurifierBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class admin_add_AirPurifier extends AppCompatActivity {
    private ActivityAdminAddAirPurifierBinding binding;
    ProgressDialog progressDialog;
    ProgressBar progressBar;
    StorageReference storageReference;
    Uri imageUri;
    String fileName;
    DatabaseReference databaseReference;
   String modelStr,operatingVoltageStr,colorStr,dimensionStr,weightStr,manufacturerStr,warrentyStr,itemsIncludedStr,priceStr,quantityStr;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminAddAirPurifierBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressBar = new ProgressBar(this);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        binding.btnAddAirPurifier.setOnClickListener(view -> {

            modelStr=binding.airpurifierModel.getText().toString();
            operatingVoltageStr=binding.airpurifierOperatingVoltage.getText().toString();
            colorStr=binding.airpurifierColor.getText().toString();
            dimensionStr=binding.airpurifierDimensions.getText().toString();
            weightStr=binding.airpurifierWeight.getText().toString();
            manufacturerStr=binding.airpurifierManufacturer.getText().toString();
            warrentyStr=binding.airpurifierWarrenty.getText().toString();
            itemsIncludedStr=binding.airpurifierItemsIncluded.getText().toString();
            priceStr=binding.airpurifierPrice.getText().toString();
            quantityStr=binding.airpurifierQuantity.getText().toString();

            if(modelStr.isEmpty()|| operatingVoltageStr.isEmpty()||itemsIncludedStr.isEmpty() ||warrentyStr.isEmpty()|| colorStr.isEmpty() || dimensionStr.isEmpty() || weightStr.isEmpty() || weightStr.isEmpty() || manufacturerStr.isEmpty() ||priceStr.isEmpty() ||  quantityStr.isEmpty()) {
                Toast.makeText(admin_add_AirPurifier.this, "Please enter all details", Toast.LENGTH_SHORT).show();
            } else {
                databaseReference.child("Accessories").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        uploadImage();

                        Toast.makeText(admin_add_AirPurifier.this, "Value Entered", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), admin_add_Carcare_Purifier.class);
                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(admin_add_AirPurifier.this, "error" + error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    //      IMG UPLOAD
        binding.btnSelectImgAirPurifier.setOnClickListener(v -> {
            selectImage();
        });
}
    private void selectImage(){
        Intent intent=new Intent();
        intent.setType("image/+");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==100 && data != null && data.getData() != null){
            imageUri=data.getData();
            binding.ivAirpurifier.setImageURI(imageUri);
        }
    }
    private void uploadImage() {
        if (imageUri != null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading File...");
            progressDialog.show();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
            Date now = new Date();
            fileName = formatter.format(now);
            storageReference = FirebaseStorage.getInstance().getReference("images/" + fileName);

            storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    binding.ivAirpurifier.setImageURI(null);
                    Toast.makeText(admin_add_AirPurifier.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    Toast.makeText(admin_add_AirPurifier.this, "Failed to Upload", Toast.LENGTH_SHORT).show();

                }
            });
            uploadtoFirebase(imageUri);
        }
        else {
            Toast.makeText(this, "Please select Image", Toast.LENGTH_SHORT).show();
        }

    }

    private void uploadtoFirebase(Uri uri) {


        //storageReference= storageReference.child(System.currentTimeMillis()+"."+getFileExtension(uri));
        storageReference=storageReference.child("images/").child(fileName);
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("AirPurifier").child(modelStr).child("Image").setValue(uri.toString());
                        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("AirPurifier").child(modelStr).child("Model").setValue(modelStr);
                        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("AirPurifier").child(modelStr).child("OperatingVoltage").setValue(operatingVoltageStr);
                        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("AirPurifier").child(modelStr).child("Color").setValue(colorStr);
                        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("AirPurifier").child(modelStr).child("Dimenension").setValue(dimensionStr);
                        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("AirPurifier").child(modelStr).child("Weight").setValue(weightStr);
                        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("AirPurifier").child(modelStr).child("Manufacturer").setValue(manufacturerStr);
                        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("AirPurifier").child(modelStr).child("Warrenty").setValue(warrentyStr);
                        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("AirPurifier").child(modelStr).child("ItemsIncluded").setValue(itemsIncludedStr);
                        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("AirPurifier").child(modelStr).child("Price").setValue(priceStr);
                        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("AirPurifier").child(modelStr).child("Quantity").setValue(quantityStr);
                        Log.e("TAG", "onSuccess: "+manufacturerStr+modelStr+operatingVoltageStr+colorStr+dimensionStr+weightStr+warrentyStr+itemsIncludedStr+priceStr+quantityStr );
                        Toast.makeText(admin_add_AirPurifier.this, "Uploaded Successfully ", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(admin_add_AirPurifier.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}