package com.ellachihwanda.lifeassurancepremiums;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.ellachihwanda.lifeassurancepremiums.ui.OnBoarding;

public class SplashScreen extends AppCompatActivity {

    private int SPLASH_TIMER = 5000;
    //variables
    ImageView backgroundImage;
    TextView poweredByLine;

    //animations
    Animation sideAnim, bottomAnim;


   SharedPreferences onBoardingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);

        //hooks :link /binding java code to xml
        backgroundImage = findViewById(R.id.background_image);
        poweredByLine = findViewById(R.id.powered_by_line);

        //animations hook animations
        sideAnim = AnimationUtils.loadAnimation(this,R.anim.side_anim);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        //set Animations on elements
        backgroundImage.setAnimation(sideAnim);
        poweredByLine.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {



            @Override
            public void run() {
                onBoardingScreen = getSharedPreferences("onBoardingScreen",MODE_PRIVATE);
                boolean isFirstTime = onBoardingScreen.getBoolean("firstTime",true);

//                if(isFirstTime){
//                    SharedPreferences.Editor editor = onBoardingScreen.edit();
//                    editor.putBoolean("firstTime",false);
//                    editor.commit();
                    Intent intent = new Intent(SplashScreen.this, OnBoarding.class);
//                    startActivity(intent);
//                    finish();
//                }else{
//                    Intent intent = new Intent(SplashScreen.this, TempScreen.class);
                    startActivity(intent);
                    finish();
//                }

            }
        },SPLASH_TIMER);

    }
}