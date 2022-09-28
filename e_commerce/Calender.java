package com.example.e_commerce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Calender extends AppCompatActivity {
    CalendarView calender;
    EditText txt,date;
    Button btn;
    String text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        calender=(CalendarView) findViewById(R.id.calendarView);
        txt=(EditText) findViewById(R.id.cale_txt);
        date=(EditText)findViewById(R.id.date_txt);
        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                txt.setText(dayOfMonth+"/"+month+"/"+year);
                text=txt.getText().toString();
            }
        });

        btn=(Button) findViewById(R.id.button3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Calender.this,Signup.class);
                intent.putExtra("mytext",text);
                startActivity(intent);
                finish();
            }
        });


    }
}
