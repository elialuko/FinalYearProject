package com.example.busbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity implements View.OnClickListener{

    CardView tickets, editProfile;
    TextView name, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        editProfile = findViewById(R.id.editprofile_card);
        tickets = findViewById(R.id.tickets_card);
        name = findViewById(R.id.tvName);
        email = findViewById(R.id.tvEmail);

        tickets.setOnClickListener(this);
        editProfile.setOnClickListener(this);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String userName = snapshot.child("name").getValue(String.class);
                String userSurname = snapshot.child("surname").getValue(String.class);
                String userEmail = snapshot.child("email").getValue(String.class);
                name.setText(userName + " " + userSurname);
                email.setText(userEmail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onClick(View v) {
        Intent i;
        String tag = (String) v.getTag();

        switch (tag){
            case "tickets" : i = new Intent(Profile.this, List.class);
                startActivity(i);
                break;
            case "editProfile" : i = new Intent(Profile.this, EditUser.class);
                startActivity(i);
                break;
    }
}
}