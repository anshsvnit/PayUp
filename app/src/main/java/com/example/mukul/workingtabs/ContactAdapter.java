package com.example.mukul.workingtabs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.app.Activity;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {


    public List<String> tmpEvents= new ArrayList<String>();
    public List<String> tmpStatus= new ArrayList<String>();

    public ContactAdapter(List<String> listEvents, List<String> statusList)  {
        tmpEvents = listEvents;
        tmpStatus = statusList;
    }


    @Override
    public int getItemCount() {
        return tmpEvents.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {

        String ce = tmpEvents.get(i);
        Log.e("The string passed :",ce);
        contactViewHolder.eventName.setText(ce);
        String cs = tmpStatus.get(i);
        contactViewHolder.eStatus.setText(cs);

    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

   /* public void updateData(ArrayList<ViewModel> viewModels) {
       tmpEvents.clear();
        tmpStatus.clear();
        items.addAll(viewModels);
        notifyDataSetChanged();
    }*/

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        protected TextView eventName;
        protected TextView eStatus;

        public ContactViewHolder(View v) {
            super(v);
            eventName =  (TextView) v.findViewById(R.id.eventName);
            eStatus = (TextView) v.findViewById(R.id.eventStatus);
        }
    }
}