package com.ellachihwanda.lifeassurancepremiums;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class History extends AppCompatActivity {

    RecyclerView recyclerView;
    String[] s1 = {"payment","payment","claim","payment"};
    String[] s2 = {"rx1","rx2","rx3","rx4"};
    String[] s3 = {"120","130","140","150"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.transaction_history_rvw);
        TransactionsAdapter transactionsAdapter = new TransactionsAdapter(this,s1,s2,s3);
        recyclerView.setAdapter(transactionsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
