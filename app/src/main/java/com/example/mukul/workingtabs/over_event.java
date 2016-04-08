package com.example.mukul.workingtabs;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class over_event extends AppCompatActivity {
    FrameLayout frame;
    RecyclerView recList;
    TextView BlankDB,tview;
    String evName;
    public SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.over_event_layout);
        evName = getIntent().getExtras().getString("eventtext");
        tview = (TextView) findViewById(R.id.eventId);
        frame = (FrameLayout) findViewById(R.id.frameover);
        tview.setText(evName);
        if (checkDataBase()) {

            recList = new RecyclerView(this);
            recList.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recList.setLayoutManager(llm);
            OverEventAdapter adapter = new OverEventAdapter(getListofsettlements("settlement",evName,"payby"),getListofsettlements("settlement",evName,"payto"),getListofsettlements("settlement",evName,"amount"));
            recList.setAdapter(adapter);
            frame.addView(recList);
        }
        else {
            BlankDB = new TextView(this);
            BlankDB.setText("There is no payment to display");
            BlankDB.setTextSize(40);
            frame.addView(BlankDB);
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(getDatabasePath("settlement").toString(), null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            // database doesn't exist yet.
        }
        return checkDB != null;
    }

    private ArrayList<String> getListofsettlements(String dbName,String eventName,String string) {

        this.db = this.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        ArrayList<String> array = new ArrayList<String>();
        Cursor crs = db.rawQuery("SELECT * FROM "+eventName, null);

        while(crs.moveToNext()){
            String uname = crs.getString(crs.getColumnIndex(string));
            Log.e("The string is : ", uname);
            array.add(uname);
        }
        db.close();
        return array;
    }

}
