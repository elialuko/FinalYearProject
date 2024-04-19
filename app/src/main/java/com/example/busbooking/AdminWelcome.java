package com.example.busbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminWelcome extends AppCompatActivity implements View.OnClickListener {

    CardView analytics, route, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_welcome);
        analytics = findViewById(R.id.analytics_card);
        route = findViewById(R.id.route_card);
        logout = findViewById(R.id.logout_card);

        analytics.setOnClickListener(this);
        route.setOnClickListener(this);
        logout.setOnClickListener(this);
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
                case "route":
                i = new Intent(AdminWelcome.this, RouteAnalytics.class);
                startActivity(i);
                break;
            case "logout" : i = new Intent(AdminWelcome.this, Logout.class);
                startActivity(i);
                break;
        }
    }
}