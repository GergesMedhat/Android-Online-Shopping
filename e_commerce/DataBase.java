package com.example.e_commerce;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Calendar;
import java.util.Date;


public class DataBase extends SQLiteOpenHelper {
    final static String dataName = "DataBase";
    SQLiteDatabase database;
    public Date cur= Calendar.getInstance().getTime();
    public DataBase(Context context) {
        super(context, dataName, null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table  customer (id integer primary key  autoincrement , name text not null, email text not null," +
                "password text not null, gender text not null, birthdate text , jop text )");

        db.execSQL("create table category (id integer primary key  autoincrement , name text not null )");

        db.execSQL("create table product (id integer primary key autoincrement, name text not null , " +
                "price real not null , quantity integer not null , cate_id integer not null ," +
                "foreign key (cate_id)references category (id))");
        db.execSQL("create table Orders (id integer primary key autoincrement,ordDate Date not null,custID integer not null, " +
                "address text not null,foreign key(custID)references customer(id))");
        db.execSQL("create table OrderDetails(id integer primary key autoincrement,Quantity integer not null ,prodID integer primary key,foreign key(id)references Orders(id),foreign key(prodID)references product(id))");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists customer");
        db.execSQL("drop table if exists category");
        db.execSQL("drop table if exists product");
        onCreate(db);

    }

    public void insertCustomer(Customer cust) {
        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", cust.getCustName());
        values.put("email", cust.getMail());
        values.put("password", cust.getPassword());
        values.put("birthdate", cust.getCustBirthDate());
        values.put("gender", cust.getGender());
        values.put("jop", cust.getCustJop());

        database.insert("customer", null, values);
        database.close();

    }

    public Cursor userLogin(String username, String pass) {
        database = getReadableDatabase();
        String[] args = {username, pass};

        Cursor cursor = database.rawQuery("select id from customer where name =? and  password =? ", args);

        if (cursor != null)
            cursor.moveToFirst();

        database.close();
        return cursor;

    }

    public String getPassword(String mail) {
        database = getReadableDatabase();
        String[] args = {mail};
        Cursor cursor = database.rawQuery("select password from customer where email =?", args);
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            database.close();
            return cursor.getString(0);
        }
        database.close();

        cursor.close();
        return null;
    }

    public void insertProduct(Product product){
        database=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",product.getProName());
         values.put("price",product.getPrice());
        values.put("quantity",product.getPro_quantity());
        values.put("cate_id",product.getCatId());

        database.insert("product",null,values);
        database.close();
    }

    public Cursor getProducts(){
        database=getReadableDatabase();
        String[]fields={"id","name","price","quantity","cate_id"};
        Cursor cursor= database.query("product",fields,null,null,null,null,null);

        if (cursor!=null)
            cursor.moveToFirst();

        // database.close();

        return cursor;


    }

    public void insertCategory(Category cate){
        database=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",cate.getCat_name());
        database.insert("category",null,values);
        database.close();
    }



    public Cursor getCategory(){
        database=getReadableDatabase();
        String []fields={"id","name"};
        Cursor cursor= database.query("category",fields,null,null,null,null,null);

        if (cursor.getCount()>0)
            cursor.moveToFirst();

        database.close();

        return cursor;
    }


    public Cursor getProductByName(String name) {
        database = getReadableDatabase();
        String[] arg = {name};
        Cursor cursor = database.rawQuery("Select name from product where name Like ?",arg);
        if(cursor!=null)
            cursor.moveToFirst();


        database.close();
        return cursor;
    }
    public Cursor getProductPriceByName(String name) {
        database = getReadableDatabase();
        String[] arg = {name};
        Cursor cursor = database.rawQuery("Select price from product where name Like ?",arg);
        if(cursor!=null)
            cursor.moveToFirst();


        database.close();
        return cursor;
    }
    public Cursor getProductQuantityByName(String name) {
        database = getReadableDatabase();
        String[] arg = {name};
        Cursor cursor = database.rawQuery("Select quantity from product where name Like ?",arg);
        if(cursor!=null)
            cursor.moveToFirst();


        database.close();
        return cursor;
    }


    public String getCatId(String name ){
        database=getReadableDatabase();
        String[]args={name};
        Cursor cursor=database.rawQuery("select id from category where name =?",args);

        if (cursor.getCount()>0) {

            cursor.moveToFirst();
            database.close();
            return cursor.getString(0);
        }
        database.close();

        cursor.close();
        return null;

    }
    public void insertOrder(Order order){
        database=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("custID",order.getCustId());
        values.put("address",order.getOrderAddress());
        order.setOrderDate(cur);
        values.put("ordDate", String.valueOf(cur));
        database.insert("order",null,values);
        database.close();
    }
}


