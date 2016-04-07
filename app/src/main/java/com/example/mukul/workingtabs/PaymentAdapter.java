package com.example.mukul.workingtabs;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anshul on 3/4/16.
 */
public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.PaymentViewHolder> {

    public List<String> paymentsNames= new ArrayList<String>();
    public List<String> paymentsBy= new ArrayList<String>();
    public List<String> paymentsAmount= new ArrayList<String>();

    public PaymentAdapter(List<String> pNameList, List<String> pByList, List<String> pAmountList)  {
        paymentsNames= pNameList;
        paymentsBy = pByList;
        paymentsAmount= pAmountList;
    }


    @Override
    public int getItemCount() {
        return paymentsAmount.size();
    }

    @Override
    public void onBindViewHolder(PaymentViewHolder paymentViewHolder, int i) {

        String ce = paymentsNames.get(i);
        Log.e("The string passed :", ce);
        paymentViewHolder.pName.setText(ce);
        String cs = paymentsBy.get(i);
        paymentViewHolder.pBy.setText(cs);
        String ca =paymentsAmount.get(i);
        paymentViewHolder.pamount.setText(ca);

    }

    @Override
    public PaymentViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout_payment, viewGroup, false);

        return new PaymentViewHolder(itemView);
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder {

        protected TextView pName;
        protected TextView pBy;
        protected TextView pamount;


        public PaymentViewHolder(View v) {
            super(v);
            pName =  (TextView) v.findViewById(R.id.pName);
            pBy = (TextView) v.findViewById(R.id.pby);
            pamount =(TextView) v.findViewById(R.id.pamount);
        }
    }


}
