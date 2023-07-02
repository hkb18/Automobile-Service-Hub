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

import com.example.auton.databinding.ActivityAdminAddHornsBinding;
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

public class admin_add_Horns extends AppCompatActivity {
    private ActivityAdminAddHornsBinding binding;
    ProgressDialog progressDialog;
    ProgressBar progressBar;
    StorageReference storageReference;
    Uri imageUri;
    String fileName;
    DatabaseReference databaseReference;
    String dimensionStr,materialtypeStr,colorStr,weightStr,manufacturerStr,modelStr,voltageStr,noiselevelStr,fittypeStr,featureStr,priceStr,quantityStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminAddHornsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressBar = new ProgressBar(this);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        binding.btnAddHorns.setOnClickListener(view -> {

            modelStr=binding.hornsModel.getText().toString();
            dimensionStr=binding.hornsDimension.getText().toString();
            weightStr=binding.hornsWeight.getText().toString();
            colorStr=binding.hornsColor.getText().toString();
            materialtypeStr=binding.hornsMaterialType.getText().toString();
            manufacturerStr=binding.hornsManufacturer.getText().toString();
            fittypeStr=binding.hornsFitType.getText().toString();
            featureStr=binding.hornsFeature.getText().toString();
            voltageStr=binding.hornsVoltage.getText().toString();
            noiselevelStr=binding.hornsNoiseLevel.getText().toString();
            priceStr=binding.hornsPrice.getText().toString();
            quantityStr=binding.hornsQuantity.getText().toString();

            if(modelStr.isEmpty()||voltageStr.isEmpty() || noiselevelStr.isEmpty() ||dimensionStr.isEmpty()||fittypeStr.isEmpty()||featureStr.isEmpty()||weightStr.isEmpty()||materialtypeStr.isEmpty()|| colorStr.isEmpty() || manufacturerStr.isEmpty() ||priceStr.isEmpty() ||  quantityStr.isEmpty()) {
                Toast.makeText(admin_add_Horns.this, "Please enter all details", Toast.LENGTH_SHORT).show();
            } else {
                databaseReference.child("Accessories").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        uploadImage();

                        Toast.makeText(admin_add_Horns.this, "Value Entered", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), admin_add_HornsProtectives.class);
                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(admin_add_Horns.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //      IMG UPLOAD
        binding.btnSelectHorns.setOnClickListener(v -> {
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
            binding.ivHorns.setImageURI(imageUri);
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
                    binding.ivHorns.setImageURI(null);
                    Toast.makeText(admin_add_Horns.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    Toast.makeText(admin_add_Horns.this, "Failed to Upload", Toast.LENGTH_SHORT).show();

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
                     /*   databaseReference.child("Accessories").child("HORNS_PROTECTIVES").child("Horns").child(modelStr).child("Image").setValue(uri.toString());
                        databaseReference.child("Accessories").child("HORNS_PROTECTIVES").child("Horns").child(modelStr).child("Model").setValue(modelStr);
                        databaseReference.child("Accessories").child("HORNS_PROTECTIVES").child("Horns").child(modelStr).child("Dimension").setValue(dimensionStr);
                        databaseReference.child("Accessories").child("HORNS_PROTECTIVES").child("Horns").child(modelStr).child("Color").setValue(colorStr);
                        databaseReference.child("Accessories").child("HORNS_PROTECTIVES").child("Horns").child(modelStr).child("Weight").setValue(weightStr);
                        databaseReference.child("Accessories").child("HORNS_PROTECTIVES").child("Horns").child(modelStr).child("MaterialType").setValue(materialtypeStr);
                        databaseReference.child("Accessories").child("HORNS_PROTECTIVES").child("Horns").child(modelStr).child("Voltage").setValue(voltageStr);
                        databaseReference.child("Accessories").child("HORNS_PROTECTIVES").child("Horns").child(modelStr).child("NoiseLevel").setValue(noiselevelStr);
                        databaseReference.child("Accessories").child("HORNS_PROTECTIVES").child("Horns").child(modelStr).child("FitType").setValue(fittypeStr);
                        databaseReference.child("Accessories").child("HORNS_PROTECTIVES").child("Horns").child(modelStr).child("Feature").setValue(featureStr);
                        databaseReference.child("Accessories").child("HORNS_PROTECTIVES").child("Horns").child(modelStr).child("Manufacturer").setValue(manufacturerStr);
                        databaseReference.child("Accessories").child("HORNS_PROTECTIVES").child("Horns").child(modelStr).child("Price").setValue(priceStr);
                        databaseReference.child("Accessories").child("HORNS_PROTECTIVES").child("Horns").child(modelStr).child("Quantity").setValue(quantityStr);
                       */
                        Accessories_ModelClass modelClass=new Accessories_ModelClass();
                        modelClass.setBoxIncluded("");
                        // modelClass.setBoxIncludes();
                        modelClass.setBrand("");
                        modelClass.setBulbType("");
                        modelClass.setColor(colorStr);
                        modelClass.setChannel("");
                        modelClass.setCategory("");
                        modelClass.setDesign("");
                        modelClass.setDimension(dimensionStr);
                        // modelClass.setDimenension();
                        modelClass.setDuration("");
                        modelClass.setDiameter("");
                        modelClass.setDisplayType("");
                        modelClass.setFrequency("");
                        modelClass.setFragrence("");
                        modelClass.setFeature(featureStr);
                        modelClass.setFabricType("");
                        modelClass.setFitType(fittypeStr);
                        modelClass.setHoseLength("");
                        modelClass.setImage(uri.toString());
                        modelClass.setItemForm("");
                        modelClass.setItemsIncluded("");
                        // modelClass.setItemIncluded();
                        modelClass.setKey("");
                        modelClass.setLumens("");
                        modelClass.setManufacturer(manufacturerStr);
                        modelClass.setModel(modelStr);
                        modelClass.setMaxVoltage("");
                        modelClass.setMountingHardware("");
                        modelClass.setMaterial("");
                        modelClass.setMaterialType(materialtypeStr);
                        modelClass.setMaxPressure("");
                        modelClass.setNoiseLevel(noiselevelStr);
                        modelClass.setOperatingVoltage("");
                        modelClass.setOSType("");
                        modelClass.setPowerOutput("");
                        modelClass.setPrice(priceStr);
                        modelClass.setPosition("");
                        modelClass.setPattern("");
                        modelClass.setQuantity(quantityStr);
                        modelClass.setQuality("");
                        modelClass.setRAM("");
                        modelClass.setROM("");
                        modelClass.setSalientFeature("");
                        modelClass.setSensitivity("");
                        modelClass.setSpeakerType("");
                        modelClass.setScreenSize("");
                        modelClass.setVolume("");
                        modelClass.setVoltage(voltageStr);
                        modelClass.setWeight(weightStr);
                        modelClass.setWarrenty("");
                        modelClass.setWattage("");
                        databaseReference.child("Accessories").child("HORNS_PROTECTIVES").child("Horns").child(modelStr).setValue(modelClass);


                        Toast.makeText(admin_add_Horns.this, "Uploaded Successfully ", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(admin_add_Horns.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}