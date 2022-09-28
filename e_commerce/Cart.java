package com.example.e_commerce;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.service.autofill.SaveInfo;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Cart extends AppCompatActivity {
    ListView cart1;
    Date current=Calendar.getInstance().getTime();
    TextView total;
    DataBase db;
    Button submit;
    EditText location;
    Button back;
    Button map;
    ArrayAdapter<String>cartAdapter;
    public String pro = null;
    int numofprod=1;
    int newPrice;
    private SharedPreferences cartPreferences;
    private SharedPreferences.Editor cartPrefsEditor;
    private Boolean saveCart;


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater=new MenuInflater(this);
        inflater.inflate(R.menu.menu1,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {


        cart1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pro = (((TextView)view).getText().toString());
                Cursor cursor=db.getProductQuantityByName(pro);
                String quan=cursor.getString(0);
                int quantity=Integer.parseInt(quan);

                Cursor cursor2=db.getProductPriceByName(pro);
                String price=cursor2.getString(0);


                if (item.getItemId() == R.id.addition) {

                    if((numofprod+1)>quantity)
                    {
                        Toast.makeText(getApplicationContext(),"Not Enough Available",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), pro + " increment", Toast.LENGTH_SHORT).show();
                        newPrice+= Integer.parseInt(price) ;


                        total.setText(String.valueOf(newPrice));
                        cartAdapter.add(pro);
                        numofprod++;
                    }
                }
                else if(item.getItemId()==R.id.sup)
                {
                    if(numofprod-1<1)
                    {
                        newPrice-=Integer.parseInt(price);
                        total.setText(String.valueOf(newPrice));
                        cartAdapter.remove(pro);
                        Toast.makeText(getApplicationContext(),"Item Deleted",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        newPrice-=Integer.parseInt(price);
                        total.setText(String.valueOf(newPrice));
                        Toast.makeText(getApplicationContext(),"decrement",Toast.LENGTH_SHORT).show();
                        numofprod--;

                        cartAdapter.remove(pro);

                    }

                }
                else if(item.getItemId()==R.id.remove)
                {
                    for (int i = 0 ; i<cart1.getCount();i++) {

                        cartAdapter.remove(pro);
                    }
                    total.setText("0");
                    Toast.makeText(getApplicationContext(), pro + " deleted", Toast.LENGTH_SHORT).show();
                }
            }



        });


        return false;

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartPreferences = getSharedPreferences("cartPrefs", MODE_PRIVATE);
        cartPrefsEditor = cartPreferences.edit();
        saveCart = cartPreferences.getBoolean("saveCart", false);

        db=new DataBase(this);
        Customer cas=new Customer();
        int i=cas.getId();
        location=(EditText)findViewById(R.id.Location_txt);

        submit=(Button)findViewById(R.id.Sub_btn);
        back=(Button)findViewById(R.id.button4);
        total = (TextView) findViewById(R.id.total_txt);
        total.setText("0");
        map=(Button)findViewById(R.id.map_btn);
        cart1=(ListView) findViewById(R.id.cartList);
        registerForContextMenu(cart1);
        cartAdapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
        cart1.setAdapter(cartAdapter);



        String name = getIntent().getExtras().getString("name");
        if(!name.equals("")) {
            Cursor c = db.getProductByName(name);

            cartAdapter.add(c.getString(0));

                Cursor cursor = db.getProductPriceByName(name);
                String price = cursor.getString(0);
                newPrice += Integer.parseInt(price);
                String totalprice = price;
                total.setText(totalprice);


            Cursor cursor2 = db.getProductQuantityByName(name);

            String quantity = cursor2.getString(0);



        }

      if (saveCart==true) {
          if(!cartPreferences.getString("prodName","").equals(""))
              cartAdapter.add(cartPreferences.getString("prodName", ""));



          if(total.getText().toString().equals("0")&&name.equals("")) {
                String nPrice=(cartPreferences.getString("prodPrice",total.getText().toString()));
                newPrice+=Integer.parseInt(nPrice);
                total.setText(String.valueOf(newPrice));
            }
        }



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartPrefsEditor.putBoolean("saveCart", true);
                cartPrefsEditor.putString("prodName", name);
                if(!total.getText().toString().equals("0")) {
                    cartPrefsEditor.putString("prodPrice", total.getText().toString());
                }
                cartPrefsEditor.commit();
                Intent intent=new Intent(Cart.this,MakeOrder.class);
                startActivity(intent);


            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cart1.getCount()!=0) {
                    String currentLocation=location.getText().toString();
                    Order order = new Order(i,currentLocation,current);
                    db.insertOrder(order);
                    cartPrefsEditor.clear();
                    cartPrefsEditor.commit();
                    total.setText("0");
                    location.setText("");
                    cartAdapter.clear();
                    Toast.makeText(getApplicationContext(), "Your Order submitted !", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"Your cart is empty",Toast.LENGTH_SHORT).show();

            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Cart.this, MapsActivity2.class);
                startActivity(intent);
                finish();
            }
        });



    }

}
