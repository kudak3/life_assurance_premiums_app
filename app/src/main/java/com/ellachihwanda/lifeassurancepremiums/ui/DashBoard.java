package com.ellachihwanda.lifeassurancepremiums.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
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

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoard extends AppCompatActivity {
    private Client client;
    public static User user;
    private PolicyCoverage currentCoverage;
    private ProgressDialog progressDialog;
     List<PolicyCoverage> coverageList;

    TextView txtHello, txtPolicyNumber, txtDueDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        progressDialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        user = (User) getIntent().getSerializableExtra("user");


        //hook to ui
        txtHello = findViewById(R.id.hello_text);
        txtPolicyNumber = findViewById(R.id.policy_number);
        txtDueDate = findViewById(R.id.due_date);

            //populate view with details
        initDashBoard();




    }

    public void initDashBoard() {
        String helloText = "Hello , " + user.getFirstName().toUpperCase();
        txtHello.setText(helloText);

        PolicyService policyService = ApiClient.createService(PolicyService.class);
        Call<List<PolicyCoverage>> call = policyService.getPolicyCovers(user.getId());
        call.enqueue(new Callback<List<PolicyCoverage>>() {
            @Override
            public void onResponse(Call<List<PolicyCoverage>> call, Response<List<PolicyCoverage>> response) {
                hideDialog();
                coverageList = response.body();
                System.out.println("######----" + response.code());


                if (response.isSuccessful()) {
                    client = coverageList.get(0).getClient();
                    System.out.println("--" + client);

                    if ( coverageList.size() != 0) {

                        currentCoverage = coverageList.get(0);

                        String dueDate = currentCoverage.getDate().toString();
                        txtPolicyNumber.setText(currentCoverage.getPolicyNumber());
                        txtDueDate.setText(dueDate);
                    }


                } else {
                    // error response, no access to resource?
                    Toast.makeText(getApplicationContext(), response.code() + " Failed to load client details", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<PolicyCoverage>> call, Throwable t) {

                hideDialog();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });


    }

    public void callPayments(View view) {
        Intent intent = new Intent(this, PayPremium.class);
        intent.putExtra("cover", currentCoverage);
        startActivity(intent);
    }

    public void showProfile(View view) {
        Intent intent = new Intent(this, UserProfile.class);
        intent.putExtra("client", client);
        startActivity(intent);
    }

    public void showHistory(View view) {
        Intent intent = new Intent(this, HistoryScreen.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void showPolicies(View view) {
        Intent intent = new Intent(this, PoliciesScreen.class);
        intent.putExtra("client", client);
        intent.putExtra("coverList", (Serializable) coverageList);
        startActivity(intent);
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}