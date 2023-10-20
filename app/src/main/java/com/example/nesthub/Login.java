package com.example.nesthub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword,editTextPerson;
    Button buttonlogin;

    FirebaseAuth mAuth;
    FirebaseUser mUser ;
    ProgressBar progressBar;
    TextView textView;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInOptions gso;
    ImageView BtnGoogle ;

    int RC_SIGN_IN = 20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        BtnGoogle = findViewById(R.id.btnGoogleAuth);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        buttonlogin = findViewById(R.id.btn_login);
//        progressBar = findViewById(R.id.progressBar);

        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());


                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    editTextEmail.setError("Invalid Email");
                } else if (password.length() < 8) {
                    editTextPassword.setError("Invalid Password");
                }else {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Login.this, "Login successful", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(Login.this, "Login Field", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }


            }
        });

    }





    public void registerEvent(View view){
        Intent intent = new Intent(Login.this, Register.class);
        startActivity(intent);
        finish();
    }
}