package com.ellachihwanda.lifeassurancepremiums.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ellachihwanda.lifeassurancepremiums.R;
import com.ellachihwanda.lifeassurancepremiums.controller.ApiClient;
import com.ellachihwanda.lifeassurancepremiums.model.Client;
import com.ellachihwanda.lifeassurancepremiums.model.PolicyCoverage;
import com.ellachihwanda.lifeassurancepremiums.model.User;
import com.ellachihwanda.lifeassurancepremiums.service.PolicyService;
import com.ellachihwanda.lifeassurancepremiums.ui.auth.Login;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    SharedPreferences sharedPreferences;


    TextView txtHello, txtPolicyNumber, txtDueDate, txtPremiumAmount;
    public static final String MyPREFERENCES = "MyPrefs";
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        //-------------------------- Bottom Navigation ----------------------------------------------------------------------------------------
        //initialise and assign variables
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        //set itemListener on bottomNavigationItems
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        return true;
                    case R.id.pay_policy:
                        startActivity(new Intent(getApplicationContext(), PayPremium.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.history:
                        startActivity(new Intent(getApplicationContext(), HistoryScreen.class));
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

        //get values from preferences
        sharedPreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String userJson = sharedPreferences.getString("user", "");

        Gson gson = new Gson();
        user = gson.fromJson(userJson, User.class);


        //link with ui
        txtHello = findViewById(R.id.hello_text);
        txtPolicyNumber = findViewById(R.id.policy_number);
        txtDueDate = findViewById(R.id.due_date);
        txtPremiumAmount = findViewById(R.id.premium_amount);


        //populate view with details
        initDashBoard();


    }

    public void initDashBoard() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        String salutation = "HI";

        if (timeOfDay >= 0 && timeOfDay < 12) {
            salutation = "Good Morning";
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            salutation = "Good Afternoon";
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            salutation = "Good Evening";
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            salutation = "Good Night";
        }


        String helloText = salutation + " , " + user.getFirstName().toUpperCase();
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

                    //storing our values in preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    editor.putString("client", gson.toJson(client));
                    editor.apply();

                    if (coverageList.size() != 0) {

                        currentCoverage = coverageList.get(0);

                        editor.putString("cover", gson.toJson(currentCoverage));
                        editor.putString("coverList", gson.toJson(coverageList));
                        editor.apply();

                        String dueDate = sdf.format(currentCoverage.getDate());
                        txtPolicyNumber.setText(currentCoverage.getPolicyNumber());
                        txtDueDate.setText(dueDate);
                        txtPremiumAmount.setText("$ " + currentCoverage.getPolicy().getPremium().toString());
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
        startActivity(intent);
    }

    public void showHistory(View view) {
        Intent intent = new Intent(this, HistoryScreen.class);
        startActivity(intent);
    }

    public void showPolicies(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        Intent intent = new Intent(this, PoliciesScreen.class);
        intent.putExtra("client", client);


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

    public void showClaim(View view) {
        Intent intent = new Intent(this, InsuranceClaimScreen.class);
        intent.putExtra("coverage", currentCoverage);
        intent.putExtra("coverList", (Serializable) coverageList);
        startActivity(intent);
    }

    public void logout(View view) {
        getApplicationContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE).edit().clear().apply();
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

}