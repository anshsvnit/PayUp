package com.example.mukul.workingtabs;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HelloFragment extends DialogFragment {
    public final int RESULT_PICK_CONTACT = 8500;
    List<String> names = new ArrayList<String>();
    List<String> contacts = new ArrayList<String>();
    public int i = 0;
    Button btn,sub_but;
    View v;
    TextView tview1;
    EditText eventName;
    public DataHelper helper;
    public DataHelper1 helper1;
    public DataHelper2 helper2;
    public SQLiteDatabase db,db1,db2;

    public String eName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /** Inflating the layout for this fragment **/
        v = inflater.inflate(R.layout.activity_hello_fragment, container);
        btn=(Button)v.findViewById(R.id.close);
        sub_but = (Button)v.findViewById(R.id.submit);
        tview1=(TextView)v.findViewById(R.id.textview1);
        eventName = (EditText) v.findViewById(R.id.eventName);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            // TODO Auto-generated method stub
                getDialog().dismiss();
            }
        });


        Button button = (Button) v.findViewById(R.id.add_contact);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
            }
        });


        sub_but.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Add new Event

                eName = eventName.getText().toString();
                helper = new DataHelper(getContext());

                // getting database for reading/writing purpose
                db = helper.getWritableDatabase();
                Log.e("Helper Created", eName);
                Log.d("Value of i : ", Integer.toString(i));
                String currentDate = getDate();

                if (!eName.equals("")){
                ContentValues values = new ContentValues();

                values.put(DataHelper.EVENT_NAME, eName);
                values.put(DataHelper.EVENT_STATUS, "Yes");
                values.put(DataHelper.MEMBER_NO, (i+1));
                values.put(DataHelper.START_DATE,currentDate);
                values.put(DataHelper.AMOUNT_TOTAL,"0");

                    db.insert(helper.TABLE_NAME, null, values);
                }

            else {
                Toast.makeText(getContext(),
                        "There is error", Toast.LENGTH_LONG).show();
            }

                db.close();
                helper.close();

                helper1 = new DataHelper1(getContext());
                helper2 = new DataHelper2(getContext());
                db2 = helper2.getWritableDatabase();
                db1 = helper1.getWritableDatabase();

                Log.e("Helper Created", eName);
                Log.d("Value of i : ", Integer.toString(i));

                if (!eName.equals("") ) {
                    db1.execSQL("CREATE TABLE " + eName + " (" + DataHelper1.CONTACT_NAME + " TEXT," + DataHelper1.CONTACT_NO + " TEXT," + DataHelper1.AMOUNT_PAID + " TEXT);"
                    );
                    db2.execSQL("CREATE TABLE " + eName + " (" + DataHelper2.PAYMENT_NAME + " TEXT," + DataHelper2.PAYMENT_BY + " TEXT," + DataHelper2.PAYMENT_AMOUNT + " TEXT);"
                    );

                    ContentValues values;
                    values = new ContentValues();
                    values.put(DataHelper1.CONTACT_NAME, "SELF");
                    values.put(DataHelper1.CONTACT_NO, " ");
                    values.put(DataHelper1.AMOUNT_PAID, "0");

                        db1.insert(eName, null, values);

                    for (int j = 0; j < i; j++) {

                        String fname = names.get(j);
                        String contact = contacts.get(j);

                        values = new ContentValues();
                        values.put(DataHelper1.CONTACT_NAME, fname);
                        values.put(DataHelper1.CONTACT_NO, contact);
                        values.put(DataHelper1.AMOUNT_PAID, "0");

                        db1.insert(eName, null, values);

                        Toast.makeText(getContext(),
                                "Successfully added Event", Toast.LENGTH_LONG).show();
                    }
                }
                    else {
                        Toast.makeText(getContext(),
                                "There is error", Toast.LENGTH_LONG).show();
                    }

                    // closing the database connection
                db1.close();
                db2.close();
                helper1.close();
                helper2.close();
                getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
                                                     @Override
                                                     public void onDismiss(DialogInterface dialog) {
                                                         getActivity().finish();
                                                         startActivity(getActivity().getIntent());
                                                     }

                                                 } );

                getDialog().dismiss();
            }
        });

        return v;
    }

    public void onResume(){
        super.onResume();
        Window window = getDialog().getWindow();
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        window.setLayout(width, height);
        window.setGravity(Gravity.CENTER);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check whether the result is ok
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == android.app.Activity.RESULT_OK) {
            // Check for the request code, we might be usign multiple startActivityForReslut
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    contactPicked(data);
                    break;
            }
        } else {
            Log.e("MainActivity", "Failed to pick contact");
        }
    }

    /**
     * Query the Uri and read contact details. Handle the picked contact data.

     */
    private void contactPicked(Intent data) {
        Cursor cursor = null;
        String name = null;
        try {
            String phoneNo = null;
            // getData() method will have the Content Uri of the selected contact
            Uri uri = data.getData();
            //Query the content uri
            cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            // column index of the phone number
            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            // column index of the contact name
            int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            phoneNo = cursor.getString(phoneIndex);

            name = cursor.getString(nameIndex);
            names.add(name);
            contacts.add(phoneNo);
            i++;
            // Set the value to the textviews
                        tview1.append(name);
                        tview1.append("\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDate(){
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);
        return reportDate;
    }
}

