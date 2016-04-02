package com.example.mukul.workingtabs;

import android.app.Activity;
import android.os.Bundle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import java.util.ArrayList;
import android.util.Log;
import java.util.List;

/**
 * Created by anshul on 31/3/16.
 */
public class getList extends Activity{


    public SQLiteDatabase db;
    public List<String> array = new ArrayList<String>();
    public List<String> array1 = new ArrayList<String>();
   // private Context context;

    public getList(){
        //REQUIRED PUBLIC CONST
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public List<String> getListofevents() {

        this.db = this.openOrCreateDatabase("events", Context.MODE_PRIVATE, null);

        Cursor crs = db.rawQuery("SELECT * FROM event", null);

        while(crs.moveToNext()){
            String uname = crs.getString(crs.getColumnIndex("name"));
            Log.e("The string is : ",uname);
            array.add(uname);
        }
        db.close();
        return array;
    }


    public List<String> getListofstatus() {
        this.db = this.openOrCreateDatabase("events", Context.MODE_PRIVATE, null);
        Cursor crs = db.rawQuery("SELECT * FROM event", null);

        while(crs.moveToNext()){
            String uname = crs.getString(crs.getColumnIndex("status"));
            Log.e("The string is : ",uname);
            array1.add(uname);
        }
        db.close();
        return array1;
    }
}
    
