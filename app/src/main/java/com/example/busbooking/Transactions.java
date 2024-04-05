package com.example.busbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);
        total = findViewById(R.id.total);
        //total.setText("€"+"3.30");

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("User").child(firebaseUser.getUid()).child("Bookings");

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    double totalAmount = 0.0;
                    for (DataSnapshot bookingSnapshot : snapshot.getChildren()) {
                        Double price = bookingSnapshot.child("price").getValue(Double.class);
                        if (price != null) {
                            totalAmount += price;
                        } else {
                            // Handle null prices if necessary
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