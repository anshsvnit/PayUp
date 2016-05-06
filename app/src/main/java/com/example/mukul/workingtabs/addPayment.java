package com.example.mukul.workingtabs;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class addPayment extends DialogFragment implements OnItemSelectedListener {

    public DataHelper2 helper;
    public DataHelper1 helper1;
    public SQLiteDatabase db, db1;
    private int numberMember;
    private List<String> list = new ArrayList<String>();
    private List<String> listedittext = new ArrayList<String>();
    private List<EditText> listInputs = new ArrayList<EditText>();
    Button btn, submit_but;
    String eventName, paymentNameString, paymentAmountString, paymentByString, selectedPayee;
    View v;
    CheckBox divideEqual;
    LinearLayout relativeLayout;
    EditText paymentName, paymentAmount;
    private android.widget.Spinner memberList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /** Inflating the layout for this fragment **/

        v = inflater.inflate(R.layout.activity_add_payment, container);
        btn = (Button) v.findViewById(R.id.close);
        submit_but = (Button) v.findViewById(R.id.submit);
        divideEqual = (CheckBox) v.findViewById(R.id.divide_checkbox);
        memberList = (android.widget.Spinner) v.findViewById(R.id.memberlist);
        paymentName = (EditText) v.findViewById(R.id.pName);
        paymentAmount = (EditText) v.findViewById(R.id.pamount);
        eventName = ((activeEvent) getActivity()).evName;
        relativeLayout = (LinearLayout) v.findViewById(R.id.listpayments);
        addItemsOnSpinner();
        memberList.setOnItemSelectedListener(this);

        for (int memberNumber = 0; memberNumber < numberMember; memberNumber++) {
            listedittext = getListofevents(eventName, "name");
            EditText tmpedit = new EditText(getActivity());
            tmpedit.setId(memberNumber);
            tmpedit.setInputType(2);
            tmpedit.setHint(listedittext.get(memberNumber));
            listInputs.add(tmpedit);
            LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            relativeLayout.addView(tmpedit, layoutParam);

        }

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                getDialog().dismiss();
            }
        });

        submit_but.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                paymentNameString = paymentName.getText().toString();
                paymentNameString = paymentNameString.replaceAll(" ", "_");
                paymentAmountString= paymentAmount.getText().toString();

                if (divideEqual.isChecked()) {
                    addPaymentDB();
                    helper1 = new DataHelper1(getActivity());
                    db1 = helper1.getWritableDatabase();

                    int paymentAmount = Integer.valueOf(paymentAmountString);
                    float perPerson = Float.valueOf(paymentAmount) / Float.valueOf(numberMember);

                    List<String> amountDetailNames = getListofevents(eventName, "name");
                    int position = amountDetailNames.indexOf(selectedPayee);
                    List<String> amountDetails = getListofevents(eventName, "paid");
                    List<String> changedDetails = new ArrayList<String>();


                    for (int x = 0; x < amountDetails.size(); x++) {
                        if (x == position) {
                            changedDetails.add(x, String.valueOf(Float.valueOf(amountDetails.get(x)) - perPerson + paymentAmount));
                        }

                        else {
                            changedDetails.add(x, String.valueOf(Float.valueOf(amountDetails.get(x)) - perPerson));
                        }
                        String query = "UPDATE " + eventName + " SET " + DataHelper1.AMOUNT_PAID + " = '" + changedDetails.get(x) + "' WHERE " + DataHelper1.CONTACT_NAME + " = '" + amountDetailNames.get(x) + "'";
                        db1.execSQL(query);
                    }

                    Toast.makeText(getContext(),
                            "Added the Payment", Toast.LENGTH_LONG).show();

                    db1.close();
                    helper1.close();
                }

                else {
                    if (checkTotal(paymentAmountString)) {
                        addPaymentDB();
                        helper1 = new DataHelper1(getActivity());
                        db1 = helper1.getWritableDatabase();
                        Float paymentAmount = Float.valueOf(paymentAmountString);
                        List<String> amountDetailNames = getListofevents(eventName, "name");
                        List<String> amountDetails = getListofevents(eventName, "paid");
                        List<String> changedDetails = new ArrayList<String>();
                        int position = amountDetailNames.indexOf(selectedPayee);

                        for (int memberNumber = 0; memberNumber < numberMember; memberNumber++) {
                            if (memberNumber == position) {
                                Float payeeamount;
                                payeeamount = (Float.valueOf(amountDetails.get(memberNumber))) - (Float.valueOf(listInputs.get(memberNumber).getText().toString())) + paymentAmount;
                                changedDetails.add(memberNumber, String.valueOf(payeeamount));
                            } else {
                                changedDetails.add(memberNumber, String.valueOf(Float.valueOf(amountDetails.get(memberNumber)) - Float.valueOf(listInputs.get(memberNumber).getText().toString())));
                            }

                            String query = "UPDATE " + eventName + " SET " + DataHelper1.AMOUNT_PAID + " = '" + changedDetails.get(memberNumber) + "' WHERE " + DataHelper1.CONTACT_NAME + " = '" + amountDetailNames.get(memberNumber) + "'";
                            db1.execSQL(query);

                        }
                        Toast.makeText(getContext(),
                                "Added the Payment", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getContext(),
                                "The total is not Proper. Cancelling Payment", Toast.LENGTH_LONG).show();
                    }
                }
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

    public List<String> getListofevents(String evName, String attribute) {
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

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, list); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        memberList.setAdapter(spinnerArrayAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        selectedPayee = parent.getItemAtPosition(position).toString();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        window.setLayout(width, height);
        window.setGravity(Gravity.CENTER);
    }

    public void addPaymentDB() {
        paymentNameString = paymentName.getText().toString();
        paymentAmountString = paymentAmount.getText().toString();
        paymentByString = selectedPayee;
        helper = new DataHelper2(getContext());

        db = helper.getWritableDatabase();
        Log.e("Helper Created", eventName);

        if (!eventName.equals("")) {
            ContentValues values = new ContentValues();

            values.put(DataHelper2.PAYMENT_NAME, paymentNameString);
            values.put(DataHelper2.PAYMENT_AMOUNT, paymentAmountString);
            values.put(DataHelper2.PAYMENT_BY, paymentByString);

            db.insert(eventName, null, values);

        } else {
            Toast.makeText(getContext(),
                    "There is error", Toast.LENGTH_LONG).show();
        }
        db.close();
        helper.close();
    }

    public boolean checkTotal(String payment) {
        int total = 0;
        for (int memberNumber = 0; memberNumber < numberMember; memberNumber++) {
            String tmpValue = listInputs.get(memberNumber).getText().toString();
            if(tmpValue.equalsIgnoreCase("")){
                return false;
            }
            total+= Integer.valueOf(tmpValue);
        }
        if(payment.equals(String.valueOf(total))){
            return true;
        }
        else
            return false;
    }
}