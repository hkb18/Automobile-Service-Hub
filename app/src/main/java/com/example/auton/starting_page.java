package com.example.auton;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class starting_page extends AppCompatActivity {
    TextView textViewfullname, textViewcontactno, textViewemaiid, textViewusername, textViewpassword, textViewconfirmpassword;
    String fullname, contactno, emailid, username, passwd, confirmpasswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_page);

       /* Bundle e=getIntent().getExtras();
        fullname=e.getString("Value1");
        contactno=e.getString("Value2");
        emailid=e.getString("Value3");
        username=e.getString("Value4");
        passwd=e.getString("Value5");
        confirmpasswd=e.getString("Value6");

        textViewfullname=findViewById(R.id.fullname);
        textViewcontactno=findViewById(R.id.contactno);
        textViewemaiid=findViewById(R.id.emailid);
        textViewusername=findViewById(R.id.username);
        textViewpassword=findViewById(R.id.password);
        textViewconfirmpassword=findViewById(R.id.confirmpassword);

        textViewfullname.setText(fullname);
        textViewcontactno.setText(contactno);
        textViewemaiid.setText(emailid);
        textViewusername.setText(username);
        textViewpassword.setText(passwd);
        textViewconfirmpassword.setText(confirmpasswd);*/
    }
}