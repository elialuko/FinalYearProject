package com.example.busbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class Logout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        //FirebaseDatabase.getInstance().goOffline();
        finish();
        Toast.makeText(Logout.this, "Logged Out", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, MainActivity.class));
    }
}