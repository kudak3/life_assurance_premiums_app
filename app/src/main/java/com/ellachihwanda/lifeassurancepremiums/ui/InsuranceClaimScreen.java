package com.ellachihwanda.lifeassurancepremiums.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ellachihwanda.lifeassurancepremiums.R;
import com.ellachihwanda.lifeassurancepremiums.controller.ApiClient;
import com.ellachihwanda.lifeassurancepremiums.model.Client;
import com.ellachihwanda.lifeassurancepremiums.model.InsuranceClaim;
import com.ellachihwanda.lifeassurancepremiums.model.Payment;
import com.ellachihwanda.lifeassurancepremiums.model.PaymentType;
import com.ellachihwanda.lifeassurancepremiums.model.Policy;
import com.ellachihwanda.lifeassurancepremiums.model.PolicyCoverage;
import com.ellachihwanda.lifeassurancepremiums.model.User;
import com.ellachihwanda.lifeassurancepremiums.model.dto.ClaimDto;
import com.ellachihwanda.lifeassurancepremiums.service.ClaimService;
import com.ellachihwanda.lifeassurancepremiums.service.PaymentService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsuranceClaimScreen extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedPreferences;
    Client client;
    PolicyCoverage currentCover;
    List<PolicyCoverage> coverageList;
    TextView txtPolicyNumber, txtPolicyDueDate, txtPolicyName, txtPolicyDescription, txtPremium;
    EditText etClaimDescription, etAmount;
    Button btnSubmitClaim;
    private ProgressDialog progressDialog;
    private Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim);




        progressDialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        currentCover = (PolicyCoverage) getIntent().getSerializableExtra("coverage");
        coverageList = (List<PolicyCoverage>) getIntent().getSerializableExtra("coverList");

        sharedPreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String userJson = sharedPreferences.getString("client", "");
        Gson gson = new Gson();
        client = gson.fromJson(userJson, Client.class);


        etClaimDescription = findViewById(R.id.claim_desc);
        etAmount = findViewById(R.id.claim_amount_pay);
        txtPremium = findViewById(R.id.cover_amount);
        txtPolicyDescription = findViewById(R.id.claim_policy_desc);
        txtPolicyDueDate = findViewById(R.id.due_date);
        txtPolicyName = findViewById(R.id.claim_policy_name);
        txtPolicyNumber = findViewById(R.id.claim_policy_number_pay);
        btnSubmitClaim = findViewById(R.id.submit_claim);
        spinner = findViewById(R.id.spinner);
        initCover();
        getCovers();
    }


    @SuppressLint("SetTextI18n")
    public void initCover() {

        System.out.println("====" + currentCover);
        txtPolicyNumber.setText(currentCover.getPolicyNumber());
        txtPolicyName.setText(currentCover.getPolicy().getName());
        txtPolicyDescription.setText(currentCover.getPolicy().getDescription());
        txtPremium.setText('$' + currentCover.getPolicy().getCoverageAmount().toString());


    }



    public void getCovers() {

        PolicyCoverage placeholderItem = new PolicyCoverage();
        placeholderItem.setPolicy(new Policy("Select A Different Policy"));
        placeholderItem.setPolicyNumber("");

        coverageList.add(0, placeholderItem);

        ArrayAdapter<PolicyCoverage> adapter = new ArrayAdapter<PolicyCoverage>(getApplicationContext(), android.R.layout.simple_spinner_item, coverageList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    //Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;

            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // If user change the default selection
                // First item is disable and it is used for hint
                if (position > 0) {

                    currentCover = (PolicyCoverage) parent.getSelectedItem();
                    initCover();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void submitClaim(View view) {
        progressDialog.setMessage("Submitting claim please wait...");
        showDialog();
        btnSubmitClaim.setEnabled(false);

        String description = etClaimDescription.getText().toString();
        Long amount = Long.parseLong(String.valueOf(etAmount.getText()));

        ClaimDto claimDto = new ClaimDto(currentCover.getPolicyNumber(), description, amount);

        ClaimService claimService = ApiClient.createService(ClaimService.class);
        Call<ResponseBody> call = claimService.makeClaim(claimDto);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Insurance Claim submitted successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), HistoryScreen.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to make a claim: " + response.code(), Toast.LENGTH_LONG).show();

                }
                btnSubmitClaim.setEnabled(true);
                hideDialog();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An error has occured: " + t.getMessage(), Toast.LENGTH_LONG).show();
                btnSubmitClaim.setEnabled(true);
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