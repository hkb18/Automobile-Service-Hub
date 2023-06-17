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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

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

public class admin_add_CleaningKit extends AppCompatActivity {
    Button add,selectImg;
    ProgressDialog progressDialog;
    ProgressBar progressBar;
    ImageView imageView;
    StorageReference storageReference;
    Uri imageUri;
    String fileName;
    TextInputEditText textInputEditTextModel,textInputEditTextWeight,textInputEditTextDimension,textInputEditTextBrand,textInputEditTextVolume,textInputEditTextBoxIncludes,textInputEditTextItemForm,textInputEditTextPrice,textInputEditTextQuantity;
    String weightStr,dimensionStr,brandStr,volumeStr,boxIncudeStr,itemFormStr,priceStr,quantityStr,modelStr;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_cleaning_kit);

        progressBar = new ProgressBar(this);
        imageView=findViewById(R.id.ivCleaningkit);
        selectImg=findViewById(R.id.btn_selectImgCleaningkit);

        add=findViewById(R.id.btn_add_CleaningKit);
        textInputEditTextWeight=findViewById(R.id.cleaningkitWeight);
        textInputEditTextModel=findViewById(R.id.cleaningkitModel);
        textInputEditTextDimension=findViewById(R.id.cleaningkitDimensions);
        textInputEditTextBrand=findViewById(R.id.cleaningkitBrand);
        textInputEditTextVolume=findViewById(R.id.cleaningkitVolume);
        textInputEditTextBoxIncludes=findViewById(R.id.cleaningkitBoxIncludes);
        textInputEditTextItemForm=findViewById(R.id.cleaningkitItemForm);
        textInputEditTextPrice=findViewById(R.id.cleaningkitPrice);
        textInputEditTextQuantity=findViewById(R.id.cleaningkitQuantity);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weightStr=textInputEditTextWeight.getText().toString();
                modelStr=textInputEditTextModel.getText().toString();
                dimensionStr=textInputEditTextDimension.getText().toString();
                brandStr=textInputEditTextBrand.getText().toString();
                volumeStr=textInputEditTextVolume.getText().toString();
                boxIncudeStr=textInputEditTextBoxIncludes.getText().toString();
                itemFormStr=textInputEditTextItemForm.getText().toString();
                priceStr=textInputEditTextPrice.getText().toString();
                quantityStr=textInputEditTextQuantity.getText().toString();
                if(modelStr.isEmpty()||weightStr.isEmpty()|| dimensionStr.isEmpty()||brandStr.isEmpty() ||volumeStr.isEmpty()|| boxIncudeStr.isEmpty() || itemFormStr.isEmpty() ||priceStr.isEmpty() ||  quantityStr.isEmpty()) {
                    Toast.makeText(admin_add_CleaningKit.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                } else{
                    databaseReference.child("Accessories").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            uploadImage();
                            Toast.makeText(admin_add_CleaningKit.this, "Value Entered", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), admin_add_Carcare_Purifier.class);
                            startActivity(i);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(admin_add_CleaningKit.this, "error" + error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    //      IMG UPLOAD
        selectImg.setOnClickListener(v -> {
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
            imageView.setImageURI(imageUri);
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
                    imageView.setImageURI(null);
                    Toast.makeText(admin_add_CleaningKit.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    Toast.makeText(admin_add_CleaningKit.this, "Failed to Upload", Toast.LENGTH_SHORT).show();

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
                        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("CleaningKit").child(modelStr).child("Image").setValue(uri.toString());
                        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("CleaningKit").child(modelStr).child("Weight").setValue(weightStr);
                        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("CleaningKit").child(modelStr).child("Model").setValue(modelStr);
                        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("CleaningKit").child(modelStr).child("Dimensions").setValue(dimensionStr);
                        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("CleaningKit").child(modelStr).child("Brand").setValue(brandStr);
                        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("CleaningKit").child(modelStr).child("Volume").setValue(volumeStr);
                        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("CleaningKit").child(modelStr).child("BoxIncluded").setValue(boxIncudeStr);
                        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("CleaningKit").child(modelStr).child("ItemForm").setValue(itemFormStr);
                        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("CleaningKit").child(modelStr).child("Price").setValue(priceStr);
                        databaseReference.child("Accessories").child("CARCARE_PURIFIERS").child("CleaningKit").child(modelStr).child("Quantity").setValue(quantityStr);
                        Toast.makeText(admin_add_CleaningKit.this, "Uploaded Successfully ", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(admin_add_CleaningKit.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}