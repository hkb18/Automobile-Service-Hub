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
    Button add,selectImg;
    ProgressDialog progressDialog;
    ProgressBar progressBar;
    ImageView imageView;
    StorageReference storageReference;
    Uri imageUri;
    String fileName;
    TextInputEditText textInputEditTextModel,textInputEditTextDimension,textInputEditTextMaterialType,textInputEditTextFabricType,textInputEditTextQuantity,textInputEditTextColor,textInputEditTextBrand,textInputEditTextPrice;
    DatabaseReference databaseReference;
    String dimensionStr,materialTypeStr,fabricTypeStr,quantityStr,priceStr,colorStr,brandStr,modelStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_micro_fibres);

        progressBar = new ProgressBar(this);
        imageView=findViewById(R.id.ivMicroFibres);
        selectImg=findViewById(R.id.btn_selectImgMicroFibres);

        add=findViewById(R.id.btn_add_MicroFibres);
        textInputEditTextModel=findViewById(R.id.microfibresModel);
        textInputEditTextDimension=findViewById(R.id.microfibresDimensions);
        textInputEditTextMaterialType=findViewById(R.id.microfibresMaterialType);
        textInputEditTextFabricType=findViewById(R.id.microfibresFabricType);
        textInputEditTextQuantity=findViewById(R.id.microfibresQuantity);
        textInputEditTextColor=findViewById(R.id.microfibresColor);
        textInputEditTextBrand=findViewById(R.id.microfibresBrand);
        textInputEditTextPrice=findViewById(R.id.microfibresPrice);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dimensionStr=textInputEditTextDimension.getText().toString();
                modelStr=textInputEditTextModel.getText().toString();
                materialTypeStr=textInputEditTextMaterialType.getText().toString();
                fabricTypeStr=textInputEditTextFabricType.getText().toString();
                quantityStr=textInputEditTextQuantity.getText().toString();
                colorStr=textInputEditTextColor.getText().toString();
                brandStr=textInputEditTextBrand.getText().toString();
                priceStr=textInputEditTextPrice.getText().toString();
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