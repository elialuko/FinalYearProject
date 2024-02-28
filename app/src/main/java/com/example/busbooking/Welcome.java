package com.example.busbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Welcome extends AppCompatActivity implements View.OnClickListener {
    CardView fare, booking, profile, reports, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        fare = findViewById(R.id.fare_card);
        booking = findViewById(R.id.booking_card);
        profile = findViewById(R.id.profile_card);
        reports = findViewById(R.id.reports_card);
        logout = findViewById(R.id.logout_card);

        fare.setOnClickListener(this);
        booking.setOnClickListener(this);
        profile.setOnClickListener(this);
        reports.setOnClickListener(this);
        logout.setOnClickListener(this);
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
            case "profile" : i = new Intent(Welcome.this, MainActivity.class);
            startActivity(i);
            break;
            case "reports" : i = new Intent(Welcome.this, Reports.class);
            startActivity(i);
            break;
            case "logout" : i = new Intent(Welcome.this, Logout.class);
            startActivity(i);
            break;
        }
    }
}