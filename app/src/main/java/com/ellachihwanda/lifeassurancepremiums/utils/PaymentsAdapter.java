package com.ellachihwanda.lifeassurancepremiums.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ellachihwanda.lifeassurancepremiums.R;
import com.ellachihwanda.lifeassurancepremiums.model.Payment;

import java.util.List;

public class PaymentsAdapter extends RecyclerView.Adapter<PaymentsAdapter.ViewHolder> {

    //variables to hold our data

    List<Payment> payments;
    Context context;


    public PaymentsAdapter(Context context, List<Payment> payments){
        this.payments = payments;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.transaction_history,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Payment payment = payments.get(0);
        holder.tvLeading.setText(payment.getDescription());
        holder.tvDate.setText(payment.getDate().toString());
        holder.tvAmount.setText(payment.getAmount().toString());

    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvLeading,tvDate,tvAmount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvLeading = itemView.findViewById(R.id.tvLeading);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvAmount = itemView.findViewById(R.id.tvAmount);
        }
    }
}
