package com.example.mukul.workingtabs;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;

import android.view.View;

import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import java.util.List;

public class activeEvent extends AppCompatActivity {

    FrameLayout frame;
    RecyclerView recList;
    TextView BlankDB,tview;
    String evName;
    Button addPayment;
    DataHelper helper;
    private DialogFragment addPayFrag;
    public SQLiteDatabase db,db1;
    List<String> tmpAmounts = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_event_display);
        evName = getIntent().getExtras().getString("eventtext");
        tview = (TextView) findViewById(R.id.eventId);
        frame = (FrameLayout) findViewById(R.id.frameactive);
        tview.setText(evName);

        if (checkDataBase()) {
            updateEventsDatabase(evName);
            recList = new RecyclerView(this);
            recList.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recList.setLayoutManager(llm);
            PaymentAdapter ca = new PaymentAdapter(getListofpayments("payment",evName,"payment_name"), getListofpayments("payment",evName,"payee_name"), getListofpayments("payment", evName, "payment_amount"));

            recList.setAdapter(ca);
            frame.addView(recList);
        } else {
            BlankDB = new TextView(this);
            BlankDB.setText("There is no event to display");
            BlankDB.setTextSize(40);
            frame.addView(BlankDB);
        }

        addPayment =(Button) findViewById(R.id.newPayment);
        View.OnClickListener listener = new View.OnClickListener() {

            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                addPayFrag = new addPayment();
                addPayFrag.show(fragmentManager, "activity_add_payment");
            }
        };
        addPayment.setOnClickListener(listener);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatesettlementDB();
            }
        });

    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(getDatabasePath("payment").toString(), null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            // database doesn't exist yet.
        }
        return checkDB != null;
    }


    private List<String> getListofpayments(String dbName,String evName,String string) {

        this.db = this.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
        List<String> array = new ArrayList<String>();
        Cursor crs = db.rawQuery("SELECT * FROM "+evName, null);

        while(crs.moveToNext()){
            String uname = crs.getString(crs.getColumnIndex(string));
            Log.e("The string is : ", uname);
            array.add(uname);
        }
        db.close();
        return array;
    }


    public void updatesettlementDB() {

        createSettlement();
        SQLiteDatabase db2, db3;
        db2 = this.openOrCreateDatabase("events", Context.MODE_PRIVATE, null);
        List<Float> listpaymentspos = new ArrayList<Float>();
        List<String> listmemberspos = new ArrayList<String>();
        Cursor crs = db2.rawQuery("SELECT * FROM " + evName + " WHERE paid > 0 ORDER BY paid ASC", null);
        while (crs.moveToNext()) {
            String amountpaid = crs.getString(crs.getColumnIndex("paid"));
            String uname = crs.getString(crs.getColumnIndex("name"));
            Log.e("The string is : ", uname);
            listpaymentspos.add(Float.valueOf(amountpaid));
            listmemberspos.add(uname);
        }
        crs.close();
        db2.close();

        db3 = this.openOrCreateDatabase("events", Context.MODE_PRIVATE, null);
        List<Float> listpaymentsneg = new ArrayList<Float>();
        List<String> listmembersneg = new ArrayList<String>();
        Cursor c = db3.rawQuery("SELECT * FROM " + evName + " WHERE paid < 0 or paid = 0 ORDER BY paid ASC", null);
        while (c.moveToNext()) {
            String amountpaid = c.getString(c.getColumnIndex("paid"));
            String uname = c.getString(c.getColumnIndex("name"));
            Log.e("The string is : ", uname);
            listpaymentsneg.add(Float.valueOf(amountpaid));
            listmembersneg.add(uname);
        }
        c.close();
        db3.close();

        for (int i = 0, j = 0; i < listmemberspos.size() && j < listmembersneg.size(); ) {
            Log.e("value of positive", listmemberspos.get(i));
            i++;
            Log.e("value of negative", listmembersneg.get(j));
            j++;
        }

        List<String> payBy = new ArrayList<String>();
        List<String> payTo = new ArrayList<String>();
        List<Float> amount = new ArrayList<Float>();

        Float[] pp = listpaymentspos.toArray(new Float[listpaymentspos.size()]);
        Float[] pn = listpaymentsneg.toArray(new Float[listpaymentsneg.size()]);
        String[] mp = listmemberspos.toArray(new String[listmemberspos.size()]);
        String[] mn = listmembersneg.toArray(new String[listmembersneg.size()]);

        for (int i = 0, j = 0; i < listmemberspos.size() && j < listmembersneg.size(); ) {
            Log.d("Inside loop", "Entered the loop");
            if (pp[i] > Math.abs(pn[j])) {
                pp[i] += pn[j];
                payBy.add(mn[j]);
                payTo.add(mp[i]);
                amount.add(Math.abs(pn[j]));
                Log.e("Pay payby", mn[j]);
                Log.e("Pay payto", mp[i]);
                Log.e("Pay amount", String.valueOf(Math.abs(pn[j])));
                pn[j] = null;
                j++;
            } else if (pp[i] < Math.abs(pn[j])) {
                pn[j] += pp[i];
                payBy.add(mn[j]);
                payTo.add(mp[i]);
                amount.add(Math.abs(pp[i]));
                Log.e("Pay payby", mn[j]);
                Log.e("Pay payto", mp[i]);
                Log.e("Pay amount", String.valueOf(pp[i]));
                pp[i] = null;
                i++;
            } else if (pp[i] == Math.abs(pn[j])) {
                pn[j] += pp[i];
                payBy.add(mn[j]);
                payTo.add(mp[i]);
                amount.add(Math.abs(pp[i]));
                Log.e("Pay payby", mn[j]);
                Log.e("Pay payto", mp[i]);
                Log.e("Pay amount", String.valueOf(pp[i]));
                pp[i] = null;
                i++;
                j++;
            }
        }

        updateDatabaseSettlement(payBy, payTo, amount);
        updateEndStatus();
    }


    public void updateEndStatus(){
        helper = new DataHelper(getBaseContext());
        db1= helper.getWritableDatabase();
        String query = "UPDATE event SET "+ DataHelper.EVENT_STATUS+ " = 'no' WHERE "+ DataHelper.EVENT_NAME +" = '"+evName+"'";
        db1.execSQL(query);
        Intent intent = new Intent(getBaseContext(),MainActivity.class);
        finish();
        startActivity(intent);
    }


    public void updateDatabaseSettlement(List<String> payFrom,List<String> payTo,List<Float> payAmount){
        DataHelper3 helper4 = new DataHelper3(getBaseContext());
        SQLiteDatabase db4;
        db4 = helper4.getWritableDatabase();
        ContentValues values;

        for(int i=0;i<payFrom.size();i++) {

            values = new ContentValues();
            values.put(DataHelper3.FROM,payFrom.get(i) );
            values.put(DataHelper3.TO, payTo.get(i));
            values.put(DataHelper3.AMOUNT, String.valueOf(payAmount.get(i)));
            db4.insert(evName, null, values);
        }
    }

    public void createSettlement(){
        DataHelper3 helper1 = new DataHelper3(getBaseContext());
        SQLiteDatabase dbtmp;
        dbtmp = helper1.getWritableDatabase();
        dbtmp.execSQL("CREATE TABLE " + evName + " (" + DataHelper3.FROM + " TEXT," + DataHelper3.TO + " TEXT," + DataHelper3.AMOUNT + " TEXT);"
        );
        helper1.close();
        dbtmp.close();
    }
    public void updateEventsDatabase(String tmpName){
        tmpAmounts = getListofpayments("payment", evName, "payment_amount");
        Float total= 0.0f;
        for(int i =0 ;i<tmpAmounts.size();i++){
            total += Float.valueOf(tmpAmounts.get(i));
        }

        Log.e("value of total",String.valueOf(total));

        DataHelper helper3 = new DataHelper(getBaseContext());
        SQLiteDatabase db3;
        db3 = helper3.getWritableDatabase();
        String query = "UPDATE event SET "+ DataHelper.AMOUNT_TOTAL+ " = '"+String.valueOf(total)+"' WHERE "+ DataHelper.EVENT_NAME +" = '"+tmpName+"'";
        db3.execSQL(query);

    }
}


