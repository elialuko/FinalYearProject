package com.example.busbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Transactions extends AppCompatActivity {

    TextView total;
    Button select;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        total = findViewById(R.id.total);
        select = findViewById(R.id.select);
        //total.setText("€"+"3.30");

        Spinner mySpinner = findViewById(R.id.spinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(Transactions.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.months));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("User").child(firebaseUser.getUid()).child("Bookings");

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    double totalAmount = 0.0;
                    for (DataSnapshot bookingSnapshot : snapshot.getChildren()) {
                        String date = bookingSnapshot.child("date").getValue(String.class);
                        Double price = bookingSnapshot.child("price").getValue(Double.class);
                        System.out.println("Date: " + date);
                        if (date != null && date.charAt(3) == '0' && date.charAt(4) == '1') {
                            if (price != null) {
                                totalAmount += price;
                            } else {
                                // Handle null prices if necessary
                            }
                        }
                    }
                    total.setText("€" + String.format("%.2f", totalAmount));
                } catch (Exception e) {
                    e.printStackTrace();
                    // Handle the exception gracefully, perhaps display an error message to the user
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mySpinner.getSelectedItem().toString().equalsIgnoreCase("April")) {
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try {
                                double totalAmount = 0.0;
                                for (DataSnapshot bookingSnapshot : snapshot.getChildren()) {
                                    String date = bookingSnapshot.child("date").getValue(String.class);
                                    Double price = bookingSnapshot.child("price").getValue(Double.class);
                                    System.out.println("Date: " + date);
                                    if (date != null && date.charAt(3) == '0' && date.charAt(4) == '4') {
                                        if (price != null) {
                                            totalAmount += price;
                                        } else {
                                            // Handle null prices if necessary
                                        }
                                    }
                                }
                                total.setText("€" + String.format("%.2f", totalAmount));
                            } catch (Exception e) {
                                e.printStackTrace();
                                // Handle the exception gracefully, perhaps display an error message to the user
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else if(mySpinner.getSelectedItem().toString().equalsIgnoreCase("February")) {
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try {
                                double totalAmount = 0.0;
                                for (DataSnapshot bookingSnapshot : snapshot.getChildren()) {
                                    String date = bookingSnapshot.child("date").getValue(String.class);
                                    Double price = bookingSnapshot.child("price").getValue(Double.class);
                                    System.out.println("Date: " + date);
                                    if (date != null && date.charAt(3) == '0' && date.charAt(4) == '2') {
                                        if (price != null) {
                                            totalAmount += price;
                                        } else {
                                            // Handle null prices if necessary
                                        }
                                    }
                                }
                                total.setText("€" + String.format("%.2f", totalAmount));
                            } catch (Exception e) {
                                e.printStackTrace();
                                // Handle the exception gracefully, perhaps display an error message to the user
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else if(mySpinner.getSelectedItem().toString().equalsIgnoreCase("March")) {
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try {
                                double totalAmount = 0.0;
                                for (DataSnapshot bookingSnapshot : snapshot.getChildren()) {
                                    String date = bookingSnapshot.child("date").getValue(String.class);
                                    Double price = bookingSnapshot.child("price").getValue(Double.class);
                                    System.out.println("Date: " + date);
                                    if (date != null && date.charAt(3) == '0' && date.charAt(4) == '3') {
                                        if (price != null) {
                                            totalAmount += price;
                                        } else {
                                            // Handle null prices if necessary
                                        }
                                    }
                                }
                                total.setText("€" + String.format("%.2f", totalAmount));
                            } catch (Exception e) {
                                e.printStackTrace();
                                // Handle the exception gracefully, perhaps display an error message to the user
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else if(mySpinner.getSelectedItem().toString().equalsIgnoreCase("May")) {
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try {
                                double totalAmount = 0.0;
                                for (DataSnapshot bookingSnapshot : snapshot.getChildren()) {
                                    String date = bookingSnapshot.child("date").getValue(String.class);
                                    Double price = bookingSnapshot.child("price").getValue(Double.class);
                                    System.out.println("Date: " + date);
                                    if (date != null && date.charAt(3) == '0' && date.charAt(4) == '5') {
                                        if (price != null) {
                                            totalAmount += price;
                                        } else {
                                            // Handle null prices if necessary
                                        }
                                    }
                                }
                                total.setText("€" + String.format("%.2f", totalAmount));
                            } catch (Exception e) {
                                e.printStackTrace();
                                // Handle the exception gracefully, perhaps display an error message to the user
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else if(mySpinner.getSelectedItem().toString().equalsIgnoreCase("June")) {
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try {
                                double totalAmount = 0.0;
                                for (DataSnapshot bookingSnapshot : snapshot.getChildren()) {
                                    String date = bookingSnapshot.child("date").getValue(String.class);
                                    Double price = bookingSnapshot.child("price").getValue(Double.class);
                                    System.out.println("Date: " + date);
                                    if (date != null && date.charAt(3) == '0' && date.charAt(4) == '6') {
                                        if (price != null) {
                                            totalAmount += price;
                                        } else {
                                            // Handle null prices if necessary
                                        }
                                    }
                                }
                                total.setText("€" + String.format("%.2f", totalAmount));
                            } catch (Exception e) {
                                e.printStackTrace();
                                // Handle the exception gracefully, perhaps display an error message to the user
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else if(mySpinner.getSelectedItem().toString().equalsIgnoreCase("July")) {
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try {
                                double totalAmount = 0.0;
                                for (DataSnapshot bookingSnapshot : snapshot.getChildren()) {
                                    String date = bookingSnapshot.child("date").getValue(String.class);
                                    Double price = bookingSnapshot.child("price").getValue(Double.class);
                                    System.out.println("Date: " + date);
                                    if (date != null && date.charAt(3) == '0' && date.charAt(4) == '7') {
                                        if (price != null) {
                                            totalAmount += price;
                                        } else {
                                            // Handle null prices if necessary
                                        }
                                    }
                                }
                                total.setText("€" + String.format("%.2f", totalAmount));
                            } catch (Exception e) {
                                e.printStackTrace();
                                // Handle the exception gracefully, perhaps display an error message to the user
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else if(mySpinner.getSelectedItem().toString().equalsIgnoreCase("August")) {
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try {
                                double totalAmount = 0.0;
                                for (DataSnapshot bookingSnapshot : snapshot.getChildren()) {
                                    String date = bookingSnapshot.child("date").getValue(String.class);
                                    Double price = bookingSnapshot.child("price").getValue(Double.class);
                                    System.out.println("Date: " + date);
                                    if (date != null && date.charAt(3) == '0' && date.charAt(4) == '8') {
                                        if (price != null) {
                                            totalAmount += price;
                                        } else {
                                            // Handle null prices if necessary
                                        }
                                    }
                                }
                                total.setText("€" + String.format("%.2f", totalAmount));
                            } catch (Exception e) {
                                e.printStackTrace();
                                // Handle the exception gracefully, perhaps display an error message to the user
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else if(mySpinner.getSelectedItem().toString().equalsIgnoreCase("September")) {
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try {
                                double totalAmount = 0.0;
                                for (DataSnapshot bookingSnapshot : snapshot.getChildren()) {
                                    String date = bookingSnapshot.child("date").getValue(String.class);
                                    Double price = bookingSnapshot.child("price").getValue(Double.class);
                                    System.out.println("Date: " + date);
                                    if (date != null && date.charAt(3) == '0' && date.charAt(4) == '9') {
                                        if (price != null) {
                                            totalAmount += price;
                                        } else {
                                            // Handle null prices if necessary
                                        }
                                    }
                                }
                                total.setText("€" + String.format("%.2f", totalAmount));
                            } catch (Exception e) {
                                e.printStackTrace();
                                // Handle the exception gracefully, perhaps display an error message to the user
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else if(mySpinner.getSelectedItem().toString().equalsIgnoreCase("October")) {
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try {
                                double totalAmount = 0.0;
                                for (DataSnapshot bookingSnapshot : snapshot.getChildren()) {
                                    String date = bookingSnapshot.child("date").getValue(String.class);
                                    Double price = bookingSnapshot.child("price").getValue(Double.class);
                                    System.out.println("Date: " + date);
                                    if (date != null && date.charAt(3) == '1' && date.charAt(4) == '0') {
                                        if (price != null) {
                                            totalAmount += price;
                                        } else {
                                            // Handle null prices if necessary
                                        }
                                    }
                                }
                                total.setText("€" + String.format("%.2f", totalAmount));
                            } catch (Exception e) {
                                e.printStackTrace();
                                // Handle the exception gracefully, perhaps display an error message to the user
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else if(mySpinner.getSelectedItem().toString().equalsIgnoreCase("November")) {
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try {
                                double totalAmount = 0.0;
                                for (DataSnapshot bookingSnapshot : snapshot.getChildren()) {
                                    String date = bookingSnapshot.child("date").getValue(String.class);
                                    Double price = bookingSnapshot.child("price").getValue(Double.class);
                                    System.out.println("Date: " + date);
                                    if (date != null && date.charAt(3) == '1' && date.charAt(4) == '1') {
                                        if (price != null) {
                                            totalAmount += price;
                                        } else {
                                            // Handle null prices if necessary
                                        }
                                    }
                                }
                                total.setText("€" + String.format("%.2f", totalAmount));
                            } catch (Exception e) {
                                e.printStackTrace();
                                // Handle the exception gracefully, perhaps display an error message to the user
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else if(mySpinner.getSelectedItem().toString().equalsIgnoreCase("December")) {
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try {
                                double totalAmount = 0.0;
                                for (DataSnapshot bookingSnapshot : snapshot.getChildren()) {
                                    String date = bookingSnapshot.child("date").getValue(String.class);
                                    Double price = bookingSnapshot.child("price").getValue(Double.class);
                                    System.out.println("Date: " + date);
                                    if (date != null && date.charAt(3) == '1' && date.charAt(4) == '2') {
                                        if (price != null) {
                                            totalAmount += price;
                                        } else {
                                            // Handle null prices if necessary
                                        }
                                    }
                                }
                                total.setText("€" + String.format("%.2f", totalAmount));
                            } catch (Exception e) {
                                e.printStackTrace();
                                // Handle the exception gracefully, perhaps display an error message to the user
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else if(mySpinner.getSelectedItem().toString().equalsIgnoreCase("January")) {
                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try {
                                double totalAmount = 0.0;
                                for (DataSnapshot bookingSnapshot : snapshot.getChildren()) {
                                    String date = bookingSnapshot.child("date").getValue(String.class);
                                    Double price = bookingSnapshot.child("price").getValue(Double.class);
                                    System.out.println("Date: " + date);
                                    if (date != null && date.charAt(3) == '0' && date.charAt(4) == '1') {
                                        if (price != null) {
                                            totalAmount += price;
                                        } else {
                                            // Handle null prices if necessary
                                        }
                                    }
                                }
                                total.setText("€" + String.format("%.2f", totalAmount));
                            } catch (Exception e) {
                                e.printStackTrace();
                                // Handle the exception gracefully, perhaps display an error message to the user
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
}