package com.example.mukul.workingtabs;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by Anshul on 24-03-2016.
 */
public class DataHelper  extends SQLiteOpenHelper{

    //database version, current ver is 1.
    public static final int DATABASE_VER=1;

    //database Name or db name
    public static final String DATABASE_NAME="events";

    //table Name, table person
    public String TABLE_NAME="event";

    //table fields name,fist name
    public static final String EVENT_NAME="name";
    public static final String EVENT_STATUS="status";
    public static final String MEMBER_NO ="numbers";
    public static final String AMOUNT_TOTAL ="totalmoney";
    public static final String START_DATE ="sdate";

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        //creating string sqlTable for creating a table
        String sqlTable = "CREATE TABLE " +TABLE_NAME+ "(" +EVENT_NAME+ " TEXT," +EVENT_STATUS+  " TEXT," + MEMBER_NO+" NUMBER," +AMOUNT_TOTAL+" TEXT," +START_DATE+" TEXT);";
        //db.execSQL() will execute string which we provide and will create a table with given table name and fields.
       Log.e("TABLE_NAME",TABLE_NAME);
        db.execSQL(sqlTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

}
