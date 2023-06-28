package com.example.auton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.auton.databinding.ActivityAdminAddMicroFibresBinding;
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

public class admin_add_MicroFibres extends AppCompatActivity {
    private ActivityAdminAddMicroFibresBinding binding;
    ProgressDialog progressDialog;
    ProgressBar progressBar;
    StorageReference storageReference;
    Uri imageUri;
    String fileName;
    DatabaseReference databaseReference;
    String dimensionStr,materialTypeStr,fabricTypeStr,quantityStr,priceStr,colorStr,brandStr,modelStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminAddMicroFibresBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressBar = new ProgressBar(this);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        binding.btnAddMicroFibres.setOnClickListener(view -> {
            dimensionStr=binding.microfibresDimensions.getText().toString();
            modelStr=binding.microfibresModel.getText().toString();
            materialTypeStr=binding.microfibresMaterialType.getText().toString();
            fabricTypeStr=binding.microfibresFabricType.getText().toString();
            quantityStr=binding.microfibresQuantity.getText().toString();
            colorStr=binding.microfibresColor.getText().toString();
            brandStr=binding.microfibresBrand.getText().toString();
            priceStr=binding.microfibresPrice.getText().toString();
            if(modelStr.isEmpty()||dimensionStr.isEmpty()|| materialTypeStr.isEmpty()||fabricTypeStr.isEmpty() ||quantityStr.isEmpty()|| colorStr.isEmpty() || brandStr.isEmpty() ||priceStr.isEmpty()) {
                Toast.makeText(admin_add_MicroFibres.this, "Please enter all details", Toast.LENGTH_SHORT).show();
            } else {
                databaseReference.child("Accessories").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        uploadImage();
                        Toast.makeText(admin_add_MicroFibres.this, "Value Entered", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), admin_add_Carcare_Purifier.class);
                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(admin_add_MicroFibres.this, "error" + error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        //      IMG UPLOAD
        binding.btnSelectImgMicroFibres.setOnClickListener(v -> {
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
            binding.ivMicroFibres.setImageURI(imageUri);
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
                    binding.ivMicroFibres.setImageURI(null);
                    Toast.makeText(admin_add_MicroFibres.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    Toast.makeText(admin_add_MicroFibres.this, "Failed to Upload", Toast.LENGTH_SHORT).show();

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
                        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("MicroFibres").child(modelStr).child("Image").setValue(uri.toString());
                        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("MicroFibres").child(modelStr).child("Dimension").setValue(dimensionStr);
                        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("MicroFibres").child(modelStr).child("Model").setValue(modelStr);
                        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("MicroFibres").child(modelStr).child("MaterialType").setValue(materialTypeStr);
                        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("MicroFibres").child(modelStr).child("FabricType").setValue(fabricTypeStr);
                        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("MicroFibres").child(modelStr).child("Quantity").setValue(quantityStr);
                        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("MicroFibres").child(modelStr).child("Color").setValue(colorStr);
                        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("MicroFibres").child(modelStr).child("Brand").setValue(brandStr);
                        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("MicroFibres").child(modelStr).child("Price").setValue(priceStr);

                        Toast.makeText(admin_add_MicroFibres.this, "Uploaded Successfully ", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(admin_add_MicroFibres.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}