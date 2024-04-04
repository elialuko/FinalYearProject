package com.example.busbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminWelcome extends AppCompatActivity implements View.OnClickListener {

    CardView analytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_welcome);
        analytics = findViewById(R.id.analytics_card);

        analytics.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        String tag = (String) v.getTag();

        switch (tag) {
            case "analytics":
                i = new Intent(AdminWelcome.this, ReportAnalytics.class);
                startActivity(i);
                break;
        }
    }
}