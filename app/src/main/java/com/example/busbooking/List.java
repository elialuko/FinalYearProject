package com.example.busbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.busbooking.Model.MyAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class List extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Bookings>list;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        auth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.recyclerview);
        FirebaseUser firebaseUser = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());
        DatabaseReference bookingRef = databaseReference.child("Bookings");
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this, list);
        recyclerView.setAdapter(adapter);

        bookingRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Bookings bookings = dataSnapshot.getValue(Bookings.class);
                    Log.d("FirebaseData", "Forecasts: " + bookings.toString());
                    list.add(bookings);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}