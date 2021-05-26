package com.ellachihwanda.lifeassurancepremiums.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ellachihwanda.lifeassurancepremiums.R;
import com.ellachihwanda.lifeassurancepremiums.controller.ApiClient;
import com.ellachihwanda.lifeassurancepremiums.model.dto.CoverDto;
import com.ellachihwanda.lifeassurancepremiums.model.Policy;
import com.ellachihwanda.lifeassurancepremiums.model.PolicyCoverage;
import com.ellachihwanda.lifeassurancepremiums.service.PolicyService;
import com.ellachihwanda.lifeassurancepremiums.ui.PoliciesScreen;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ellachihwanda.lifeassurancepremiums.ui.PoliciesScreen.hideDialog;
import static com.ellachihwanda.lifeassurancepremiums.ui.PoliciesScreen.showDialog;

public class PoliciesAdapter extends RecyclerView.Adapter<PoliciesAdapter.ViewHolder> {
    List<Policy> policies;
    Context context;
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;


    public PoliciesAdapter(Context context, List<Policy> policies) {

        this.policies = policies;
        this.context = context;
    }

    @NonNull
    @Override
    public PoliciesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.availlable_policies, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PoliciesAdapter.ViewHolder holder, int position) {

        Policy policy = policies.get(position);
        holder.tvName.setText(policy.getName());
        holder.tvPremium.setText('$' + policy.getPremium().toString());
        holder.tvCoverAmount.setText('$' + policy.getCoverageAmount().toString());
        holder.tvDescription.setText(policy.getDescription());
        holder.btnJoinPolicy.setOnClickListener((view) -> {
            PoliciesScreen.progressDialog.setMessage("Joining Policy please wait...");
            showDialog();
            CoverDto cover = new CoverDto(PoliciesScreen.client, policies.get(position));
            PolicyService policyService = ApiClient.createService(PolicyService.class);
            Call<List<PolicyCoverage>> call = policyService.joinPolicy(cover);
            call.enqueue(new Callback<List<PolicyCoverage>>() {
                @Override
                public void onResponse(Call<List<PolicyCoverage>> call, Response<List<PolicyCoverage>> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Successfully joined the policy", Toast.LENGTH_LONG).show();
                        List<PolicyCoverage> list = response.body();
                        System.out.println("=================");
                        System.out.println(list);

                        sharedPreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Gson gson = new Gson();
                        editor.putString("coverList", gson.toJson(list));
                        editor.apply();

                        Intent intent = new Intent(context, PoliciesScreen.class);
                        ((Activity) context).finish();
                        context.startActivity(((Activity) context).getIntent());
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context, "Failed to join the policy: " + response.code(), Toast.LENGTH_LONG).show();

                    }
                    hideDialog();


                }

                @Override
                public void onFailure(Call<List<PolicyCoverage>> call, Throwable t) {

                    Toast.makeText(holder.btnJoinPolicy.getContext(), "An error has occured: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    hideDialog();

                }
            });
        });

    }

    @Override
    public int getItemCount() {
        return policies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvPremium, tvCoverAmount, tvDescription, btnJoinPolicy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvPremium = itemView.findViewById(R.id.tvPremium);
            tvCoverAmount = itemView.findViewById(R.id.tvCoverageAmount);
            tvDescription = itemView.findViewById(R.id.tvPolicyDescription);
            btnJoinPolicy = itemView.findViewById(R.id.btnJoin);

        }
    }


}
