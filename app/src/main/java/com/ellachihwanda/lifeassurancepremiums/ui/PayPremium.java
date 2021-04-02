package com.ellachihwanda.lifeassurancepremiums.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.ellachihwanda.lifeassurancepremiums.R;
import com.ellachihwanda.lifeassurancepremiums.model.PolicyCoverage;

import java.util.ArrayList;
import java.util.List;

public class PayPremium extends AppCompatActivity {
    private PolicyCoverage coverage;
    EditText etAccountNumber, etPremium;
    TextView txtPolicyNumber, txtPolicyDueDate, txtPolicyName, txtPolicyDescription, txtPremium;
    Button btnCompletePayment;
    RadioGroup rgPaymentMethod;
    RadioButton rbSelectedMethod, rbZipit;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_premium);
        coverage = (PolicyCoverage) getIntent().getSerializableExtra("cover");

        etAccountNumber = findViewById(R.id.account_number);
        etPremium = findViewById(R.id.premium_amount_pay);
        txtPremium = findViewById(R.id.premium_amount);
        txtPolicyDescription = findViewById(R.id.policy_desc);
        txtPolicyDueDate = findViewById(R.id.due_date);
        txtPolicyName = findViewById(R.id.policy_name);
        txtPolicyNumber = findViewById(R.id.policy_number_pay);
        spinner = findViewById(R.id.spinner);
        rgPaymentMethod = findViewById(R.id.radio_group_pmts);
        rbZipit = findViewById(R.id.zipit);
        btnCompletePayment = findViewById(R.id.complete_payment);

        initPayment();

    }


    public void initPayment() {
        txtPolicyNumber.setText(coverage.getPolicyNumber());
        txtPremium.setText('$' + coverage.getPolicy().getPremium().toString());
        txtPolicyDueDate.setText(coverage.getDate().toString());
        txtPolicyName.setText(coverage.getPolicy().getName());
        txtPolicyDescription.setText(coverage.getPolicy().getDescription());
        List<String> list = new ArrayList<>();
        list.add("Banc Abc");
        list.add("Zb bank");
        list.add("Bank");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list);
           rbZipit.setOnClickListener(v -> {

        });
           adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
           spinner.setAdapter(adapter);


    }

    public void payPremium() {
        btnCompletePayment.setEnabled(false);
        int selectedId = rgPaymentMethod.getCheckedRadioButtonId();
        rbSelectedMethod = findViewById(selectedId);

        Long accountNumber = Long.parseLong(etAccountNumber.getText().toString());
        Long premium = Long.parseLong(etPremium.getText().toString());

    }
}