package com.ellachihwanda.lifeassurancepremiums.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.ellachihwanda.lifeassurancepremiums.R;
import com.ellachihwanda.lifeassurancepremiums.controller.ApiClient;
import com.ellachihwanda.lifeassurancepremiums.model.InsuranceClaim;
import com.ellachihwanda.lifeassurancepremiums.model.Payment;
import com.ellachihwanda.lifeassurancepremiums.model.User;
import com.ellachihwanda.lifeassurancepremiums.service.ClaimService;
import com.ellachihwanda.lifeassurancepremiums.service.PaymentService;
import com.ellachihwanda.lifeassurancepremiums.utils.PaymentsAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryScreen extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private List<Payment> payments;
    private List<InsuranceClaim> claims;
    private User user;


    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        progressDialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        user = (User) getIntent().getSerializableExtra("user");

        initHistory();

        recyclerView = findViewById(R.id.transaction_history_rvw);
        PaymentsAdapter paymentsAdapter = new PaymentsAdapter(this, payments);
        recyclerView.setAdapter(paymentsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initHistory() {
        showDialog();
        getPaymentHistory();
        getClaimHistory();

    }

    private void getClaimHistory() {
        ClaimService claimService = ApiClient.createService(ClaimService.class);
        Call<List<InsuranceClaim>> call = claimService.getClaimHistory();
        call.enqueue(new Callback<List<InsuranceClaim>>() {
            @Override
            public void onResponse(Call<List<InsuranceClaim>> call, Response<List<InsuranceClaim>> response) {
                if (response.isSuccessful()) {
                    claims = response.body();
                    hideDialog();
                }
            }

            @Override
            public void onFailure(Call<List<InsuranceClaim>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();

            }
        });

    }

    private void getPaymentHistory() {
        PaymentService paymentService = ApiClient.createService(PaymentService.class);
        Call<List<Payment>> call = paymentService.getPaymentsHistory();
        call.enqueue(new Callback<List<Payment>>() {
            @Override
            public void onResponse(Call<List<Payment>> call, Response<List<Payment>> response) {
                if (response.isSuccessful()) {
                    payments = response.body();
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
