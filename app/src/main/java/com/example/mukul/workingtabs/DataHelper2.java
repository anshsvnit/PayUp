package com.example.mukul.workingtabs;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Anshul on 24-03-2016.
 */
public class DataHelper2 extends SQLiteOpenHelper{

    //database version, current ver is 1.
    public static final int DATABASE_VER=1;

    //database Name or db name
    public static final String DATABASE_NAME="payment";

    public static final String PAYMENT_BY="payee_name";
    public static final String PAYMENT_NAME="payment_name";
    public static final String PAYMENT_AMOUNT="payment_amount";

    public DataHelper2(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

}
