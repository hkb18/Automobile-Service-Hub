package com.example.auton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.auton.databinding.ActivityAdminAddToolkitsBinding;
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

public class admin_add_Toolkits extends AppCompatActivity {
    private ActivityAdminAddToolkitsBinding binding;
    ProgressDialog progressDialog;
    ProgressBar progressBar;
    StorageReference storageReference;
    Uri imageUri;
    String fileName;
    DatabaseReference databaseReference;
    String modelStr,colorStr,dimensionStr,weightStr,itemincludedStr,brandStr,priceStr,quantityStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminAddToolkitsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressBar = new ProgressBar(this);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        binding.btnAddToolkits.setOnClickListener(view -> {

            modelStr=binding.toolkitsModel.getText().toString();
            dimensionStr=binding.toolkitsDimension.getText().toString();
            weightStr=binding.toolkitsWeight.getText().toString();
            colorStr=binding.toolkitsColor.getText().toString();
            itemincludedStr=binding.toolkitsItemIncluded.getText().toString();
            brandStr=binding.toolkitsBrand.getText().toString();
            priceStr=binding.toolkitsPrice.getText().toString();
            quantityStr=binding.toolkitsQuantity.getText().toString();

            if(modelStr.isEmpty()||dimensionStr.isEmpty()||weightStr.isEmpty()||brandStr.isEmpty()|| colorStr.isEmpty() || itemincludedStr.isEmpty() ||priceStr.isEmpty() ||  quantityStr.isEmpty()) {
                Toast.makeText(admin_add_Toolkits.this, "Please enter all details", Toast.LENGTH_SHORT).show();
            } else {
                databaseReference.child("Accessories").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        uploadImage();

                        Toast.makeText(admin_add_Toolkits.this, "Value Entered", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), admin_add_RoadsideAssistance.class);
                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(admin_add_Toolkits.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //      IMG UPLOAD
        binding.btnSelectToolkits.setOnClickListener(v -> {
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
            binding.ivToolkits.setImageURI(imageUri);
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
                    binding.ivToolkits.setImageURI(null);
                    Toast.makeText(admin_add_Toolkits.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    Toast.makeText(admin_add_Toolkits.this, "Failed to Upload", Toast.LENGTH_SHORT).show();

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
                        databaseReference.child("Accessories").child("ROADSIDE_ASSISTANCE").child("Toolkits").child(modelStr).child("Image").setValue(uri.toString());
                        databaseReference.child("Accessories").child("ROADSIDE_ASSISTANCE").child("Toolkits").child(modelStr).child("Model").setValue(modelStr);
                        databaseReference.child("Accessories").child("ROADSIDE_ASSISTANCE").child("Toolkits").child(modelStr).child("Dimension").setValue(dimensionStr);
                        databaseReference.child("Accessories").child("ROADSIDE_ASSISTANCE").child("Toolkits").child(modelStr).child("Color").setValue(colorStr);
                        databaseReference.child("Accessories").child("ROADSIDE_ASSISTANCE").child("Toolkits").child(modelStr).child("Weight").setValue(weightStr);
                        databaseReference.child("Accessories").child("ROADSIDE_ASSISTANCE").child("Toolkits").child(modelStr).child("ItemIncluded").setValue(itemincludedStr);
                        databaseReference.child("Accessories").child("ROADSIDE_ASSISTANCE").child("Toolkits").child(modelStr).child("Brand").setValue(brandStr);
                        databaseReference.child("Accessories").child("ROADSIDE_ASSISTANCE").child("Toolkits").child(modelStr).child("Price").setValue(priceStr);
                        databaseReference.child("Accessories").child("ROADSIDE_ASSISTANCE").child("Toolkits").child(modelStr).child("Quantity").setValue(quantityStr);
                        Log.e("TAG", "onSuccess: "+modelStr+dimensionStr+colorStr+weightStr+itemincludedStr+brandStr+priceStr+quantityStr);
                        Toast.makeText(admin_add_Toolkits.this, "Uploaded Successfully ", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(admin_add_Toolkits.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}