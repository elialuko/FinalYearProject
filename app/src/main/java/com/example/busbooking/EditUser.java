package com.example.busbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class EditUser extends AppCompatActivity {
    EditText name, surname, email, number, password;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        name = findViewById(R.id.edtName);
        surname = findViewById(R.id.edtSurname);
        email = findViewById(R.id.edtEmail);
        number = findViewById(R.id.edtPhone);
        password = findViewById(R.id.edtPassword);
        save = findViewById(R.id.btnSave);

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
                String setsurname = snapshot.child("surname").getValue(String.class);
                surname.setText((setsurname));
                String setnumber = snapshot.child("phone").getValue(String.class);
                number.setText((setnumber));
                String setpassword = snapshot.child("password").getValue(String.class);
                password.setText((setpassword));

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = name.getText().toString();
                String newSurname = surname.getText().toString();
                String newEmail = email.getText().toString();
                String newNumber = number.getText().toString();
                String newPassword = password.getText().toString();

                userRef.child("name").setValue(newName);
                userRef.child("surname").setValue(newSurname);
                userRef.child("email").setValue(newEmail);
                userRef.child("phone").setValue(newNumber);
                userRef.child("password").setValue(newPassword);

                Toast.makeText(EditUser.this, "Saved", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(EditUser.this, Profile.class);
                startActivity(i);
            }
        });
    }
}