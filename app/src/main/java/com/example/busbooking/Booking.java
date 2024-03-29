package com.example.busbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Booking extends AppCompatActivity {

    EditText name, surname, number, email;
    RadioButton oneonefive, onetwenty, adult, student, child;
    RadioGroup rg;
    Button checkout;

    RadioGroup routeType;
    String fromSel, toSel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        name = findViewById(R.id.name);
        surname = findViewById(R.id.surname);
        number = findViewById(R.id.number);
        email = findViewById(R.id.email);
        oneonefive = findViewById(R.id.oneonefive);
        onetwenty = findViewById(R.id.onetwenty);
        adult = findViewById(R.id.rbAdult);
        student = findViewById(R.id.rbStudent);
        child = findViewById(R.id.rbChild);
        routeType =  findViewById(R.id.RouteType);
        checkout = findViewById(R.id.CheckoutBtn);
        rg = findViewById(R.id.rg_TicketType);


        populateSpinnersWithDefaultArrays();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String setname = dataSnapshot.child("name").getValue(String.class);
                    name.setText((setname));


                    String setemail = dataSnapshot.child("email").getValue(String.class);
                    email.setText((setemail));


                    String setnum = dataSnapshot.child("phone").getValue(String.class);
                    number.setText((setnum));


                    String setsurname = dataSnapshot.child("surname").getValue(String.class);
                    surname.setText((setsurname));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        routeType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.oneonefive) {
                    // Code to populate spinners for oneonefive
                    Spinner mySpinner = findViewById(R.id.spinner1);
                    ArrayAdapter<String> myAdapter = new ArrayAdapter<>(Booking.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.oneonefiveFrom));
                    myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mySpinner.setAdapter(myAdapter);
                    fromSel = mySpinner.getSelectedItem().toString();


                    Spinner mySpinner1 = findViewById(R.id.spinner2);
                    ArrayAdapter<String> myAdapter1 = new ArrayAdapter<>(Booking.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.oneonefiveTo));
                    myAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mySpinner1.setAdapter(myAdapter1);
                    toSel = mySpinner1.getSelectedItem().toString();
                } else if (checkedId == R.id.onetwenty) {
                    // Code to populate spinners for onetwenty
                    Spinner mySpinner = findViewById(R.id.spinner1);
                    ArrayAdapter<String> myAdapter = new ArrayAdapter<>(Booking.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.onetwentyFrom));
                    myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mySpinner.setAdapter(myAdapter);
                    fromSel = mySpinner.getSelectedItem().toString();

                    Spinner mySpinner1 = findViewById(R.id.spinner2);
                    ArrayAdapter<String> myAdapter1 = new ArrayAdapter<>(Booking.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.onetwentyTo));
                    myAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mySpinner1.setAdapter(myAdapter1);
                    toSel = mySpinner1.getSelectedItem().toString();
                } else {
                    populateSpinnersWithDefaultArrays();
                }

            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textName = name.getText().toString();
                String textSurname = surname.getText().toString();
                String textEmail = email.getText().toString();
                String textNumber = number.getText().toString();

                int selectedId = rg.getCheckedRadioButtonId();
                child = findViewById(selectedId);
                student = findViewById(selectedId);
                adult = findViewById(selectedId);
                String rtext=child.getText().toString();

                if (TextUtils.isEmpty(textName)){
                    Toast.makeText(Booking.this, "Please Enter a Name", Toast.LENGTH_LONG).show();
                } else if(TextUtils.isEmpty(textSurname)){
                    Toast.makeText(Booking.this, "Please Enter a Surname", Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(textNumber)){
                    Toast.makeText(Booking.this, "Please Enter a Number", Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(textEmail)){
                    Toast.makeText(Booking.this, "Please Enter an Email", Toast.LENGTH_LONG).show();
                }else{
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());
                    DatabaseReference bookingRef = userRef.child("Bookings");
                    String bookingsID = bookingRef.push().getKey();
                    Map<String, Object> bookingData = new HashMap<>();
                    Spinner mySpinner = findViewById(R.id.spinner1);
                    Spinner mySpinner1 = findViewById(R.id.spinner2);
                    fromSel = mySpinner.getSelectedItem().toString();
                    toSel = mySpinner1.getSelectedItem().toString();
                    bookingData.put("name",textName);
                    bookingData.put("email",textEmail);
                    bookingData.put("from",fromSel);
                    bookingData.put("to",toSel);
                    bookingRef.child(bookingsID).setValue(bookingData);


                    Intent intent = new Intent(Booking.this, Payment.class);
                    startActivity(intent);


                }
            }
        });


    }

    private void populateSpinnersWithDefaultArrays() {
        // Populate spinners with default arrays
        Spinner mySpinner = findViewById(R.id.spinner1);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(Booking.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Notice));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        Spinner mySpinner1 = findViewById(R.id.spinner2);
        ArrayAdapter<String> myAdapter1 = new ArrayAdapter<>(Booking.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Notice2));
        myAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner1.setAdapter(myAdapter1);
    }
}