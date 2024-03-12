package com.example.busbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Welcome extends AppCompatActivity implements View.OnClickListener {
    CardView fare, booking, journey, reports, tickets, analytics, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        fare = findViewById(R.id.fare_card);
        booking = findViewById(R.id.booking_card);
        journey = findViewById(R.id.journey_card);
        reports = findViewById(R.id.reports_card);
        tickets = findViewById(R.id.tickets_card);
        logout = findViewById(R.id.logout_card);
        analytics = findViewById(R.id.analytics_card);

        fare.setOnClickListener(this);
        booking.setOnClickListener(this);
        journey.setOnClickListener(this);
        reports.setOnClickListener(this);
        tickets.setOnClickListener(this);
        logout.setOnClickListener(this);
        analytics.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        String tag = (String) v.getTag();

        switch (tag){
            case "fare" : i = new Intent(Welcome.this, Fares.class);
            startActivity(i);
            break;
            case "booking" : i = new Intent(Welcome.this, Booking.class);
            startActivity(i);
            break;
            case "journey" : i = new Intent(Welcome.this, JourneyPlanner.class);
            startActivity(i);
            break;
            case "reports" : i = new Intent(Welcome.this, Reports.class);
            startActivity(i);
            break;
            case "tickets" : i = new Intent(Welcome.this, com.example.busbooking.List.class);
            startActivity(i);
            break;
            case "analytics" : i = new Intent(Welcome.this, Analytics.class);
            startActivity(i);
            break;
            case "logout" : i = new Intent(Welcome.this, Logout.class);
            startActivity(i);
            break;
        }
    }
}