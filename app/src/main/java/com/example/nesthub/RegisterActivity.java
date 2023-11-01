package com.example.nesthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword,editTextFullname,editTextNumber;
    Button buttonReg;

    FirebaseAuth mAuth;
//    ProgressBar progressBar;
    TextView textView;

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
        textView = findViewById(R.id.loginNow);
//        progressBar = findViewById(R.id.progressBar);

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("UserInfo");




        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                progressBar.setVisibility(View.VISIBLE);

                String email, password,name,number;
                email = editTextEmail.getText().toString();
                password = editTextPassword.getText().toString();
                name = editTextFullname.getText().toString();
                number = editTextNumber.getText().toString();
                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || number.isEmpty()){
                    editTextFullname.setError("Please enter your Full Name");
                    editTextEmail.setError("Please enter your Email");
                    editTextPassword.setError("Please enter your Password");
                    editTextNumber.setError("Please enter your Number");
                } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    editTextEmail.setError("Use email like exemple@vwxyz.ex");
                } else if(name.length() > 20){
                    editTextEmail.setError("Invalid Name");
                } else if (number.length() != 10 ) {
                    editTextNumber.setError("Invalid Number, number should be 10 digits");
                } else if (password.length() < 8) {
                    editTextPassword.setError("Invalid Password");
                }else {
                    mAuth = FirebaseAuth.getInstance();
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            progressBar.setVisibility(View.GONE);
                            if(task.isSuccessful()){

                                UserClass user = new UserClass(name, number);
                                myRef.child(mAuth.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(RegisterActivity.this, "RegisterActivity Successful", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });




                            }else{
                                Toast.makeText(RegisterActivity.this, "RegisterActivity Field", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }



            }

        });




    }

}