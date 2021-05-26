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

import java.text.SimpleDateFormat;
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
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        Payment payment = payments.get(position);
        holder.tvPaymentType.setText(payment.getPaymentType().getName());
        holder.tvDate.setText(sdf.format(payment.getDate()));
        holder.tvAmount.setText('$'+payment.getAmount().toString());
        holder.tvDescription.setText(payment.getDescription());
        holder.tvAccountNumber.setText(payment.getAccountNumber());

    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvPaymentType,tvDate,tvAmount,tvDescription,tvAccountNumber;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPaymentType = itemView.findViewById(R.id.tvPaymentType);
            tvDate = itemView.findViewById(R.id.tvTransactionDate);
            tvAmount = itemView.findViewById(R.id.tvPayment);
            tvDescription = itemView.findViewById(R.id.tvPaymentDescription);
            tvAccountNumber = itemView.findViewById(R.id.tvAccountNumber);

        }
    }
}
