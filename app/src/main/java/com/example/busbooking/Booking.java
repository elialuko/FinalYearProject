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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Booking extends AppCompatActivity {

    EditText name, date, number, email;
    RadioButton oneonefive, onetwenty, adult, student, child;
    RadioGroup rg;
    Button checkout;

    RadioGroup routeType, ticketType;
    String fromSel, toSel;
    double amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        name = findViewById(R.id.name);
        date = findViewById(R.id.date);
        number = findViewById(R.id.number);
        email = findViewById(R.id.email);
        oneonefive = findViewById(R.id.oneonefive);
        onetwenty = findViewById(R.id.onetwenty);
        adult = findViewById(R.id.rbAdult);
        student = findViewById(R.id.rbStudent);
        child = findViewById(R.id.rbChild);
        routeType =  findViewById(R.id.RouteType);
        ticketType =  findViewById(R.id.rg_TicketType);
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

                    Date currentDate = new Date();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    String formattedDate = dateFormat.format(currentDate);
                    date.setText(formattedDate);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        routeType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.oneonefive) {
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

        ticketType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.rbAdult){
                    amount = 3.30;
                }else if(checkedId==R.id.rbStudent){
                    amount = 2.80;
                }else if(checkedId==R.id.rbChild){
                    amount = 1.90;
                }

            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textName = name.getText().toString();
                String textDate = date.getText().toString();
                String textEmail = email.getText().toString();
                String textNumber = number.getText().toString();
                Spinner mySpinner = findViewById(R.id.spinner1);
                Spinner mySpinner1 = findViewById(R.id.spinner2);
                fromSel = mySpinner.getSelectedItem().toString();
                toSel = mySpinner1.getSelectedItem().toString();
                int selectedId = rg.getCheckedRadioButtonId();
                child = findViewById(selectedId);
                student = findViewById(selectedId);
                adult = findViewById(selectedId);
                String rtext=child.getText().toString();

                if (TextUtils.isEmpty(textName)){
                    Toast.makeText(Booking.this, "Please Enter a Name", Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(textNumber)){
                    Toast.makeText(Booking.this, "Please Enter a Number", Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(textEmail)){
                    Toast.makeText(Booking.this, "Please Enter an Email", Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(textDate)){
                    Toast.makeText(Booking.this, "Please Enter a Date", Toast.LENGTH_LONG).show();
                }else if(fromSel.equals(toSel)){
                    Toast.makeText(Booking.this, "Can't Buy Tickets To The Same Stop", Toast.LENGTH_LONG).show();
                }else if(fromSel.equals("Please select a route first")){
                    Toast.makeText(Booking.this, "Please select a stop", Toast.LENGTH_LONG).show();
                }else if(toSel.equals("Please select a route first")){
                    Toast.makeText(Booking.this, "Please select a stop", Toast.LENGTH_LONG).show();
                }else if(fromSel.equals("From")){
                    Toast.makeText(Booking.this, "Please select a stop", Toast.LENGTH_LONG).show();
                }else if(toSel.equals("To")){
                    Toast.makeText(Booking.this, "Please select a stop", Toast.LENGTH_LONG).show();
                }else{
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());
                    DatabaseReference bookingRef = userRef.child("Bookings");
                    String bookingsID = bookingRef.push().getKey();
                    Map<String, Object> bookingData = new HashMap<>();


                    bookingData.put("name",textName);
                    bookingData.put("email",textEmail);
                    bookingData.put("date",textDate);
                    bookingData.put("from",fromSel);
                    bookingData.put("to",toSel);
                    bookingData.put("price",amount);
                    bookingData.put("status","active");
                    bookingData.put("id", bookingsID);
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