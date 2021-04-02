package com.ellachihwanda.lifeassurancepremiums.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ellachihwanda.lifeassurancepremiums.R;
import com.ellachihwanda.lifeassurancepremiums.controller.ApiClient;
import com.ellachihwanda.lifeassurancepremiums.model.Client;
import com.ellachihwanda.lifeassurancepremiums.model.User;
import com.ellachihwanda.lifeassurancepremiums.service.ClientService;
import com.ellachihwanda.lifeassurancepremiums.service.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfile extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private Client client;
    private User user;
    TextView txtFullName, txtMemberId;
    EditText etFullName, etEmail, etPhoneNumber;
    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        progressDialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        client = (Client) getIntent().getSerializableExtra("client");
        txtFullName = findViewById(R.id.fullname_field);
        txtMemberId = findViewById(R.id.memberId_field);
        etFullName = findViewById(R.id.full_name_profile);
        etEmail = findViewById(R.id.email_profile);
        etPhoneNumber = findViewById(R.id.phone_profile);
        btnUpdate = findViewById(R.id.btnUpdate);

        initProfile();
    }

    public void initProfile() {
        String fullName = client.getFirstName() + " " + client.getLastName();
        txtFullName.setText(fullName);
        txtMemberId.setText(client.getId().toString());
        etFullName.setText(fullName);
        etPhoneNumber.setText(client.getPhoneNumber());
    }

    public void update(View view) {
        if (!validate()) {
            return;
        } else {
            String fullName = etFullName.getText().toString();
            String email = etEmail.getText().toString();
            String phoneNumber = etPhoneNumber.getText().toString();

            String firstName = fullName.split(" ")[0];
            String lastName = fullName.split(" ")[1];

            client.setFirstName(firstName);
            client.setLastName(lastName);
            client.setPhoneNumber(phoneNumber);
            client.setEmail(email);
            ClientService clientService = ApiClient.createService(ClientService.class);
            Call<Client> call = clientService.updateClientProfile(client);
            call.enqueue(new Callback<Client>() {
                @Override
                public void onResponse(Call<Client> call, Response<Client> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Client Details updated successfully", Toast.LENGTH_LONG).show();

                        client = response.body();
                    }
                    {
                        // error response, no access to resource?
                        Toast.makeText(getApplicationContext(), "Details successfully updated", Toast.LENGTH_LONG).show();

                        btnUpdate.setEnabled(true);
                    }
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
        String fullName = etFullName.getText().toString();
        String email = etEmail.getText().toString();
//        String password = etPassword.getText().toString();


        if (fullName.isEmpty()) {
            etFullName.setError("enter a valid fullName");
            valid = false;
        } else {
            etFullName.setError(null);
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

//        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
//            etPassword.setError("between 4 and 10 alphanumeric characters");
//            valid = false;
//        } else {
//            etPassword.setError(null);
//        }

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