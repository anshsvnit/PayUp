package com.example.mukul.workingtabs;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    ArrayList<String> tmpEvents = new ArrayList<String>();
    ArrayList<String> tmpStatus = new ArrayList<String>();
    ArrayList<String> MemberNumber = new ArrayList<String>();
    ArrayList<String> tmpMoney = new ArrayList<String>();
    ArrayList<String> tmpDates = new ArrayList<String>();

    public ContactAdapter(ArrayList<String> listEvents,ArrayList<String> listStatus,ArrayList<String> listMembers,ArrayList<String> listMoney,ArrayList<String> listDates)  {
        tmpEvents = listEvents;
        tmpStatus = listStatus;
        MemberNumber = listMembers;
        tmpMoney = listMoney;
        tmpDates = listDates;
        Log.e("count of tmpEvents",String.valueOf(listEvents.size()));
    }

    @Override
    public int getItemCount() {
        return (tmpEvents.size());
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {

        String ce = tmpEvents.get(i);
        Log.e("The string passed :",ce);
        contactViewHolder.eventName.setText(ce);
        String cs = tmpStatus.get(i);
        if (cs.equalsIgnoreCase("Yes")) {
            Log.e("The string cs is:", cs);
            contactViewHolder.eStatus.setText("Active");
        }
        else if (cs.equalsIgnoreCase("no")) {
                Log.e("The string cs is:", cs);
                contactViewHolder.eStatus.setText("Settled");
        }
        String cn = MemberNumber.get(i);
        Log.e("The string cn is:",cn);
        contactViewHolder.eventMembers.setText(cn + " Members");
        String cm = tmpMoney.get(i);
        Log.e("The string cn is:","Total Amount : "+ cm);
        contactViewHolder.amount.setText(cm);
        String cd = tmpDates.get(i);
        Log.e("The string cn is:",cd);
        contactViewHolder.sdate.setText(cd);

    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        protected TextView eventName;
        protected TextView eStatus;
        protected  TextView eventMembers;
        protected TextView amount;
        protected TextView sdate;

        public ContactViewHolder(View v) {
            super(v);
            eventName =  (TextView) v.findViewById(R.id.eventName);
            eStatus = (TextView) v.findViewById(R.id.eventStatus);
            eventMembers= (TextView) v.findViewById(R.id.eventMembers);
            amount = (TextView) v.findViewById(R.id.amount);
            sdate = (TextView) v.findViewById(R.id.sdate);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    if (eStatus.getText().toString().equalsIgnoreCase("Active")) {
                        intent = new Intent(v.getContext(), activeEvent.class);
                    }
                    else {
                        intent = new Intent(v.getContext(), over_event.class);
                    }
                    intent.putExtra("eventtext", eventName.getText().toString());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}