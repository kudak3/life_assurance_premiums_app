package com.ellachihwanda.lifeassurancepremiums.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PoliciesScreen extends AppCompatActivity {
    public static ProgressDialog progressDialog;
    public static Client client;
    private PolicyCoverage currentCoverage;
    private RecyclerView rVMyPoliciesList, rvAvailablePolicies;
    private TextView txtHello, txtNoCovers, txtNoPolicies, txtAvailableHeading;
    PoliciesAdapter policiesAdapter;
    List<PolicyCoverage> coverageList;
    List<PolicyCoverage> myPolicyList = new ArrayList<>();
    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policies);

        //-------------------------- Bottom Navigation ----------------------------------------------------------------------------------------
        //initialise and assign variables
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.my_policies);
        //set itemListener on bottomNavigationItems
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.my_policies:
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
                    case R.id.history:
                        startActivity(new Intent(getApplicationContext(), HistoryScreen.class));
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
        String coverJson = sharedPreferences.getString("coverList", "");

        Type listType = new TypeToken<List<PolicyCoverage>>() {
        }.getType();
        myPolicyList = coverJson.isEmpty() ? new ArrayList<>() : gson.fromJson(coverJson, listType);
        System.out.println(myPolicyList);


        rVMyPoliciesList = findViewById(R.id.my_policies_list);
        rvAvailablePolicies = findViewById(R.id.available_policies_list);

        //link with xml

        txtNoCovers = findViewById(R.id.no_policy_covers);
        txtNoPolicies = findViewById(R.id.no_available_policies);
        txtHello = findViewById(R.id.hello_text);


        initPolicies();


    }

    public void initPolicies() {
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


        String helloText = salutation + " , " + client.getFirstName().toUpperCase() + " " + client.getLastName();
        txtHello.setText(helloText);

        progressDialog.setMessage("Loading Policies...");
        showDialog();
        getMyPolicies();
        getAvailablePolicies();


    }

    public void getMyPolicies() {
        System.out.println("============my policies");
        System.out.println(myPolicyList);

        if (myPolicyList == null || myPolicyList.isEmpty() || myPolicyList.size() == 0) {
            txtNoCovers.setVisibility(View.VISIBLE);
            hideDialog();
            return;
        }
        getCurrentCover();


    }

    public void getAvailablePolicies() {
        PolicyService policyService = ApiClient.createService(PolicyService.class);
        Call<List<Policy>> call = policyService.getPolicies();
        call.enqueue(new Callback<List<Policy>>() {
            @Override
            public void onResponse(@NotNull Call<List<Policy>> call, @NotNull Response<List<Policy>> response) {
                if (response.isSuccessful()) {
                    List<Policy> availablePolicies = response.body();

                    if (availablePolicies == null || availablePolicies.isEmpty() || availablePolicies.size() == 0) {
                        txtNoPolicies.setVisibility(View.VISIBLE);
                        hideDialog();
                        return;
                    }


                    policiesAdapter = new PoliciesAdapter(PoliciesScreen.this, response.body());
                    rvAvailablePolicies.setAdapter(policiesAdapter);
                    rvAvailablePolicies.setLayoutManager(new LinearLayoutManager(PoliciesScreen.this));
                    hideDialog();
                }

            }

            @Override
            public void onFailure(@NotNull Call<List<Policy>> call, @NotNull Throwable t) {
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

    private void getCurrentCover() {

        String userJson = sharedPreferences.getString("user", "");
        Gson gson = new Gson();
        User user = gson.fromJson(userJson, User.class);
        PolicyService policyService = ApiClient.createService(PolicyService.class);
        Call<List<PolicyCoverage>> call = policyService.getPolicyCovers(user.getId());
        call.enqueue(new Callback<List<PolicyCoverage>>() {
            @Override
            public void onResponse(@NotNull Call<List<PolicyCoverage>> call, @NotNull Response<List<PolicyCoverage>> response) {
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

                        CoverageAdapter coverageAdapter = new CoverageAdapter(getApplicationContext(), myPolicyList);
                        rVMyPoliciesList.setAdapter(coverageAdapter);
                        rVMyPoliciesList.setLayoutManager(new LinearLayoutManager(PoliciesScreen.this));


                    }


                } else {
                    // error response, no access to resource?
                    Toast.makeText(getApplicationContext(), response.code() + " Failed to load client details", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(@NotNull Call<List<PolicyCoverage>> call, @NotNull Throwable t) {

                hideDialog();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }
}