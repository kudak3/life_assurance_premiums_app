package com.ellachihwanda.lifeassurancepremiums.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ellachihwanda.lifeassurancepremiums.R;
import com.ellachihwanda.lifeassurancepremiums.controller.ApiClient;
import com.ellachihwanda.lifeassurancepremiums.model.Client;
import com.ellachihwanda.lifeassurancepremiums.model.PolicyCoverage;
import com.ellachihwanda.lifeassurancepremiums.model.User;
import com.ellachihwanda.lifeassurancepremiums.service.ClientService;
import com.ellachihwanda.lifeassurancepremiums.service.UserService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ellachihwanda.lifeassurancepremiums.ui.DashBoard.MyPREFERENCES;

public class UserProfile extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private Client client;
    TextView txtFullName, txtMemberId, txtClaimsCount, txtActivePolicies;
    EditText etFirstName, etLastName, etEmail, etPhoneNumber, etHomeAddress;
    Button btnUpdate;
    Integer activePolicies;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //-------------------------- Bottom Navigation ----------------------------------------------------------------------------------------
        //initialise and assign variables
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        //set itemListener on bottomNavigationItems
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.profile:
                        return true;
                    case R.id.pay_policy:
                        startActivity(new Intent(getApplicationContext(), PayPremium.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), DashBoard.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.history:
                        startActivity(new Intent(getApplicationContext(), HistoryScreen.class));
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

        activePolicies = getIntent().getIntExtra("policies", 0);
        txtFullName = findViewById(R.id.fullname_field);
        txtMemberId = findViewById(R.id.memberId_field);
        txtActivePolicies = findViewById(R.id.active_policies_label);
        txtClaimsCount = findViewById(R.id.claims_count);
        etFirstName = findViewById(R.id.first_name_profile);
        etLastName = findViewById(R.id.last_name_profile);
        etEmail = findViewById(R.id.email_profile);
        etPhoneNumber = findViewById(R.id.phone_profile);
        etHomeAddress = findViewById(R.id.home_address_profile);
        btnUpdate = findViewById(R.id.btnUpdate);

        initProfile();
    }

    public void initProfile() {

        int claimsCount = client.getClaims().size();
        String fullName = client.getFirstName() + " " + client.getLastName();
        txtFullName.setText(fullName);
        txtMemberId.setText(client.getEmail());
        etFirstName.setText(client.getFirstName());
        etLastName.setText(client.getLastName());
        etPhoneNumber.setText(client.getPhoneNumber());
        etEmail.setText(client.getEmail());
        txtActivePolicies.setText(activePolicies.toString());
        txtClaimsCount.setText(String.valueOf(claimsCount));
        etHomeAddress.setText(client.getHomeAddress());

    }

    public void update(View view) {

        if (!validate()) {
            return;
        } else {
            showDialog();
            String firstName = etFirstName.getText().toString();
            String lastName = etLastName.getText().toString();
            String email = etEmail.getText().toString();
            String phoneNumber = etPhoneNumber.getText().toString();
            String homeAddress = etHomeAddress.getText().toString();


            client.setFirstName(firstName);
            client.setLastName(lastName);
            client.setPhoneNumber(phoneNumber);
            client.setEmail(email);
            client.setHomeAddress(homeAddress);
            ClientService clientService = ApiClient.createService(ClientService.class);
            Call<Client> call = clientService.updateClientProfile(client);
            call.enqueue(new Callback<Client>() {
                @Override
                public void onResponse(Call<Client> call, Response<Client> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Client Details updated successfully", Toast.LENGTH_LONG).show();
                        client = response.body();
                    } else {
                        // error response, no access to resource?
                        Toast.makeText(getApplicationContext(), "Details successfully updated", Toast.LENGTH_LONG).show();

                        btnUpdate.setEnabled(true);
                    }
                    hideDialog();
                }

                @Override
                public void onFailure(Call<Client> call, Throwable t) {
                    System.out.println(t.getMessage());

                }
            });
        }

    }

    public boolean validate() {
        boolean valid = true;
        String phoneNumber = etPhoneNumber.getText().toString();
        String firstName = etFirstName.getText().toString();
        String lastName = etLastName.getText().toString();
        String email = etEmail.getText().toString();


        if (firstName.isEmpty()) {
            etFirstName.setError("enter a valid firstName");
            valid = false;
        } else {
            etFirstName.setError(null);
        }

        if (lastName.isEmpty()) {
            etLastName.setError("enter a valid lastName");
            valid = false;
        } else {
            etLastName.setError(null);
        }

        if (phoneNumber.isEmpty()) {
            etPhoneNumber.setError("enter a valid phoneNumber");
            valid = false;
        } else {
            etPhoneNumber.setError(null);
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("enter a valid email");
            valid = false;
        } else {
            etEmail.setError(null);
        }


        return valid;
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