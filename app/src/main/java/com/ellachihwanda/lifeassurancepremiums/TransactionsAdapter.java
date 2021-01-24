package com.ellachihwanda.lifeassurancepremiums;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.zip.Inflater;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.ViewHolder> {

    //variables to hold our data
    String[] data1,data2,data3;
    Context context;

    public TransactionsAdapter(Context context, String[] s1,String[] s2,String[] s3){

        data1 = s1;
        data2 = s2;
        data3 = s3;
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
        holder.tvLeading.setText(data1[position]);
        holder.tvDetails.setText(data2[position]);
        holder.tvAmount.setText(data3[position]);

    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvLeading,tvDetails,tvAmount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvLeading = itemView.findViewById(R.id.tvLeading);
            tvDetails = itemView.findViewById(R.id.tvDetails);
            tvAmount = itemView.findViewById(R.id.tvAmount);
        }
    }
}
