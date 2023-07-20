package com.example.auton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.auton.databinding.ActivityAdminAddBatteriesBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class admin_add_Batteries extends AppCompatActivity {
    ProgressDialog progressDialog;
    ProgressBar progressBar;
    StorageReference storageReference;
    Uri imageUri;
    String fileName;
    DatabaseReference databaseReference;
    String brandStr, voltageStr, warrentyyrsStr, priceStr, pk;
    private ActivityAdminAddBatteriesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminAddBatteriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressBar = new ProgressBar(this);
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        binding.btnAddService.setOnClickListener(view -> {
            brandStr = binding.brandName.getText().toString();
            voltageStr = binding.voltage.getText().toString();
            warrentyyrsStr = binding.warrenty.getText().toString();
            priceStr = binding.price.getText().toString();

            if (brandStr.isEmpty() || voltageStr.isEmpty() || warrentyyrsStr.isEmpty() || priceStr.isEmpty()) {
                Toast.makeText(this, "Please Enter all details", Toast.LENGTH_SHORT).show();
            } else {
                databaseReference.child("SERVICE_TYPE").child("Batteries").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        uploadImage();

                        Toast.makeText(admin_add_Batteries.this, "Value Entered Successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), admin_AddService.class);
                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(admin_add_Batteries.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        //      IMG UPLOAD
        binding.btnSelectImgBattery.setOnClickListener(v -> {
            selectImage();
        });
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/+");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null) {
            imageUri = data.getData();
            binding.ivBattery.setImageURI(imageUri);
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
                    binding.ivBattery.setImageURI(null);
                    Toast.makeText(admin_add_Batteries.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    Toast.makeText(admin_add_Batteries.this, "Failed to Upload", Toast.LENGTH_SHORT).show();

                }
            });
            uploadtoFirebase(imageUri);
        } else {
            Toast.makeText(this, "Please select Image", Toast.LENGTH_SHORT).show();
        }

    }

    private void uploadtoFirebase(Uri uri) {
        storageReference = storageReference.child("images/").child(fileName);
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        pk = databaseReference.push().getKey();
                        databaseReference.child("SERVICE_TYPE").child("Batteries").child(pk).child("Image").setValue(uri.toString());
                        databaseReference.child("SERVICE_TYPE").child("Batteries").child(pk).child("Brand").setValue(brandStr);
                        databaseReference.child("SERVICE_TYPE").child("Batteries").child(pk).child("Voltage").setValue(voltageStr);
                        databaseReference.child("SERVICE_TYPE").child("Batteries").child(pk).child("YrsofWarrenty").setValue(warrentyyrsStr);
                        databaseReference.child("SERVICE_TYPE").child("Batteries").child(pk).child("Price").setValue(priceStr);
                        databaseReference.child("SERVICE_TYPE").child("Batteries").child(pk).child("Key").setValue(pk);

                        Toast.makeText(admin_add_Batteries.this, "Uploaded Successfully ", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(admin_add_Batteries.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}