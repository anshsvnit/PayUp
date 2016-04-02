package com.example.mukul.workingtabs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class HelloFragment extends DialogFragment {
    public final int RESULT_PICK_CONTACT = 8500;
    List<String> names = new ArrayList<String>();
    List<String> contacts = new ArrayList<String>();
    public int i = 0;
    Button btn;
    Button sub_but;
    View v;
    TextView tview1;
    EditText eventName;
    public DataHelper helper;
    public DataHelper1 helper1;
    public SQLiteDatabase db,db1;

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
                // TODO Auto-generated method stub

                //Adding the new event to event list.

                eName = eventName.getText().toString();
                helper = new DataHelper(getContext());

                // getting database for reading/writing purpose
                db = helper.getWritableDatabase();
                Log.e("Helper Created", eName);
                Log.d("Value of i : ", Integer.toString(i));

                if (!eName.equals("") ) {
                ContentValues values = new ContentValues();

                values.put(DataHelper.EVENT_NAME, eName);
                values.put(DataHelper.EVENT_STATUS, "Yes");

                // insert query will insert data in database with
                // contentValues pair.
                     db.insert(helper.TABLE_NAME, null, values);

                Toast.makeText(getContext(),
                        "added db", Toast.LENGTH_LONG).show();


            }

            else {

                Toast.makeText(getContext(),
                        "There is error", Toast.LENGTH_LONG).show();
            }

                db.close();
                helper.close();

                //Creating the table of event added

                helper1 = new DataHelper1(getContext());
                db1 = helper1.getWritableDatabase();

                Log.e("Helper Created", eName);
                Log.d("Value of i : ", Integer.toString(i));


                db1.execSQL("CREATE TABLE " + eName + " ("+ DataHelper1.CONTACT_NAME+ " TEXT,"+ DataHelper1.CONTACT_NO +" TEXT);"
                );

                Log.e("TABLE_NAME", eName);

                // getting data from edit text to string variables
                for (int j = 0; j < i; j++) {

                    String fname = names.get(j);
                    String contact = contacts.get(j);

                    // checking for empty fields
                    if (!fname.equals("") ) {

                        // contentValues will add data into key value pair which
                        // will later store in db
                        ContentValues values = new ContentValues();

                        values.put(DataHelper1.CONTACT_NAME, fname);
                        values.put(DataHelper1.CONTACT_NO, contact);

                        // insert query will insert data in database with
                        // contentValues pair.
                        db1.insert(eName, null, values);

                        Toast.makeText(getContext(),
                                "added db1", Toast.LENGTH_LONG).show();

                    }

                    else {

                        Toast.makeText(getContext(),
                                "There is error", Toast.LENGTH_LONG).show();
                    }

                    // closing the database connection

                }
                db1.close();
                helper1.close();

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
     *
     * @param data
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
    
}

