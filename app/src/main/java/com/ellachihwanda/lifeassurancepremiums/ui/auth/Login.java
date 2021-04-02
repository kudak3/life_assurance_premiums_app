package com.ellachihwanda.lifeassurancepremiums.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ellachihwanda.lifeassurancepremiums.R;
import com.ellachihwanda.lifeassurancepremiums.controller.ApiClient;
import com.ellachihwanda.lifeassurancepremiums.model.User;
import com.ellachihwanda.lifeassurancepremiums.service.UserService;
import com.ellachihwanda.lifeassurancepremiums.ui.DashBoard;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    EditText _usernameText, _passwordText;
    Button _loginButton;
    public ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        progressDialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        setContentView(R.layout.activity_login);
        _usernameText = findViewById(R.id.login_username);
        _passwordText = findViewById(R.id.login_password);
        _loginButton = findViewById(R.id.login_button);
    }

    public void callDashBoard(View view) {


        if (!validate()) {

            return;
        }

        _loginButton.setEnabled(false);


        progressDialog.setMessage("Authenticating...");
        showDialog();

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.
        UserService loginService =
                ApiClient.createService(UserService.class);
        Call<User> call = loginService.basicLogin(username, password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()) {
                    // user object available

                    Toast.makeText(getApplicationContext(), "Successfully logged in", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), DashBoard.class);
                    System.out.println("================ddddddd==");
                    System.out.println(response.body());
                     intent.putExtra("user",response.body());
                    startActivity(intent);

                } else {
                    // error response, no access to resource?

                    String message;
                    switch (response.code()){
                        case 404:

                            message = "Not found";
                            break;
                        case 401:
                            message = "Incorrect username or password!!!";
                            break;
                        default:
                            message = "Login Failed Please contact Admin!!";
                    }

                     Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
                hideDialog();

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("Error", t.getMessage());
                  Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();


                hideDialog();
            }
        });
        _loginButton.setEnabled(true);


    }


    public boolean validate() {
        boolean valid = true;

        String username = _usernameText.getText().toString();
        String password = _passwordText.getText().toString();

        if (username.isEmpty()) {
            _usernameText.setError("enter a valid username");
            valid = false;
        } else {
            _usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
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


    public void callSignUpFromLogin(View view) {
        Log.d("call loging", "here");
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        startActivity(intent);
    }
}