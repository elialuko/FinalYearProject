package com.example.busbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ActivatedTicket extends AppCompatActivity {
    TextView name, date, from, to, price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activated_ticket);
        name = findViewById(R.id.name);
        date = findViewById(R.id.date);
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        price = findViewById(R.id.price);

        Intent intent = getIntent();
        if (intent != null) {
            String tname = intent.getStringExtra("name");
            String tfrom = intent.getStringExtra("from");
            String tto = intent.getStringExtra("to");
            String tdate = intent.getStringExtra("date");
            double tprice = intent.getDoubleExtra("price", 0.0);
            name.setText(tname);
            date.setText(tdate);
            from.setText(tfrom);
            to.setText(tto);
            price.setText("€" + tprice);
        }


    }
}