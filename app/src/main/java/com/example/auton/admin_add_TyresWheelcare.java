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

import com.example.auton.databinding.ActivityAdminAddTyresWheelcareBinding;
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

public class admin_add_TyresWheelcare extends AppCompatActivity {
    ProgressDialog progressDialog;
    ProgressBar progressBar;
    StorageReference storageReference;
    Uri imageUri;
    String fileName;
    DatabaseReference databaseReference;
    String brandStr, modelStr, widthStr, rimsizeStr, featureStr, priceStr, pk;
    private ActivityAdminAddTyresWheelcareBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminAddTyresWheelcareBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressBar = new ProgressBar(this);
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        binding.btnAddService.setOnClickListener(view -> {
            brandStr = binding.brandName.getText().toString();
            modelStr = binding.modelName.getText().toString();
            widthStr = binding.width.getText().toString();
            rimsizeStr = binding.rimSize.getText().toString();
            featureStr = binding.features.getText().toString();
            priceStr = binding.price.getText().toString();

            if (brandStr.isEmpty() || modelStr.isEmpty() || widthStr.isEmpty() || rimsizeStr.isEmpty() || featureStr.isEmpty() || priceStr.isEmpty()) {
                Toast.makeText(this, "Please Enter all details", Toast.LENGTH_SHORT).show();
            } else {
                databaseReference.child("SERVICE_TYPE").child("TYRES_WHEELCARE").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        uploadImage();

                        Toast.makeText(admin_add_TyresWheelcare.this, "Value Entered Successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), admin_AddService.class);
                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(admin_add_TyresWheelcare.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(admin_add_TyresWheelcare.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    Toast.makeText(admin_add_TyresWheelcare.this, "Failed to Upload", Toast.LENGTH_SHORT).show();

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
                        databaseReference.child("SERVICE_TYPE").child("TYRES_WHEELCARE").child(modelStr).child("Image").setValue(uri.toString());
                        databaseReference.child("SERVICE_TYPE").child("TYRES_WHEELCARE").child(modelStr).child("Brand").setValue(brandStr);
                        databaseReference.child("SERVICE_TYPE").child("TYRES_WHEELCARE").child(modelStr).child("Model").setValue(modelStr);
                        databaseReference.child("SERVICE_TYPE").child("TYRES_WHEELCARE").child(modelStr).child("Width").setValue(widthStr);
                        databaseReference.child("SERVICE_TYPE").child("TYRES_WHEELCARE").child(modelStr).child("RimSize").setValue(rimsizeStr);
                        databaseReference.child("SERVICE_TYPE").child("TYRES_WHEELCARE").child(modelStr).child("Features").setValue(featureStr);
                        databaseReference.child("SERVICE_TYPE").child("TYRES_WHEELCARE").child(modelStr).child("Price").setValue(priceStr);

                        Toast.makeText(admin_add_TyresWheelcare.this, "Uploaded Successfully ", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(admin_add_TyresWheelcare.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}