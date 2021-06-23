package com.sudarshan.registration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {
    private static final String DB_NAME="registration";
    private static final int DB_VERSION=1;
    private static final String TABLE_NAME="record";
    private static final String Fname="fname";
    private static final String Lname="lname";
    private static final String Dob="dob";
    private static final String Email="email";
    private static final String Password="password";
    public DataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE "+TABLE_NAME+" ("+Fname+ " TEXT,"+Lname+" TEXT,"+Dob+" TEXT,"+Email+" TEXT PRIMARY KEY,"+Password+" TEXT)";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void insertRecord(String fname,String lname,String dob,String email,String password){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Fname,fname);
        values.put(Lname,lname);
        values.put(Dob,dob);
        values.put(Email,email);
        values.put(Password,password);
        db.insert(TABLE_NAME,null,values);
        db.close();
    }
    public Boolean checkemail(String email){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * from record where email = ?",new String[] {email});
        if(cursor.getCount()>0){
            db.close();
            return true;
        }else
            db.close();
            return false;
    }
    public Boolean checkemailpassword(String email,String password){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * from record where email = ? and password=?",new String[] {email,password});
        if(cursor.getCount()>0){
            db.close();
            return true;
        }else
            db.close();
            return false;
    }
}

