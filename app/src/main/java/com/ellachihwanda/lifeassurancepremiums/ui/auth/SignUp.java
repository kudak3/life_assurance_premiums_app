package com.ellachihwanda.lifeassurancepremiums.ui.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ellachihwanda.lifeassurancepremiums.R;
import com.ellachihwanda.lifeassurancepremiums.controller.ApiClient;
import com.ellachihwanda.lifeassurancepremiums.model.User;
import com.ellachihwanda.lifeassurancepremiums.model.dto.UserDto;
import com.ellachihwanda.lifeassurancepremiums.service.UserService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {
    private ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;


    //Variables
    ImageView backBtn;
    Button next, login;
    TextView titleText, slideText;
    EditText etFirstName, etLastName, etEmail, etPassword;
    String token;
    public static final String MyPREFERENCES = "MyPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        progressDialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        getFirebaseToken();

         //get values from preferences
        sharedPreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);

        setContentView(R.layout.activity_sign_up);

        //Hooks for animation
        backBtn = findViewById(R.id.signup_back_button);
        next = findViewById(R.id.signup_next_button);
        login = findViewById(R.id.signup_login_button);
        titleText = findViewById(R.id.signup_title_text);
        slideText = findViewById(R.id.signup_slide_text);

        //user details linking xml
        etFirstName = findViewById(R.id.signup_fname);
        etLastName = findViewById(R.id.signup_lname);
        etEmail = findViewById(R.id.signup_email);
        etPassword = findViewById(R.id.signup_password);


    }

    public void callNextSigUpScreen(View view) {


        if (!validate()) {
            return;
        }
        next();

    }

    public boolean validate() {
        boolean valid = true;
        String lastName = etLastName.getText().toString();
        String firstName = etFirstName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();


        if (firstName.isEmpty() || !firstName.matches("^[a-zA-Z]*$")) {
            etFirstName.setError("Enter a valid first name");
            valid = false;
        } else {
            etFirstName.setError(null);
        }



        if (lastName.isEmpty() || !lastName.matches("^[a-zA-Z]*$")) {
            etLastName.setError("Enter a valid lastName");
            valid = false;
        } else {
            etLastName.setError(null);
        }


        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter a valid email");
            valid = false;
        } else {
            etEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            etPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            etPassword.setError(null);
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

    private void getFirebaseToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("TOKEN", "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get new FCM registration token
                    token = task.getResult();



                });


    }

    public void next() {
        progressDialog.setMessage("Creating user...");
        showDialog();
        next.setEnabled(false);
        String lastName = etLastName.getText().toString();
        String firstName = etFirstName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();


        UserDto userDto = new UserDto(firstName, lastName, email, password, (long) 1, token);

        UserService userService = ApiClient.createService(UserService.class);
        Call<User> call = userService.registerUser(userDto);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {


                if (response.isSuccessful()) {
                    // user object available
                    User user = response.body();
                      //storing our values in preferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    editor.putString("user", gson.toJson(user));
                    editor.apply();

                    Intent intent = new Intent(getApplicationContext(), SignUp2ndClass.class);
                    intent.putExtra("user", user);


                    //Add Shared Animation
                    Pair[] pairs = new Pair[5];
                    pairs[0] = new Pair<>(backBtn, "transition_back_arrow_btn");
                    pairs[1] = new Pair<>(next, "transition_next_btn");
                    pairs[2] = new Pair<>(login, "transition_login_btn");
                    pairs[3] = new Pair<>(titleText, "transition_title_text");
                    pairs[4] = new Pair<>(slideText, "transition_slide_text");
                    hideDialog();
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
                        startActivity(intent, options.toBundle());
                    } else {
                        startActivity(intent);
                    }

                } else {

                    String message;
                    switch (response.code()) {

                        case 401:
                            message = "UnAuthorised to access resource!!!";
                            break;
                        case 404:

                            message = "Not found";
                            break;

                        case 409:
                            message = "User already exists!!!";
                            break;
                        default:
                            message = "Server error contact Admin!!";
                    }

                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                    // error response, no access to resource?
                    etFirstName.getText().clear();
                    etEmail.getText().clear();
                    etPassword.getText().clear();
                    etLastName.getText().clear();
                    ;

                }
                next.setEnabled(true);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                hideDialog();
                System.out.println("===" + t.getMessage());
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                next.setEnabled(true);

            }
        });
        hideDialog();
    }

    public void callLogin(View view) {
        Log.d("call loging", "here");
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }
}