package com.example.busbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Reports extends AppCompatActivity {

    EditText name, email, report;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        name = findViewById(R.id.reportName);
        email = findViewById(R.id.reportEmail);
        report = findViewById(R.id.FeedbackReport);
        submit = findViewById(R.id.button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Reports.this, "Report Sent Successfully!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}