package com.ellachihwanda.lifeassurancepremiums.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ellachihwanda.lifeassurancepremiums.R;
import com.ellachihwanda.lifeassurancepremiums.controller.ApiClient;
import com.ellachihwanda.lifeassurancepremiums.model.Client;
import com.ellachihwanda.lifeassurancepremiums.model.InsuranceClaim;
import com.ellachihwanda.lifeassurancepremiums.model.Payment;
import com.ellachihwanda.lifeassurancepremiums.model.PolicyCoverage;
import com.ellachihwanda.lifeassurancepremiums.model.User;
import com.ellachihwanda.lifeassurancepremiums.service.ClaimService;
import com.ellachihwanda.lifeassurancepremiums.service.ClientService;
import com.ellachihwanda.lifeassurancepremiums.service.PaymentService;
import com.ellachihwanda.lifeassurancepremiums.utils.ClaimsAdapter;
import com.ellachihwanda.lifeassurancepremiums.utils.PaymentsAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ellachihwanda.lifeassurancepremiums.ui.DashBoard.MyPREFERENCES;

public class HistoryScreen extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private Client client = new Client();
    SharedPreferences sharedPreferences;
    TextView txtGreetings, txtPaymentsHeading, txtNoPayments, txtNoClaims, txtHelloText;


    RecyclerView recyclerView, rvClaims;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //-------------------------- Bottom Navigation ----------------------------------------------------------------------------------------
        //initialise and assign variables
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.history);
        //set itemListener on bottomNavigationItems
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.history:
                        return true;
                    case R.id.pay_policy:
                        startActivity(new Intent(getApplicationContext(), PayPremium.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), DashBoard.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), UserProfile.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.my_policies:
                        startActivity(new Intent(getApplicationContext(), PoliciesScreen.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }


        });

        //-----------------------------------------------------------------------------------------------------------------------------------


        progressDialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        sharedPreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String clientJson = sharedPreferences.getString("client", "");
        Gson gson = new Gson();
        client = gson.fromJson(clientJson, Client.class);


        recyclerView = findViewById(R.id.transaction_history_rvw);
        rvClaims = findViewById(R.id.claim_history_rvw);

        txtGreetings = findViewById(R.id.greetings_name);
        txtNoPayments = findViewById(R.id.no_payments);
        txtNoClaims = findViewById(R.id.no_claims);
        txtHelloText = findViewById(R.id.hello_text);

        initHistory();

    }

    private void initHistory() {
        showDialog();
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        String helloText = "HI";

        if (timeOfDay >= 0 && timeOfDay < 12) {
            helloText = "Good Morning";
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            helloText = "Good Afternoon";
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            helloText = "Good Evening";
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            helloText = "Good Night";
        }

        txtHelloText.setText(helloText);
        txtGreetings.setText(client.getFirstName() + " " + client.getLastName());
        getPaymentHistory();
        getClaimHistory();

    }

    private void getClaimHistory() {
        ClientService clientService = ApiClient.createService(ClientService.class);
        Call<List<InsuranceClaim>> call = clientService.getClaimHistory(client.getId());
        call.enqueue(new Callback<List<InsuranceClaim>>() {
            @Override
            public void onResponse(Call<List<InsuranceClaim>> call, Response<List<InsuranceClaim>> response) {

                if (response.isSuccessful()) {
                    System.out.println("======================================claims");
                    System.out.println(response.body());
                    List<InsuranceClaim> claims = response.body();
                    if (claims.isEmpty() || claims.size() == 0 || claims == null) {

                        txtNoClaims.setVisibility(View.VISIBLE);
                        hideDialog();


                        return;
                    }

                    ClaimsAdapter claimsAdapter = new ClaimsAdapter(response.body(), HistoryScreen.this);
                    rvClaims.setAdapter(claimsAdapter);
                    rvClaims.setLayoutManager(new LinearLayoutManager(HistoryScreen.this));
                    hideDialog();
                } else {
                    System.out.println("========================failed");

                }
            }

            @Override
            public void onFailure(Call<List<InsuranceClaim>> call, Throwable t) {
                System.out.println("================================");
                System.out.println(t.getMessage());
                Log.d("Error", t.getMessage(), t);
                hideDialog();

            }
        });

    }

    private void getPaymentHistory() {
        ClientService clientService = ApiClient.createService(ClientService.class);
        Call<List<Payment>> call = clientService.getPaymentsHistory(client.getId());
        call.enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                if (response.isSuccessful()) {
                    List<Payment> payments = response.body();
                    if (payments.isEmpty() || payments.size() == 0 || payments == null) {

                        txtNoPayments.setVisibility(View.VISIBLE);
                        hideDialog();

                        return;
                    }


                    PaymentsAdapter paymentsAdapter = new PaymentsAdapter(HistoryScreen.this, response.body());
                    recyclerView.setAdapter(paymentsAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(HistoryScreen.this));
                    hideDialog();
                }
            }

            @Override
            public void onFailure(Call<List<Payment>> call, Throwable t) {
                Toast.makeText(HistoryScreen.this, t.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();

            }
        });

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
