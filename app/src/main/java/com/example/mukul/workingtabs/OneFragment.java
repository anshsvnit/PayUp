package com.example.mukul.workingtabs;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import android.widget.TextView;

import java.util.List;


public class OneFragment extends Fragment {

    RecyclerView recList;
    public SQLiteDatabase db;
    public List<String> array = new ArrayList<String>();
    public List<String> array1 = new ArrayList<String>();
   // private Context context;


    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_one, container, false);

        recList = (RecyclerView) v.findViewById(R.id.cardList);

        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        ContactAdapter ca = new ContactAdapter(getListofevents("events"),getListofstatus("events"));

        recList.setAdapter(ca);

        return v;
    }


    public List<String> getListofevents(String evName) {

        this.db = getActivity().openOrCreateDatabase(evName, Context.MODE_PRIVATE, null);

        Cursor crs = db.rawQuery("SELECT * FROM event", null);

        while(crs.moveToNext()){
            String uname = crs.getString(crs.getColumnIndex("name"));
            Log.e("The string is : ", uname);
            array.add(uname);
        }
        db.close();
        return array;
    }


    public List<String> getListofstatus(String stName) {
        this.db = getActivity().openOrCreateDatabase(stName, Context.MODE_PRIVATE, null);
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


