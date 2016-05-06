package com.example.mukul.workingtabs;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class OverEventAdapter extends RecyclerView.Adapter<OverEventAdapter.overEventViewHolder> {

    private ArrayList<String> tmpfrom = new ArrayList<String>();
    private ArrayList<String> tmpto = new ArrayList<String>();
    private ArrayList<String> tmpamount = new ArrayList<String>();

    public OverEventAdapter(ArrayList<String> listFrom, ArrayList<String> listTo, ArrayList<String> listAmount){
        tmpfrom = listFrom;
        tmpto = listTo;
        tmpamount = listAmount;
        Log.e("count of tmpfrom",String.valueOf(tmpfrom.size()));
    }

    @Override
    public int getItemCount() {
        return (tmpfrom.size());
    }

    @Override
    public void onBindViewHolder(overEventViewHolder contactViewHolder, int i) {

        String cf = tmpfrom.get(i);
        Log.e("The string passed :",cf);
        contactViewHolder.payFrom.setText(cf);
        String ct = tmpto.get(i);
        Log.e("The string cn is:",ct);
        contactViewHolder.payTo.setText(ct);
        String ca = tmpamount.get(i);
        Log.e("The string cn is:",ca);
        contactViewHolder.payAmount.setText(ca);
    }

    @Override
    public OverEventAdapter.overEventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout_over, viewGroup, false);

        return new overEventViewHolder(itemView);
    }

    public static class overEventViewHolder extends RecyclerView.ViewHolder {

        protected TextView payFrom;
        protected TextView payTo;
        protected TextView payAmount;

        public overEventViewHolder(View v) {
            super(v);
            payFrom =  (TextView) v.findViewById(R.id.pfrom);
            payTo = (TextView) v.findViewById(R.id.pto);
            payAmount= (TextView) v.findViewById(R.id.pamount);
        }
    }
}