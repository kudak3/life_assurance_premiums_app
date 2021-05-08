package com.ellachihwanda.lifeassurancepremiums.ui.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.ellachihwanda.lifeassurancepremiums.ui.PoliciesScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ellachihwanda.lifeassurancepremiums.ui.DashBoard.MyPREFERENCES;

public class SignUp3rdClass extends AppCompatActivity {
    public Client client;
    EditText etPhoneNumber, etIdNumber;
    Button btnSubmit;
    private ProgressDialog progressDialog;
    String token;
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        progressDialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        getFirebaseToken();

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
        client.setDeviceToken(token);


        ClientService clientService = ApiClient.createService(ClientService.class);
        Call<Client> call = clientService.registerClient(client);
        call.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                hideDialog();

                if (response.isSuccessful()) {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    Gson gson = new Gson();
                    editor.putString("client", gson.toJson(response.body()));
                    editor.apply();
                    Intent intent = new Intent(getApplicationContext(), PoliciesScreen.class);

                    startActivity(intent);
                    finish();

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
        String _econet = "(?:\\+?263|0)(77|78)[0-9]{7}$";
        String _netone = "(?:\\+?263|0)(71)[0-9]{7}$";
        String _telecel = "(?:\\+?263|0)(73)[0-9]{7}$";
        String _idNumber = "^([0-9]{2})(-|\\s)([0-9]{6,7})([A-Z]{1})*([0-9]{2})$";


        if (phoneNumber.isEmpty()) {
            etPhoneNumber.setError("Phone Number can not be empty");
            valid = false;
        } else {
            etPhoneNumber.setError(null);
        }

        if (phoneNumber.matches(_econet) || phoneNumber.matches(_netone) || phoneNumber.matches(_telecel)) {
            etPhoneNumber.setError(null);
        } else {
            valid = false;
            etPhoneNumber.setError("Enter a valid phone number");
        }

        if (idNumber.isEmpty()) {
            etIdNumber.setError("ID number cannot be empty");
            valid = false;
        } else {
            etIdNumber.setError(null);
        }
        if (idNumber.matches(_idNumber)) {
            etIdNumber.setError(null);
        } else {
            valid = false;
            etIdNumber.setError("Enter a valid Id number");
        }

        return valid;
    }

    private void getFirebaseToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TOKEN", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        token = task.getResult();

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