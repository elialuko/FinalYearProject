package com.example.busbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Reports extends AppCompatActivity {

    EditText name, email, report, date, time;
    Button submit;
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
                    Toast.makeText(Reports.this, "Please Enter a Number", Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(textTime)){
                    Toast.makeText(Reports.this, "Please Enter an Email", Toast.LENGTH_LONG).show();
                }else{
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());
                    DatabaseReference ReportsRef = userRef.child("Reports");
                }
                Toast.makeText(Reports.this, "Report Sent Successfully!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}