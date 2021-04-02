package com.ellachihwanda.lifeassurancepremiums.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import com.ellachihwanda.lifeassurancepremiums.R;
import com.ellachihwanda.lifeassurancepremiums.ui.auth.Login;
import com.ellachihwanda.lifeassurancepremiums.ui.auth.SignUp;

public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
    }

    public void callLoginScreen(View view) {
        Intent intent = new Intent(getApplicationContext(), Login.class);


        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair(findViewById(R.id.let_userLogIn), "transition_login");


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(WelcomeScreen.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    public void callSignUpScreen(View view) {


        Intent intent = new Intent(getApplicationContext(), SignUp.class);


        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair(findViewById(R.id.start_sign_up_btn), "transition_sign_up");


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(WelcomeScreen.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }


    }
}