package com.example.e_commerce;

import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Intent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Signup extends AppCompatActivity {
    EditText nameTxt;
    EditText emailTxt;
    EditText passTxt;
    EditText dateTxt;
    EditText jobTxt;
    Button calender;

    EditText GenderTxt;
    Button sign;
    DataBase db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        db=new DataBase(this);
        calender=(Button)findViewById(R.id.calender_btn);
        calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Signup.this,Calender.class);
                startActivity(intent);
                finish();
            }
        });
        nameTxt=(EditText) findViewById(R.id.enter_name);
        emailTxt=(EditText) findViewById(R.id.enter_email);
        passTxt=(EditText) findViewById(R.id.enter_password);
        dateTxt=(EditText)findViewById(R.id.date_txt);
        dateTxt.setText(getIntent().getStringExtra("mytext"));
        jobTxt=(EditText) findViewById(R.id.job_txt);
        GenderTxt=(EditText) findViewById(R.id.Gender_txt);
        sign=(Button)findViewById(R.id.signup_btn) ;
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=nameTxt.getText().toString();
                String email=emailTxt.getText().toString();
                String Password=passTxt.getText().toString();
                String date=dateTxt.getText().toString();
                String job=jobTxt.getText().toString();
                String Gender=GenderTxt.getText().toString();
                if(!name.isEmpty()&&!Password.isEmpty()&&!email.isEmpty()&&!date.isEmpty()&&!job.isEmpty()&&!Gender.isEmpty()) {
                    Customer c = new Customer();
                    c.setCustName(name);
                    c.setGender(Gender);
                    c.setCustBirthDate(date);
                    c.setMail(email);
                    c.setPassword(Password);
                    c.setCustJop(job);
                    db.insertCustomer(c);
                    Toast.makeText(getApplicationContext(), "Registration succeeded", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Signup.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                    Toast.makeText(getApplicationContext(),"Check your information",Toast.LENGTH_SHORT).show();
            }
        });


    }
}
