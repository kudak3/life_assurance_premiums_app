package com.ellachihwanda.lifeassurancepremiums;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ellachihwanda.lifeassurancepremiums.ui.auth.SignUp;
import com.ellachihwanda.lifeassurancepremiums.ui.auth.SignUp3rdClass;

public class TempScreen extends AppCompatActivity {
    SignUp signUp = new SignUp();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_screen);
    }
}