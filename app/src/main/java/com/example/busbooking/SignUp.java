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

import com.example.busbooking.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    EditText name, surname, email, phone, password;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.edtName);
        surname = findViewById(R.id.edtSurname);
        email = findViewById(R.id.edtEmail);
        phone = findViewById(R.id.edtPhone);
        password = findViewById(R.id.edtPassword);
        signUp = findViewById(R.id.btnSignUp);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textName = name.getText().toString();
                String textSurname = surname.getText().toString();
                String textEmail = email.getText().toString();
                String textPhone = phone.getText().toString();
                String textPassword = password.getText().toString();

                if (TextUtils.isEmpty(textEmail)){
                    Toast.makeText(SignUp.this, "Please Enter an Email", Toast.LENGTH_LONG).show();
                } else if(TextUtils.isEmpty((textName))){
                    Toast.makeText(SignUp.this, "Please Enter a Name", Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(textPassword)){
                    Toast.makeText(SignUp.this, "Please Enter a Password", Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(textPhone)){
                    Toast.makeText(SignUp.this, "Please Enter a Number", Toast.LENGTH_LONG).show();
                }else if(textPhone.length()!= 10){
                    Toast.makeText(SignUp.this, "Number must be 10 digits long", Toast.LENGTH_LONG).show();
                }else if(textPassword.length()<6){
                    Toast.makeText(SignUp.this, "Password must contain at least 6 digits", Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(textSurname)){
                    Toast.makeText(SignUp.this, "Please Enter a Surname", Toast.LENGTH_LONG).show();
                }else{

                    registerUser(textEmail,textName,textSurname,textPassword,textPhone);
                }
            }

            private void registerUser(String textEmail, String textName, String textSurname, String textPassword, String textPhone) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Please Wait");
                mDialog.show();
                auth.createUserWithEmailAndPassword(textEmail,textPassword).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            User user = new User(textName, textSurname, textEmail,textPassword, textPhone);
                            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());
                            userRef.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        mDialog.dismiss();
                                        Toast.makeText(SignUp.this, "User Registered", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(SignUp.this, Welcome.class);
                                        startActivity(intent);
                                    } else {
                                        mDialog.dismiss();
                                        Toast.makeText(SignUp.this, "Registration Failed", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }else{
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}