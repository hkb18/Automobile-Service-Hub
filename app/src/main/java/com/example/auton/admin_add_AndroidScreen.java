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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.auton.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class admin_add_AndroidScreen extends AppCompatActivity {

    DatabaseReference databaseReference;
    Button add,upload,select;
    ProgressDialog progressDialog;
    ImageView imageView;

    StorageReference storageReference;
    Uri imageUri;
    TextInputEditText textInputEditTextModel,textInputEditTextDimension,textInputEditTextRAM,textInputEditTextROM,textInputEditTextDisplayType,
        textInputEditTextOSType,textInputEditTextWeight,textInputEditTextScreenSize,textInputEditTextManufacturer,textInputEditTextPrice,textInputEditTextQuantity;
    String modelStr,dimensionStr,ramStr,romStr,displaytypeStr,ostypeStr,weightStr,screensizeStr,manufacturerStr,priceStr,quantityStr;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_android_screen);



        imageView=findViewById(R.id.image_view);
        select=findViewById(R.id.btn_selectImg);
        upload=findViewById(R.id.btn_uploadImg);
        add=findViewById(R.id.btn_addAndroidScreens);
        textInputEditTextModel=findViewById(R.id.screenModel);
        textInputEditTextDimension=findViewById(R.id.screenDimensions);
        textInputEditTextRAM=findViewById(R.id.screenRam);
        textInputEditTextROM=findViewById(R.id.screenRom);
        textInputEditTextDisplayType=findViewById(R.id.screenDisplayType);
        textInputEditTextOSType=findViewById(R.id.screenOSType);
        textInputEditTextWeight=findViewById(R.id.screenWeight);
        textInputEditTextScreenSize=findViewById(R.id.screenSize);
        textInputEditTextManufacturer=findViewById(R.id.screenManufacturer);
        textInputEditTextPrice=findViewById(R.id.screenPrice);
        textInputEditTextQuantity=findViewById(R.id.screenQuantity);

        databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://auton-648f3-default-rtdb.firebaseio.com/");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelStr=textInputEditTextModel.getText().toString();
                dimensionStr=textInputEditTextDimension.getText().toString();
                ramStr=textInputEditTextRAM.getText().toString();
                romStr=textInputEditTextROM.getText().toString();
                displaytypeStr=textInputEditTextDisplayType.getText().toString();
                ostypeStr=textInputEditTextOSType.getText().toString();
                weightStr=textInputEditTextWeight.getText().toString();
                screensizeStr=textInputEditTextScreenSize.getText().toString();
                manufacturerStr=textInputEditTextManufacturer.getText().toString();
                priceStr=textInputEditTextPrice.getText().toString();
                quantityStr=textInputEditTextQuantity.getText().toString();

                if(TextUtils.isEmpty(modelStr)|| dimensionStr.isEmpty() || ramStr.isEmpty() || romStr.isEmpty() || displaytypeStr.isEmpty() || ostypeStr.isEmpty() || weightStr.isEmpty() ||screensizeStr.isEmpty() || manufacturerStr.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty()) {
                    Toast.makeText(admin_add_AndroidScreen.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child("Accessories").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(modelStr) && snapshot.hasChild(manufacturerStr)){
                                Toast.makeText(admin_add_AndroidScreen.this,"Already existing Model",Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(getApplicationContext(),admin_HomePage.class);
                                startActivity(i);
                            }
                            else {
                            databaseReference.child("Accessories").child("AndroidScreens").child(modelStr).child("Model").setValue(modelStr);
                            databaseReference.child("Accessories").child("AndroidScreens").child(modelStr).child("Dimension").setValue(dimensionStr);
                            databaseReference.child("Accessories").child("AndroidScreens").child(modelStr).child("RAM").setValue(ramStr);
                            databaseReference.child("Accessories").child("AndroidScreens").child(modelStr).child("ROM").setValue(romStr);
                            databaseReference.child("Accessories").child("AndroidScreens").child(modelStr).child("DisplayType").setValue(displaytypeStr);
                            databaseReference.child("Accessories").child("AndroidScreens").child(modelStr).child("OSType").setValue(ostypeStr);
                            databaseReference.child("Accessories").child("AndroidScreens").child(modelStr).child("Weight").setValue(weightStr);
                            databaseReference.child("Accessories").child("AndroidScreens").child(modelStr).child("ScreenSize").setValue(screensizeStr);
                            databaseReference.child("Accessories").child("AndroidScreens").child(modelStr).child("Manufacturer").setValue(manufacturerStr);
                            databaseReference.child("Accessories").child("AndroidScreens").child(modelStr).child("Price").setValue(priceStr);
                            databaseReference.child("Accessories").child("AndroidScreens").child(modelStr).child("Quantity").setValue(quantityStr);
//                            databaseReference.child("Accessories").child("AndroidScreens").child(modelStr).child("Image").setValue();
                            Toast.makeText(admin_add_AndroidScreen.this, "Value Entered", Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(getApplicationContext(),admin_Add_Accessories.class);
                            startActivity(i);
                        }}

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(admin_add_AndroidScreen.this, "error"+error.getMessage().toString(), Toast.LENGTH_SHORT).show();

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

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadImage();
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

    private void uploadImage(){

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Uploading File...");
        progressDialog.show();

        SimpleDateFormat formatter=new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now=new Date();
        String fileName=formatter.format(now);
        storageReference=FirebaseStorage.getInstance().getReference("images/"+fileName);

        storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageView.setImageURI(null);
                Toast.makeText(admin_add_AndroidScreen.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                if (progressDialog.isShowing())
                    progressDialog.dismiss();

                Toast.makeText(admin_add_AndroidScreen.this, "Failed to Upload", Toast.LENGTH_SHORT).show();

            }
        });
    }
}