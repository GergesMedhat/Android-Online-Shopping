package com.example.e_commerce;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ForgetPassword extends AppCompatActivity {
    Button forgett;
    EditText pass;
    DataBase db;
    EditText em;
    String email;
    String PassWord;
    Button back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        forgett=findViewById(R.id.forget_btn);
        db=new DataBase(this);
        pass=findViewById(R.id.setpass_txt);
        back=findViewById(R.id.back_btn);
        forgett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                em=findViewById(R.id.Email_txt);
                email=em.getText().toString().trim();
                PassWord= db.getPassword(email);
                if(PassWord==null)
                    Toast.makeText(getApplicationContext(),"Please check your email",Toast.LENGTH_SHORT).show();
                else
                    pass.setText(PassWord);

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetPassword.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
