package com.example.mukul.workingtabs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.app.Activity;
import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    public List<String> arrayevent= new ArrayList<String>();
    public List<String> arraystatus= new ArrayList<String>();
    public getList tmp;


    public ContactAdapter() {

        tmp = new getList();
        arrayevent = tmp.getListofevents();
        arraystatus = tmp.getListofstatus();
    }


    @Override
    public int getItemCount() {
        return arrayevent.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        String ce = arrayevent.get(i);
        contactViewHolder.eventName.setText(ce);
        String cs = arraystatus.get(i);
        contactViewHolder.eventName.setText(cs);

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
        protected TextView estatus;

        public ContactViewHolder(View v) {
            super(v);
            eventName =  (TextView) v.findViewById(R.id.eventName);
            estatus = (TextView) v.findViewById(R.id.eventStatus);
        }
    }
}