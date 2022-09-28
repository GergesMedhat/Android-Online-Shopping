package com.example.e_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    DataBase db;
    EditText nameTxt;
    EditText passTxt;
    Button signup;
    CheckBox check;

    Button log;
    TextView forget;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);


        db=new DataBase(this);
        nameTxt= findViewById(R.id.name_txt);
        passTxt= findViewById(R.id.pass_txt);
        check=findViewById(R.id.checkBox);
        if (saveLogin == true) {
            nameTxt.setText(loginPreferences.getString("username", ""));
            passTxt.setText(loginPreferences.getString("password", ""));
            check.setChecked(true);
        }



         signup= findViewById(R.id.sign_btn);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Signup.class);
                startActivity(intent);
                finish();
            }
        });
        log= findViewById(R.id.log_btn);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=nameTxt.getText().toString();
                String password=passTxt.getText().toString();


                Cursor cursor = db.userLogin(name, password);

                if(cursor.getCount()<=0||name.isEmpty()||password.isEmpty())
                    Toast.makeText(getApplicationContext(),"Please Check username and password", Toast.LENGTH_SHORT).show();
                else
                {
                    if(check.isChecked())
                    {

                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("username", name);
                        loginPrefsEditor.putString("password", password);
                        loginPrefsEditor.commit();
                    }
                    else {
                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                    }

                    Intent start=new Intent(MainActivity.this,MakeOrder.class);

                    startActivity(start);
                    finish();
                    Toast.makeText(getApplicationContext(), "Successfully login", Toast.LENGTH_SHORT).show();

                }


            }
        });
        forget= findViewById(R.id.forget_txt);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 =new Intent(MainActivity.this,ForgetPassword.class);
                startActivity(intent2);
                finish();
            }
        });

    }

}
