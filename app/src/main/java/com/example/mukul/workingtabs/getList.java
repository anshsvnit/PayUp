package com.example.mukul.workingtabs;

import android.app.Activity;
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

    public DataHelper helper;
    public SQLiteDatabase db;
    public static List<String> array = new ArrayList<String>();


    public List<String> getListofevents() {
        db = getBaseContext().openOrCreateDatabase("events", Context.MODE_PRIVATE, null);
        Cursor crs = db.rawQuery("SELECT * FROM events", null);

        while(crs.moveToNext()){
            String uname = crs.getString(crs.getColumnIndex("name"));
            Log.e("The string is : ",uname);
            array.add(uname);
        }
        return array;
    }
    }
    
