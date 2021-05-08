package com.ellachihwanda.lifeassurancepremiums.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ellachihwanda.lifeassurancepremiums.R;
import com.ellachihwanda.lifeassurancepremiums.model.Policy;
import com.ellachihwanda.lifeassurancepremiums.model.PolicyCoverage;
import com.ellachihwanda.lifeassurancepremiums.ui.PayPremium;

import java.util.ArrayList;
import java.util.List;

public class CoverageAdapter extends RecyclerView.Adapter<CoverageAdapter.ViewHolder> {
    List<PolicyCoverage> coverageList;
    Context context;

    public CoverageAdapter(Context context, List<PolicyCoverage> coverageList) {
        this.coverageList = coverageList;
        this.context = context;
    }

    @NonNull
    @Override
    public CoverageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_policies, parent, false);
        return new CoverageAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CoverageAdapter.ViewHolder holder, int position) {
        System.out.println("==========list");
        System.out.println(coverageList.get(0));
        PolicyCoverage coverage = coverageList.get(position);
        holder.tvName.setText(coverage.getPolicy().getName());
        holder.tvPremium.setText('$' + coverage.getPolicy().getPremium().toString());
        holder.tvCoverAmount.setText('$' + coverage.getPolicy().getCoverageAmount().toString());
        holder.tvDescription.setText(coverage.getPolicy().getDescription());
        holder.btnPayPolicy.setOnClickListener((view) -> {
            Intent intent = new Intent(context, PayPremium.class);
            intent.putExtra("cover", coverage);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return coverageList == null ? 0 : coverageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvPremium, tvCoverAmount, tvDescription, btnPayPolicy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvCoverageName);
            tvPremium = itemView.findViewById(R.id.tvCPremium);
            tvCoverAmount = itemView.findViewById(R.id.tvCCoverageAmount);
            tvDescription = itemView.findViewById(R.id.tvCPolicyDescription);
            btnPayPolicy = itemView.findViewById(R.id.btnPayPolicy);
        }
    }

}
