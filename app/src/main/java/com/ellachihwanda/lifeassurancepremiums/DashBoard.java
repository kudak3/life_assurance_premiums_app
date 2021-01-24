package com.ellachihwanda.lifeassurancepremiums;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DashBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
    }

   public void callPayments(View view){
        Intent intent = new Intent(this,PayPremium.class);
        startActivity(intent);
    }

    public void showProfile(View view){
        Intent intent = new Intent(this,UserProfile.class);
        startActivity(intent);
    }
    public void showHistory(View view){
        Intent intent = new Intent(this,History.class);
        startActivity(intent);
    }
}