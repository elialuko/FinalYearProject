package com.example.busbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Reports extends AppCompatActivity {

    EditText name, email, report, date, time;
    Button submit;
    String reportfromS, routefromS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        name = findViewById(R.id.reportName);
        email = findViewById(R.id.reportEmail);
        date = findViewById(R.id.reportDate);
        time = findViewById(R.id.reportTime);
        report = findViewById(R.id.FeedbackReport);
        submit = findViewById(R.id.button);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(Reports.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.reportsSpinner));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);
        reportfromS = spinner.getSelectedItem().toString();

        Spinner spinner2 = findViewById(R.id.spinner2);
        ArrayAdapter<String> myAdapter1 = new ArrayAdapter<>(Reports.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.reportsSpinner2));
        myAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(myAdapter1);
        routefromS = spinner2.getSelectedItem().toString();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String setname = snapshot.child("name").getValue(String.class);
                    name.setText((setname));
                    String setemail = snapshot.child("email").getValue(String.class);
                    email.setText((setemail));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Date currentDate = new Date();
        Date currentTime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String formattedDate = dateFormat.format(currentDate);
        String formattedTime = timeFormat.format(currentTime);
        date.setText(formattedDate);
        time.setText(formattedTime);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textName = name.getText().toString();
                String textEmail = email.getText().toString();
                String textDate = date.getText().toString();
                String textTime = time.getText().toString();
                String textReport = report.getText().toString();
                if (TextUtils.isEmpty(textName)){
                    Toast.makeText(Reports.this, "Please Enter a Name", Toast.LENGTH_LONG).show();
                } else if(TextUtils.isEmpty(textEmail)){
                    Toast.makeText(Reports.this, "Please Enter a Surname", Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(textDate)){
                    Toast.makeText(Reports.this, "Please Enter a Date", Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(textTime)){
                    Toast.makeText(Reports.this, "Please Enter a Time", Toast.LENGTH_LONG).show();
                }else{
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());
                    DatabaseReference reportsRef = userRef.child("Reports");
                    String reportsID = reportsRef.push().getKey();
                    Map<String, Object> reportsData = new HashMap<>();
                    reportfromS = spinner.getSelectedItem().toString();
                    routefromS = spinner2.getSelectedItem().toString();
                    reportsData.put("date",textDate);
                    reportsData.put("time",textTime);
                    reportsData.put("route",routefromS);
                    reportsData.put("report",reportfromS);
                    reportsRef.child(reportsID).setValue(reportsData);
                }
                Toast.makeText(Reports.this, "Report Sent Successfully!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Reports.this, Welcome.class);
                startActivity(intent);
            }
        });
    }
}