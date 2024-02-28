package com.example.busbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

    Button signinB;
    EditText email, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        signinB = findViewById(R.id.btnSignIn);
        email = findViewById(R.id.edtEmail);
        password = findViewById(R.id.edtPassword);

        signinB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textEmail = email.getText().toString();
                String textPassword = password.getText().toString();

                if(TextUtils.isEmpty(textEmail)){
                    Toast.makeText(SignIn.this, "Please Enter an Email", Toast.LENGTH_LONG).show();
                } else if(TextUtils.isEmpty(textPassword)){
                    Toast.makeText(SignIn.this, "Please Enter a Password", Toast.LENGTH_LONG).show();
                }else{
                    login(textEmail, textPassword);
                    //startActivity(new Intent(MainActivity.this, Welcome.class));
                }
            }

            private void login(String email, String password) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                mDialog.setMessage("Please Wait");
                mDialog.show();
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            mDialog.dismiss();
                            Toast.makeText(SignIn.this, "Login Successful", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SignIn.this, Welcome.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(SignIn.this, "Login Unsuccessful", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}