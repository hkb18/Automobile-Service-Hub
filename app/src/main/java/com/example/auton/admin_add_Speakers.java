package com.example.auton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
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

public class admin_add_Speakers extends AppCompatActivity {
    TextInputEditText  textInputEditTextModel,textInputEditTextSpeakerType,textInputEditTextPowerOutput,textInputEditTextFrequency,textInputEditTextSensitivity,textInputEditTextDiameter,textInputEditTextManufacturer,textInputEditTextColor,textInputEditTextPrice,textInputEditTextQuantity;
    Button add,select;
    ProgressDialog progressDialog;
    ProgressBar progressBar;
    ImageView imageView;
    StorageReference storageReference;
    Uri imageUri;
    String fileName;
    String modelStr,SpeakerTypeStr,PowerOutputStr,FrequencyStr,SensitivityStr,DiameterStr,ColorStr,ManufacturerStr,PriceStr,QuantityStr,sysTime;
    DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_speakers);

        progressBar = new ProgressBar(this);
        add=findViewById(R.id.btn_addSpeaker);
        textInputEditTextSpeakerType=findViewById(R.id.speakerType);
        textInputEditTextPowerOutput=findViewById(R.id.powerOutput);
        textInputEditTextFrequency=findViewById(R.id.frequency);
        textInputEditTextSensitivity=findViewById(R.id.sensitivity);
        textInputEditTextDiameter=findViewById(R.id.diameter);
        textInputEditTextManufacturer=findViewById(R.id.speakerManufacturer);
        textInputEditTextColor=findViewById(R.id.color);
        textInputEditTextPrice=findViewById(R.id.speakerPrice);
        textInputEditTextQuantity=findViewById(R.id.speakerQuantity);
        textInputEditTextModel=findViewById(R.id.speaker_Model);
        imageView=findViewById(R.id.ivSpeaker);
        select=findViewById(R.id.btn_selectImgSpeaker);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpeakerTypeStr=textInputEditTextSpeakerType.getText().toString();
                PowerOutputStr=textInputEditTextPowerOutput.getText().toString();
                FrequencyStr=textInputEditTextFrequency.getText().toString();
                SensitivityStr=textInputEditTextSensitivity.getText().toString();
                DiameterStr=textInputEditTextDiameter.getText().toString();
                ManufacturerStr=textInputEditTextManufacturer.getText().toString();
                ColorStr=textInputEditTextColor.getText().toString();
                PriceStr=textInputEditTextPrice.getText().toString();
                QuantityStr=textInputEditTextQuantity.getText().toString();
                modelStr=textInputEditTextModel.getText().toString();

                if(TextUtils.isEmpty(SpeakerTypeStr)||modelStr.isEmpty()|| PowerOutputStr.isEmpty() || FrequencyStr.isEmpty() || SensitivityStr.isEmpty() || DiameterStr.isEmpty() || ManufacturerStr.isEmpty() || ColorStr.isEmpty() ||PriceStr.isEmpty() ||  QuantityStr.isEmpty()) {
                    Toast.makeText(admin_add_Speakers.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child("Accessories").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            uploadImage();


                            Toast.makeText(admin_add_Speakers.this, "Value Entered", Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(getApplicationContext(),admin_add_ScreenSpeaker.class);
                            startActivity(i);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(admin_add_Speakers.this, "error"+error.getMessage().toString(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(admin_add_Speakers.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    Toast.makeText(admin_add_Speakers.this, "Failed to Upload", Toast.LENGTH_SHORT).show();

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
                        AndroidScreen_Model model= new AndroidScreen_Model(uri.toString());
//                        String modelid=databaseReference.push().getKey();//to generate random key
                        //sysTime=String.valueOf(System.currentTimeMillis());
                        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("Speaker").child(modelStr).child("Image").setValue(uri.toString());
                        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("Speaker").child(modelStr).child("Model").setValue(modelStr);
                        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("Speaker").child(modelStr).child("SpeakerType").setValue(SpeakerTypeStr);
                        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("Speaker").child(modelStr).child("PowerOutput").setValue(PowerOutputStr);
                        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("Speaker").child(modelStr).child("Frequency").setValue(FrequencyStr);
                        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("Speaker").child(modelStr).child("Sensitivity").setValue(SensitivityStr);
                        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("Speaker").child(modelStr).child("Diameter").setValue(DiameterStr);
                        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("Speaker").child(modelStr).child("Manufacturer").setValue(ManufacturerStr);
                        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("Speaker").child(modelStr).child("Color").setValue(ColorStr);
                        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("Speaker").child(modelStr).child("Price").setValue(PriceStr);
                        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("Speaker").child(modelStr).child("Quantity").setValue(QuantityStr);


                        //      Log.e("", "Img: "+model+""+modelStr+manufacturerStr+dimensionStr+poweroutputStr+frequencyStr+sensitivityStr+salientfeatureStr+weightStr+colorStr+designStr+priceStr+quantityStr);
                        Toast.makeText(admin_add_Speakers.this, "Uploaded Successfully ", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(admin_add_Speakers.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

