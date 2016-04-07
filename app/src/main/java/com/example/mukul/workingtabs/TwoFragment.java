package com.example.mukul.workingtabs;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class TwoFragment extends Fragment {

    public TwoFragment() {
        // Required empty public constructor
    }

    /*FrameLayout frame;
    RecyclerView recList;
    TextView BlankDB;
    private SQLiteDatabase datab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_two, container, false);
        frame = (FrameLayout)v.findViewById(R.id.frame);

        if(checkDataBase()) {
            recList = new RecyclerView(getActivity());
            recList.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recList.setLayoutManager(llm);

            ContactAdapter ca = new ContactAdapter(getListofevents("events", "name"),getListofevents("events", "status"),getListofevents("events", "numbers"));
            recList.setAdapter(ca);
            frame.addView(recList);
        }

        else{
            BlankDB = new TextView(getActivity());
            BlankDB.setText("There is no event to display");
            BlankDB.setTextSize(40);
            frame.addView(BlankDB);
        }
        return v;
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(getActivity().getDatabasePath("events").toString(), null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            // database doesn't exist yet.
        }
        return checkDB != null;
    }


    public ArrayList<String> getListofevents(String evName,String attribute) {

        datab = getActivity().openOrCreateDatabase(evName, Context.MODE_PRIVATE, null);
        ArrayList<String> array = new ArrayList<String>();
        Cursor crs = datab.rawQuery("SELECT * FROM event", null);
        while(crs.moveToNext()){
            String uname = crs.getString(crs.getColumnIndex(attribute));
            String tmpStatus = crs.getString(crs.getColumnIndex("status"));
            if(tmpStatus.equalsIgnoreCase("no")) {
                Log.e("The string is : ", uname);
                array.add(uname);
            }
        }
        crs.close();
        datab.close();
        return array;
    }
*/
}

