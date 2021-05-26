package com.ellachihwanda.lifeassurancepremiums.utils;

import android.annotation.SuppressLint;
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

import java.text.SimpleDateFormat;
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
        View view = inflater.inflate(R.layout.claims_history, parent, false);
        return new ClaimsAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ClaimsAdapter.ViewHolder holder, int position) {
         SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        InsuranceClaim claim = claims.get(position);
        holder.tvDescription.setText(claim.getDescription());
        holder.tvClaimDate.setText(sdf.format(claim.getDate()));
        holder.tvClaimStatus.setText(claim.getClaimStatus().getName());
        holder.tvClaimPolicy.setText(claim.getPolicyCoverage().getPolicy().getName());

    }

    @Override
    public int getItemCount() {
        return claims.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvClaimStatus, tvClaimDate, tvClaimPolicy, tvDescription,tvClaimAmount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvClaimStatus = itemView.findViewById(R.id.tvClaimStatus);
            tvClaimDate = itemView.findViewById(R.id.tvClaimDate);
            tvClaimPolicy = itemView.findViewById(R.id.tvClaimPolicy);
            tvDescription = itemView.findViewById(R.id.tvClaimDescription);
            tvClaimAmount = itemView.findViewById(R.id.tvClaimAmount);
        }
    }
}
