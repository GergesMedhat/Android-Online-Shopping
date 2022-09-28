package com.example.e_commerce;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class UploadProduct extends AppCompatActivity {
EditText prodName ,prodPrice,prodQuantity,addcate;
ArrayAdapter adapter;
Button upload;
Button add;
Button back;
DataBase db;
Spinner proCategory;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        prodName=(EditText) findViewById(R.id.prod_txt);
        prodPrice=(EditText) findViewById(R.id.price_txt);
        prodQuantity=(EditText) findViewById(R.id.quan_txt);
        addcate=(EditText)findViewById(R.id.addcategory);
        add=(Button)findViewById(R.id.addcate_btn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Cate=addcate.getText().toString();
                Category category=new Category();
                category.setCat_name(Cate);
                db.insertCategory(category);
                addcate.setText("");
                Toast.makeText(getApplicationContext(),Cate+" Added To Category",Toast.LENGTH_SHORT).show();
            }
        });
        upload=(Button) findViewById(R.id.upload_btn);
        back=(Button)findViewById(R.id.back2_btn);
        proCategory=(Spinner)findViewById(R.id.spinner2);
        db=new DataBase(this);

     //   addCategory();
        getAllcategory();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadProduct();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadProduct.this,MakeOrder.class);
                startActivity(intent);
                finish();
            }
        });


    }
  /*  protected void addCategory(){
        db.insertCategory(new Category("fashion"));
        db.insertCategory(new Category("cars"));
        db.insertCategory(new Category("sport"));

    } */
    protected void getAllcategory(){

        List<String>cate=new ArrayList<>();
        Cursor cursor=db.getCategory();
        if (cursor!=null){
            while (!cursor.isAfterLast()){
                cate.add(cursor.getString(1));
                cursor.moveToNext();
            }
            adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,cate);
            proCategory.setAdapter(adapter);
            adapter.add(cate);

        }
    }

    protected void uploadProduct(){

        String name=prodName.getText().toString().trim();
        String price=prodPrice.getText().toString().trim();
        String quan=prodQuantity.getText().toString().trim();
        int catid=Integer.parseInt(db.getCatId(proCategory.getSelectedItem().toString()));


        if(!name.isEmpty()&&!price.isEmpty()&&!quan.isEmpty())
        {
                if(quan.equals("0"))
                {
                    Toast.makeText(getApplicationContext(),"Can not Upload 0 Quantity!",Toast.LENGTH_SHORT).show();
                }
                else {
                    Product product = new Product(Integer.parseInt(quan), catid, name, Double.parseDouble(price));
                    db.insertProduct(product);
                    prodName.setText("");
                    prodPrice.setText("");
                    prodQuantity.setText("");
                    Toast.makeText(this, "product added", Toast.LENGTH_SHORT).show();
                }

        }
        else
        {
            Toast.makeText(this, "Check data again", Toast.LENGTH_SHORT).show();
        }
    }

}
