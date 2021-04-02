package com.ellachihwanda.lifeassurancepremiums.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ellachihwanda.lifeassurancepremiums.R;
import com.ellachihwanda.lifeassurancepremiums.model.InsuranceClaim;
import com.ellachihwanda.lifeassurancepremiums.model.Payment;

import java.util.List;

public class ClaimsAdapter extends RecyclerView.Adapter<ClaimsAdapter.ViewHolder> {
    List<InsuranceClaim> claims;
    Context context;

    public ClaimsAdapter(List<InsuranceClaim> claims, Context context) {
        this.claims = claims;
        this.context = context;
    }

    @NonNull
    @Override
    public ClaimsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.transaction_history,parent,false);
        return new ClaimsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClaimsAdapter.ViewHolder holder, int position) {
        InsuranceClaim claim = claims.get(0);
        holder.tvDescription.setText(claim.getDescription());
        holder.tvClaimDate.setText(claim.getDate().toString());
        holder.tvClaimStatus.setText(claim.getClaimStatus().getName());
        holder.tvClaimPolicy.setText(claim.getPolicy().getName());

    }

    @Override
    public int getItemCount() {
        return claims.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvClaimStatus,tvClaimDate,tvClaimPolicy,tvDescription;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvClaimStatus = itemView.findViewById(R.id.tvClaimStatus);
            tvClaimDate = itemView.findViewById(R.id.tvClaimDate);
            tvClaimPolicy = itemView.findViewById(R.id.tvClaimPolicy);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }
    }
}
