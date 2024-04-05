package com.example.busbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class InsightsWelcome extends AppCompatActivity implements View.OnClickListener {

    CardView spending, statistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insights_welcome);
        spending = findViewById(R.id.spending_card);
        statistics = findViewById(R.id.statistics_card);

        spending.setOnClickListener(this);
        statistics.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        String tag = (String) v.getTag();

        switch (tag) {
            case "spending":
                i = new Intent(InsightsWelcome.this, Transactions.class);
                startActivity(i);
                break;
            case "statistics":
                i = new Intent(InsightsWelcome.this, Analytics.class);
                startActivity(i);
                break;
        }
    }
}