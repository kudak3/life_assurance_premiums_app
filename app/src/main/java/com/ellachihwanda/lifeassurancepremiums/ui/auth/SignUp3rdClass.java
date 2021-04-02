package com.ellachihwanda.lifeassurancepremiums.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ellachihwanda.lifeassurancepremiums.R;
import com.ellachihwanda.lifeassurancepremiums.controller.ApiClient;
import com.ellachihwanda.lifeassurancepremiums.model.Client;
import com.ellachihwanda.lifeassurancepremiums.model.User;
import com.ellachihwanda.lifeassurancepremiums.service.ClientService;
import com.ellachihwanda.lifeassurancepremiums.ui.DashBoard;
import com.ellachihwanda.lifeassurancepremiums.ui.PoliciesScreen;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp3rdClass extends AppCompatActivity {
    public Client client;
    EditText etPhoneNumber, etIdNumber;
    Button btnSubmit;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        progressDialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        setContentView(R.layout.activity_sign_up3rd_class);
        client = (Client) getIntent().getSerializableExtra("client");

        etIdNumber = findViewById(R.id.signup_id_number);
        etPhoneNumber = findViewById(R.id.signup_phone_number);
        btnSubmit = findViewById(R.id.submit_button);
    }

    public void submit(View view) {
        if (!validate()) {
            return;
        }
        submitClient();

    }

    public void submitClient() {
        progressDialog.setMessage("Registering client...");
        showDialog();
        btnSubmit.setEnabled(false);
        String phoneNumber = etPhoneNumber.getText().toString();
        String idNumber = etIdNumber.getText().toString();

        client.setPhoneNumber(phoneNumber);
        client.setIdNumber(idNumber);


        ClientService clientService = ApiClient.createService(ClientService.class);
        Call<Client> call = clientService.registerClient(client);
        call.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                hideDialog();

                if (response.isSuccessful()) {

                    Intent intent = new Intent(getApplicationContext(), PoliciesScreen.class);
                    intent.putExtra("client", response.body());
                    startActivity(intent);

                } else {
                    // error response, no access to resource?
                    Toast.makeText(getApplicationContext(), response.code() + " User already exists please login", Toast.LENGTH_LONG).show();
                    etPhoneNumber.getText().clear();
                    etIdNumber.getText().clear();

                    btnSubmit.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {

                hideDialog();
                 Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                  btnSubmit.setEnabled(true);

            }
        });


    }

    public boolean validate() {
        boolean valid = true;
        String phoneNumber = etPhoneNumber.getText().toString();
        String idNumber = etIdNumber.getText().toString();


        if (phoneNumber.isEmpty() || !Patterns.PHONE.matcher(phoneNumber).matches()) {
            etPhoneNumber.setError("Enter a valid Phone Number");
            valid = false;
        } else {
            etPhoneNumber.setError(null);
        }

        if (idNumber.isEmpty()) {
            etIdNumber.setError("enter a valid ID number");
            valid = false;
        } else {
            etIdNumber.setError(null);
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