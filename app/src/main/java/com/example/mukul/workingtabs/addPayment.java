package com.example.mukul.workingtabs;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class addPayment extends DialogFragment implements OnItemSelectedListener{

    Button btn,submit_but;
    String eventName,paymentNameString,paymentAmountString,paymentByString,selectedPayee;
    View v;
    EditText paymentName,paymentAmount;
    public DataHelper2 helper;
    public DataHelper1 helper1;
    public SQLiteDatabase db,db1;
    private android.widget.Spinner memberList;
    public int numberMember;
    public List<String> list = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /** Inflating the layout for this fragment **/
        v = inflater.inflate(R.layout.activity_add_payment, container);
        btn = (Button) v.findViewById(R.id.close);
        submit_but = (Button) v.findViewById(R.id.submit);
        memberList = (android.widget.Spinner) v.findViewById(R.id.memberlist);
        paymentName = (EditText) v.findViewById(R.id.pName);
        paymentAmount = (EditText) v.findViewById(R.id.pamount);
        eventName = ((activeEvent)getActivity()).evName;

        addItemsOnSpinner();
        memberList.setOnItemSelectedListener(this);


        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                getDialog().dismiss();
            }
        });

        submit_but.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                //Adding the new event to event list.

                paymentNameString = paymentName.getText().toString();
                paymentAmountString = paymentAmount.getText().toString();
                paymentByString = selectedPayee;
                helper = new DataHelper2(getContext());
                // getting database for reading/writing purpose
                db = helper.getWritableDatabase();
                Log.e("Helper Created", eventName);

                if (!eventName.equals("")) {
                    ContentValues values = new ContentValues();

                    values.put(DataHelper2.PAYMENT_NAME, paymentNameString);
                    values.put(DataHelper2.PAYMENT_AMOUNT, paymentAmountString);
                    values.put(DataHelper2.PAYMENT_BY,paymentByString);

                    // insert query will insert data in database with
                    // contentValues pair.
                    db.insert(eventName, null, values);

                    Toast.makeText(getContext(),
                            "added db", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(getContext(),
                            "There is error", Toast.LENGTH_LONG).show();
                }

                db.close();
                helper.close();

                helper1 = new DataHelper1(getActivity());
                db1 = helper1.getWritableDatabase();

                int paymentAmount = Integer.valueOf(paymentAmountString);
                float perPerson = (Float.valueOf(paymentAmount)/Float.valueOf(numberMember));

                List<String> amountDetailNames = getListofevents(eventName, "name");
                int position = amountDetailNames.indexOf(selectedPayee);
                List<String> amountDetails = getListofevents(eventName, "paid");
                List<String> changedDetails = new ArrayList<String>();


                for(int x=0; x< amountDetails.size(); x++){
                if(x == position){
                    changedDetails.add(x, String.valueOf(Float.valueOf(amountDetails.get(x)) - perPerson + paymentAmount));
                }
                    else{
                    changedDetails.add(x,String.valueOf(Float.valueOf(amountDetails.get(x)) - perPerson));

                }

                    String query = "UPDATE "+ eventName+ " SET " + DataHelper1.AMOUNT_PAID + " = '"+ changedDetails.get(x)+"' WHERE "+DataHelper1.CONTACT_NAME+" = '"+ amountDetailNames.get(x)+"'";
                    db.execSQL(query);
                }

                Toast.makeText(getContext(),
                        "updated DB db", Toast.LENGTH_LONG).show();


                db1.close();
                helper1.close();
                //Refresh Parent activity on closure
                getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        getActivity().finish();
                        startActivity(getActivity().getIntent());
                    }

                });

                getDialog().dismiss();

            }
        });

        return v;
    }


    public List<String> getListofevents(String evName,String attribute) {
        this.db = getActivity().openOrCreateDatabase("events", Context.MODE_PRIVATE, null);
        List<String> array = new ArrayList<String>();
        Cursor crs = db.rawQuery("SELECT * FROM " + evName, null);

        while (crs.moveToNext()) {
            String uname = crs.getString(crs.getColumnIndex(attribute));
            Log.e("The string is : ", uname);
            array.add(uname);
            }
        crs.close();
        numberMember = array.size();
        return array;
        }

    public void addItemsOnSpinner() {

        list = new ArrayList<String>();
        list = getListofevents(eventName, "name");

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter (getActivity(), android.R.layout.simple_spinner_item, list); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        memberList.setAdapter(spinnerArrayAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        selectedPayee = parent.getItemAtPosition(position).toString();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


    public void onResume(){
        super.onResume();
        Window window = getDialog().getWindow();
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        window.setLayout(width,height);
        window.setGravity(Gravity.CENTER);
    }


}