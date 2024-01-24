package com.example.nesthub.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nesthub.MainActivity;
import com.example.nesthub.R;
import com.example.nesthub.models.UserClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword, editTextFullname, editTextNumber;
    Button buttonReg;

    FirebaseAuth mAuth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextNumber = findViewById(R.id.number);
        editTextFullname = findViewById(R.id.full_name);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        buttonReg = findViewById(R.id.btn_Register);

        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("UserInfo");

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password, name, number;
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();
                name = editTextFullname.getText().toString();
                number = editTextNumber.getText().toString();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || number.isEmpty()) {
                    // Handle empty fields
                    editTextFullname.setError("Please enter your Full Name");
                    editTextEmail.setError("Please enter your Email");
                    editTextPassword.setError("Please enter your Password");
                    editTextNumber.setError("Please enter your Number");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    // Handle invalid email format
                    editTextEmail.setError("Use email like example@xyz.com");
                } else if (name.length() > 20) {
                    // Handle invalid name length
                    editTextFullname.setError("Invalid Name");
                } else if (number.length() != 10) {
                    // Handle invalid number length
                    editTextNumber.setError("Invalid Number, number should be 10 digits");
                } else if (password.length() < 8) {
                    // Handle invalid password length
                    editTextPassword.setError("Invalid Password");
                } else {
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Registration successful
                                UserClass user = new UserClass(name, number, email);
                                myRef.child(mAuth.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(RegisterActivity.this, "Failed to store user information", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            } else {
                                // Registration failed
                                Toast.makeText(RegisterActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
