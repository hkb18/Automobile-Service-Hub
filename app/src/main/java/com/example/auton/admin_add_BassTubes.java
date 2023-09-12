package com.example.auton;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class admin_add_BassTubes extends AppCompatActivity {
    DatabaseReference databaseReference;
    Button addBasstubes, select;
    ProgressDialog progressDialog;
    ProgressBar progressBar;
    ImageView imageView;
    StorageReference storageReference;
    Uri imageUri;
    String fileName;
    TextInputEditText textInputEditTextModel, textInputEditTextDimension, textInputEditTextPowerOutput, textInputEditTextFrequency, textInputEditTextSalientFeature, textInputEditTextColor,
            textInputEditTextSensitivity, textInputEditTextWeight, textInputEditTextDesign, textInputEditTextManufacturer, textInputEditTextPrice, textInputEditTextQuantity;
    String modelStr, dimensionStr, poweroutputStr, frequencyStr, salientfeatureStr, colorStr, sensitivityStr, designStr, weightStr, manufacturerStr, priceStr, quantityStr;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_bass_tubes);

        progressBar = new ProgressBar(this);
        textInputEditTextModel = findViewById(R.id.basstubes_Model);
        textInputEditTextDimension = findViewById(R.id.basstubes_Dimensions);
        textInputEditTextPowerOutput = findViewById(R.id.basstubes_PowerOutput);
        textInputEditTextFrequency = findViewById(R.id.basstubes_Frequency);
        textInputEditTextSalientFeature = findViewById(R.id.basstubes_SalientFeature);
        textInputEditTextSensitivity = findViewById(R.id.basstubes_Sensitivity);
        textInputEditTextWeight = findViewById(R.id.basstubes_Weight);
        textInputEditTextColor = findViewById(R.id.basstubes_Color);
        textInputEditTextDesign = findViewById(R.id.basstubes_Design);
        textInputEditTextManufacturer = findViewById(R.id.basstubes_Manufacturer);
        textInputEditTextPrice = findViewById(R.id.basstubes_Price);
        textInputEditTextQuantity = findViewById(R.id.basstubes_Quantity);
        imageView = findViewById(R.id.ivBassTubes);
        select = findViewById(R.id.btn_selectImgBassTubes);
        addBasstubes = findViewById(R.id.btn_bass);

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        addBasstubes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelStr = textInputEditTextModel.getText().toString();
                dimensionStr = textInputEditTextDimension.getText().toString();
                poweroutputStr = textInputEditTextPowerOutput.getText().toString();
                frequencyStr = textInputEditTextFrequency.getText().toString();
                salientfeatureStr = textInputEditTextSalientFeature.getText().toString();
                colorStr = textInputEditTextColor.getText().toString();
                weightStr = textInputEditTextWeight.getText().toString();
                sensitivityStr = textInputEditTextSensitivity.getText().toString();
                designStr = textInputEditTextDesign.getText().toString();
                manufacturerStr = textInputEditTextManufacturer.getText().toString();
                priceStr = textInputEditTextPrice.getText().toString();
                quantityStr = textInputEditTextQuantity.getText().toString();

                if (TextUtils.isEmpty(modelStr) || dimensionStr.isEmpty() || poweroutputStr.isEmpty() || frequencyStr.isEmpty() || salientfeatureStr.isEmpty() || colorStr.isEmpty() || weightStr.isEmpty() || sensitivityStr.isEmpty() || designStr.isEmpty() || manufacturerStr.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty()) {
                    Toast.makeText(admin_add_BassTubes.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child("Accessories").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(modelStr) && snapshot.hasChild(manufacturerStr)) {
                                Toast.makeText(admin_add_BassTubes.this, "Already existing Model", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), admin_HomePage.class);
                                startActivity(i);
                            } else {
                                uploadImage();

                                Toast.makeText(admin_add_BassTubes.this, "Value Entered", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), admin_add_ScreenSpeaker.class);
                                startActivity(i);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(admin_add_BassTubes.this, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });
        //      IMG UPLOAD
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
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
                    Toast.makeText(admin_add_BassTubes.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    Toast.makeText(admin_add_BassTubes.this, "Failed to Upload", Toast.LENGTH_SHORT).show();

                }
            });
            uploadtoFirebase(imageUri);
        } else {
            Toast.makeText(this, "Please select Image", Toast.LENGTH_SHORT).show();
        }

    }

    private void uploadtoFirebase(Uri uri) {


        //storageReference= storageReference.child(System.currentTimeMillis()+"."+getFileExtension(uri));
        storageReference = storageReference.child("images/").child(fileName);
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        AndroidScreen_Model model = new AndroidScreen_Model(uri.toString());
                        Accessories_ModelClass modelClass = new Accessories_ModelClass();
                        modelClass.setBoxIncluded("");
                        // modelClass.setBoxIncludes();
                        modelClass.setBrand("");
                        modelClass.setBulbType("");
                        modelClass.setColor(colorStr);
                        modelClass.setChannel("");
                        modelClass.setCategory("");
                        modelClass.setDesign(designStr);
                        modelClass.setDimension(dimensionStr);
                        // modelClass.setDimenension();
                        modelClass.setDuration("");
                        modelClass.setDiameter("");
                        modelClass.setDisplayType("");
                        modelClass.setFrequency(frequencyStr);
                        modelClass.setFragrence("");
                        modelClass.setFeature("");
                        modelClass.setFabricType("");
                        modelClass.setFitType("");
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
                        modelClass.setMaterialType("");
                        modelClass.setMaxPressure("");
                        modelClass.setNoiseLevel("");
                        modelClass.setOperatingVoltage("");
                        modelClass.setOSType("");
                        modelClass.setPowerOutput(poweroutputStr);
                        modelClass.setPrice(priceStr);
                        modelClass.setPosition("");
                        modelClass.setPattern("");
                        modelClass.setQuantity(quantityStr);
                        modelClass.setQuality("");
                        modelClass.setRAM("");
                        modelClass.setROM("");
                        modelClass.setSalientFeature(salientfeatureStr);
                        modelClass.setSensitivity(sensitivityStr);
                        modelClass.setSpeakerType("");
                        modelClass.setScreenSize("");
                        modelClass.setVolume("");
                        modelClass.setVoltage("");
                        modelClass.setWeight(weightStr);
                        modelClass.setWarrenty("");
                        modelClass.setWattage("");
                        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("BassTubes").child(modelStr).setValue(modelClass);

//                        Log.e("", "Img: "+model+""+modelStr+manufacturerStr+dimensionStr+poweroutputStr+frequencyStr+sensitivityStr+salientfeatureStr+weightStr+colorStr+designStr+priceStr+quantityStr);
                        Toast.makeText(admin_add_BassTubes.this, "Uploaded Successfully ", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(admin_add_BassTubes.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
