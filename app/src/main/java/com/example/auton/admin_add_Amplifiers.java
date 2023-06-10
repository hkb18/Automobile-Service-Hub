package com.example.auton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

public class admin_add_Amplifiers extends AppCompatActivity {
    Button add,select;
    ProgressDialog progressDialog;
    ProgressBar progressBar;
    ImageView imageView;
    StorageReference storageReference;
    Uri imageUri;
    String fileName;
    TextInputEditText textInputEditTextModel,textInputEditTextMaxVoltage,textInputEditTextMountingHardware,textInputEditTextDimension,textInputEditTextChannels,textInputEditTextWeight,textInputEditTextManufacturer,textInputEditTextPrice,textInputEditTextQuantity;
    String modelStr,maxVoltageStr,mountingHardwareStr,dimensionStr,channelStr,weightStr,manufacturerStr,priceStr,quantityStr;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_amplifiers);

        progressBar = new ProgressBar(this);
        add=findViewById(R.id.btn_add_Amplifier);
        textInputEditTextModel=findViewById(R.id.amplifierModel);
        textInputEditTextMaxVoltage=findViewById(R.id.amplifierMaxVoltage);
        textInputEditTextMountingHardware=findViewById(R.id.amplifierMountingHardware);
        textInputEditTextDimension=findViewById(R.id.amplifierDimensions);
        textInputEditTextChannels=findViewById(R.id.amplifierChannels);
        textInputEditTextWeight=findViewById(R.id.amplifierWeight);
        textInputEditTextManufacturer=findViewById(R.id.amplifierManufacturer);
        textInputEditTextPrice=findViewById(R.id.amplifierPrice);
        textInputEditTextQuantity=findViewById(R.id.amplifierQuantity);
        imageView=findViewById(R.id.ivAmplifier);
        select=findViewById(R.id.btn_selectImgAmplifier);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelStr=textInputEditTextModel.getText().toString();
                maxVoltageStr=textInputEditTextMaxVoltage.getText().toString();
                mountingHardwareStr=textInputEditTextMountingHardware.getText().toString();
                dimensionStr=textInputEditTextDimension.getText().toString();
                channelStr=textInputEditTextChannels.getText().toString();
                weightStr=textInputEditTextWeight.getText().toString();
                manufacturerStr=textInputEditTextManufacturer.getText().toString();
                priceStr=textInputEditTextPrice.getText().toString();
                quantityStr=textInputEditTextQuantity.getText().toString();


                if(modelStr.isEmpty()|| maxVoltageStr.isEmpty() || mountingHardwareStr.isEmpty() || dimensionStr.isEmpty() || channelStr.isEmpty() || weightStr.isEmpty() || manufacturerStr.isEmpty() ||priceStr.isEmpty() ||  quantityStr.isEmpty()) {
                    Toast.makeText(admin_add_Amplifiers.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child("Accessories").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            uploadImage();

//                            databaseReference.child("Accessories").child("AndroidScreens").child(modelStr).child("Image").setValue();
                            Toast.makeText(admin_add_Amplifiers.this, "Value Entered", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), admin_add_ScreenSpeaker.class);
                            startActivity(i);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(admin_add_Amplifiers.this, "error" + error.getMessage().toString(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(admin_add_Amplifiers.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();

                    Toast.makeText(admin_add_Amplifiers.this, "Failed to Upload", Toast.LENGTH_SHORT).show();

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
                        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("Amplifiers").child(modelStr).child("Image").setValue(uri.toString());
                        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("Amplifiers").child(modelStr).child("Model").setValue(modelStr);
                        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("Amplifiers").child(modelStr).child("MaxVoltage").setValue(maxVoltageStr);
                        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("Amplifiers").child(modelStr).child("MountingHardware").setValue(mountingHardwareStr);
                        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("Amplifiers").child(modelStr).child("Dimenension").setValue(dimensionStr);
                        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("Amplifiers").child(modelStr).child("Channel").setValue(channelStr);
                        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("Amplifiers").child(modelStr).child("Weight").setValue(weightStr);
                        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("Amplifiers").child(modelStr).child("Manufacturer").setValue(manufacturerStr);
                        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("Amplifiers").child(modelStr).child("Price").setValue(priceStr);
                        databaseReference.child("Accessories").child("SCREENS_SPEAKERS").child("Amplifiers").child(modelStr).child("Quantity").setValue(quantityStr);

                        //      Log.e("", "Img: "+model+""+modelStr+manufacturerStr+dimensionStr+poweroutputStr+frequencyStr+sensitivityStr+salientfeatureStr+weightStr+colorStr+designStr+priceStr+quantityStr);
                        Toast.makeText(admin_add_Amplifiers.this, "Uploaded Successfully ", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(admin_add_Amplifiers.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

