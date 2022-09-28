package com.example.e_commerce;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MakeOrder extends AppCompatActivity {

    ListView products;
    ArrayAdapter<String>adapter;
    Button upload;
    Button cart;
    Button search;
    DataBase db;
    Cursor cursor;
    String name="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        products=findViewById(R.id.prod_list);
        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        products.setAdapter(adapter);
        search=(Button)findViewById(R.id.button2);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MakeOrder.this,Main2Activity.class);
                startActivity(intent);
                finish();
            }
        });


        upload=findViewById(R.id.up_btn);
        cart=findViewById(R.id.cart_btn);
        db=new DataBase(this);
        cursor = db.getProducts();
        if(cursor!=null)
            cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            adapter.add(cursor.getString(1));

            cursor.moveToNext();

        }



        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x=new Intent(MakeOrder.this,UploadProduct.class);
                startActivity(x);
                finish();
            }
        });
        products.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                name=((TextView)view).getText().toString();


                Toast.makeText(getApplicationContext(),name+" added to cart",Toast.LENGTH_SHORT).show();

                return true;
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(MakeOrder.this,Cart.class);
                i.putExtra("name",name);



                startActivity(i);
                finish();
            }
        });
    }
}
