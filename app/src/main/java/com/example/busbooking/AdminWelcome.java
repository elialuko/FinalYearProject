package com.example.busbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AdminWelcome extends AppCompatActivity implements View.OnClickListener {

    CardView analytics, route, logout, analytics2, route2, analytics3, route3, bookingprediction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_welcome);
        analytics = findViewById(R.id.analytics_card);
        route = findViewById(R.id.route_card);
        analytics2 = findViewById(R.id.analytics2_card);
        route2 = findViewById(R.id.route2_card);
        logout = findViewById(R.id.logout_card);
        bookingprediction = findViewById(R.id.logout_card2);
        analytics3 = findViewById(R.id.analytics3_card);
        route3 = findViewById(R.id.route3_card);

        analytics.setOnClickListener(this);
        route.setOnClickListener(this);
        analytics2.setOnClickListener(this);
        route2.setOnClickListener(this);
        logout.setOnClickListener(this);
        analytics3.setOnClickListener(this);
        route3.setOnClickListener(this);
        bookingprediction.setOnClickListener(this);
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
                case "analytics2":
                i = new Intent(AdminWelcome.this, ReportAnalytics115.class);
                startActivity(i);
                break;
                case "route2":
                i = new Intent(AdminWelcome.this, RouteAnalytics115.class);
                startActivity(i);
                break;
                case "logout" :
                i = new Intent(AdminWelcome.this, Logout.class);
                startActivity(i);
                break;
                case "analytics3":
                i = new Intent(AdminWelcome.this, FutureStops.class);
                startActivity(i);
                break;
                case "route3":
                i = new Intent(AdminWelcome.this, FutureBookingCounters.class);
                startActivity(i);
                break;
                case "bookingpredictions":
                i = new Intent(AdminWelcome.this, BookingPrediction.class);
                startActivity(i);
        }
    }
}