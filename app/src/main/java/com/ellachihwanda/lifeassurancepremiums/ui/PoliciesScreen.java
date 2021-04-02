package com.ellachihwanda.lifeassurancepremiums.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ellachihwanda.lifeassurancepremiums.R;
import com.ellachihwanda.lifeassurancepremiums.controller.ApiClient;
import com.ellachihwanda.lifeassurancepremiums.model.Client;
import com.ellachihwanda.lifeassurancepremiums.model.Policy;
import com.ellachihwanda.lifeassurancepremiums.model.PolicyCoverage;
import com.ellachihwanda.lifeassurancepremiums.model.User;
import com.ellachihwanda.lifeassurancepremiums.service.ClientService;
import com.ellachihwanda.lifeassurancepremiums.service.PolicyService;
import com.ellachihwanda.lifeassurancepremiums.utils.CoverageAdapter;
import com.ellachihwanda.lifeassurancepremiums.utils.PaymentsAdapter;
import com.ellachihwanda.lifeassurancepremiums.utils.PoliciesAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PoliciesScreen extends AppCompatActivity {
    public static ProgressDialog progressDialog;
    public static Client client;
    private RecyclerView rVMyPoliciesList, rvAvailablePolicies;
    private TextView tvMyPolicyHeading, txtHello;
    PoliciesAdapter policiesAdapter;
    List<PolicyCoverage> myPolicyList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policies);
        progressDialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        client = (Client) getIntent().getSerializableExtra("client");
        myPolicyList = (List<PolicyCoverage>) getIntent().getSerializableExtra("coverList");


        rVMyPoliciesList = findViewById(R.id.my_policies_list);
        rvAvailablePolicies = findViewById(R.id.available_policies_list);

        tvMyPolicyHeading = findViewById(R.id.label_heading);

        txtHello = findViewById(R.id.hello_text);
        initPolicies();


    }

    public void initPolicies() {
        String helloText = "Hello , " + client.getFirstName().toUpperCase();
        txtHello.setText(helloText);

        progressDialog.setMessage("Loading Policies...");
        showDialog();
        getMyPolicies();
        getAvailablePolicies();


    }

    public void getMyPolicies() {

        if (myPolicyList == null || myPolicyList.isEmpty()) {
            tvMyPolicyHeading.setVisibility(View.GONE);
            rVMyPoliciesList.setVisibility(View.GONE);

            return;
        }

        CoverageAdapter coverageAdapter = new CoverageAdapter(this, myPolicyList);
        rVMyPoliciesList.setAdapter(coverageAdapter);
        rVMyPoliciesList.setLayoutManager(new LinearLayoutManager(PoliciesScreen.this));


    }

    public void getAvailablePolicies() {
        PolicyService policyService = ApiClient.createService(PolicyService.class);
        Call<List<Policy>> call = policyService.getPolicies();
        call.enqueue(new Callback<List<Policy>>() {
            @Override
            public void onResponse(Call<List<Policy>> call, Response<List<Policy>> response) {
                if (response.isSuccessful()) {
                    System.out.println(response.body());

                    policiesAdapter = new PoliciesAdapter(PoliciesScreen.this, response.body());
                    rvAvailablePolicies.setAdapter(policiesAdapter);
                    rvAvailablePolicies.setLayoutManager(new LinearLayoutManager(PoliciesScreen.this));

                }
                hideDialog();
            }

            @Override
            public void onFailure(Call<List<Policy>> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(PoliciesScreen.this, t.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();

            }
        });
    }


    public static void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    public static void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}