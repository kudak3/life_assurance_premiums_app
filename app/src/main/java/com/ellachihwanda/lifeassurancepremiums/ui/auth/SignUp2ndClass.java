package com.ellachihwanda.lifeassurancepremiums.ui.auth;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ellachihwanda.lifeassurancepremiums.R;
import com.ellachihwanda.lifeassurancepremiums.model.Client;
import com.ellachihwanda.lifeassurancepremiums.model.User;
import com.ellachihwanda.lifeassurancepremiums.model.enums.Gender;

import java.util.Calendar;
import java.util.Date;

public class SignUp2ndClass extends AppCompatActivity {
    private ProgressDialog progressDialog;


    //Variables
    ImageView backBtn;
    Button next, login;
    TextView titleText, slideText;
    RadioGroup radioGroup;
    RadioButton selectedGender,lastRadioBtn;
    DatePicker datePicker;
    public User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        progressDialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        setContentView(R.layout.activity_sign_up2nd_class);

        user = (User) getIntent().getSerializableExtra("user");


        //Hooks
        backBtn = findViewById(R.id.signup_back_button);
        next = findViewById(R.id.signup_next_button);
        login = findViewById(R.id.signup_login_button);
        titleText = findViewById(R.id.signup_title_text);
        slideText = findViewById(R.id.signup_slide_text);
        radioGroup = findViewById(R.id.radio_group);
        lastRadioBtn =findViewById(R.id.others);
        datePicker = findViewById(R.id.age_picker);


    }

    public void next(View view) {

        int selectedId = radioGroup.getCheckedRadioButtonId();
        selectedGender = findViewById(selectedId);

        next.setEnabled(false);
        String gender = selectedGender.getText().toString();
        Date dateOfBirth = getDateFromDatePicker(datePicker);

        Client client = new Client(user, dateOfBirth, Gender.valueOf(gender.toUpperCase()));


        Intent intent = new Intent(getApplicationContext(), SignUp3rdClass.class);
        intent.putExtra("client", client);


        //Add Transition and call next activity
        Pair[] pairs = new Pair[5];
        pairs[0] = new Pair(backBtn, "transition_back_arrow_btn");
        pairs[1] = new Pair(next, "transition_next_btn");
        pairs[2] = new Pair(login, "transition_login_btn");
        pairs[3] = new Pair(titleText, "transition_title_text");
        pairs[4] = new Pair(slideText, "transition_slide_text");


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp2ndClass.this, pairs);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }


    }

    public static Date getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        System.out.println(calendar.getTime());

        return calendar.getTime();
    }



}
