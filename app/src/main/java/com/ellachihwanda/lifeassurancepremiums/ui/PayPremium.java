package com.ellachihwanda.lifeassurancepremiums.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ellachihwanda.lifeassurancepremiums.R;
import com.ellachihwanda.lifeassurancepremiums.controller.ApiClient;
import com.ellachihwanda.lifeassurancepremiums.model.Client;
import com.ellachihwanda.lifeassurancepremiums.model.Payment;
import com.ellachihwanda.lifeassurancepremiums.model.PaymentType;
import com.ellachihwanda.lifeassurancepremiums.model.PolicyCoverage;
import com.ellachihwanda.lifeassurancepremiums.model.dto.CoverDto;
import com.ellachihwanda.lifeassurancepremiums.service.PaymentService;
import com.ellachihwanda.lifeassurancepremiums.service.PolicyService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ellachihwanda.lifeassurancepremiums.ui.DashBoard.MyPREFERENCES;


public class PayPremium extends AppCompatActivity {
    private PolicyCoverage coverage;
    EditText etAccountNumber, etPremium;
    TextView txtPolicyNumber, txtPolicyDueDate, txtPolicyName, txtPolicyDescription, txtPremium;
    Button btnCompletePayment;
    private Spinner spinner;
    PaymentType paymentType;
    List<PaymentType> paymentMethods = new ArrayList<>();
    private ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_premium);

        //-------------------------- Bottom Navigation ----------------------------------------------------------------------------------------
        //initialise and assign variables
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.pay_policy);
        //set itemListener on bottomNavigationItems
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.pay_policy:
                        return true;
                    case R.id.history:
                        startActivity(new Intent(getApplicationContext(), HistoryScreen.class));
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


        sharedPreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String clientJson = sharedPreferences.getString("cover", "");
        Gson gson = new Gson();
        coverage = gson.fromJson(clientJson, PolicyCoverage.class);


        progressDialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        //link java with xml
        etAccountNumber = findViewById(R.id.account_number);
        etPremium = findViewById(R.id.premium_amount_pay);
        txtPremium = findViewById(R.id.premium_amount);
        txtPolicyDescription = findViewById(R.id.policy_desc);
        txtPolicyDueDate = findViewById(R.id.due_date);
        txtPolicyName = findViewById(R.id.policy_name);
        txtPolicyNumber = findViewById(R.id.policy_number_pay);
        spinner = findViewById(R.id.spinner);

        btnCompletePayment = findViewById(R.id.complete_payment);

        initPayment();

    }


    public void initPayment() {
        progressDialog.setMessage("Loading Please wait...");
        showDialog();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        txtPolicyNumber.setText(coverage.getPolicyNumber());
        txtPremium.setText('$' + coverage.getPolicy().getPremium().toString());
        txtPolicyDueDate.setText(sdf.format(coverage.getDate()));
        txtPolicyName.setText(coverage.getPolicy().getName());
        txtPolicyDescription.setText(coverage.getPolicy().getDescription());
        getMethodOfPayment();


    }

    public void payPremium(View view) {
        btnCompletePayment.setEnabled(false);
        String accountNumber = etAccountNumber.getText().toString();
        Long amount = Long.parseLong(etPremium.getText().toString());

        Payment payment = new Payment(coverage.getClient(), accountNumber, paymentType, amount);


        System.out.println("=============== dob");
        System.out.println(payment.getClient().getDateOfBirth());

        PaymentService paymentService = ApiClient.createService(PaymentService.class);
        Call<Payment> call = paymentService.makePayment(payment);
        call.enqueue(new Callback<Payment>() {
            @Override
            public void onResponse(Call<Payment> call, Response<Payment> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Premium successfully paid", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), HistoryScreen.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "Failed to make a payment: " + response.code(), Toast.LENGTH_LONG).show();

                }
                btnCompletePayment.setEnabled(true);
                hideDialog();


            }

            @Override
            public void onFailure(Call<Payment> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "An error has occured: " + t.getMessage(), Toast.LENGTH_LONG).show();
                btnCompletePayment.setEnabled(false);
                hideDialog();

            }
        });
    }

    private void getMethodOfPayment() {

        PaymentService paymentService = ApiClient.createService(PaymentService.class);
        Call<List<PaymentType>> call = paymentService.getPaymentMethods();
        call.enqueue(new Callback<List<PaymentType>>() {
            @Override
            public void onResponse(Call<List<PaymentType>> call, Response<List<PaymentType>> response) {

                System.out.println(response.code());
                if (response.isSuccessful()) {
                    paymentMethods = response.body();
                    PaymentType placeholderItem = new PaymentType();
                    placeholderItem.setName("Choose a payment method");
                    paymentMethods.add(0, placeholderItem);

                    ArrayAdapter<PaymentType> adapter = new ArrayAdapter<PaymentType>(getApplicationContext(), android.R.layout.simple_spinner_item, paymentMethods) {
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

                                paymentType = (PaymentType) parent.getSelectedItem();

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                } else {
                    Toast.makeText(getApplicationContext(), "Failed to load Payment Types: " + response.code(), Toast.LENGTH_LONG).show();

                }
                hideDialog();


            }

            @Override
            public void onFailure(Call<List<PaymentType>> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "An error has occured: " + t.getMessage(), Toast.LENGTH_LONG).show();
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
