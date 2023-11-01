package com.example.nesthub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword,editTextPerson;
    Button buttonlogin;

    FirebaseAuth mAuth;
    FirebaseUser mUser ;
    ProgressBar progressBar;
    TextView textView;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInOptions gso;
    ImageView BtnGoogle ;
    com.google.android.material.button.MaterialButton buttonreg ;

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
        buttonreg = findViewById(R.id.btn_signup);

        buttonreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
//        progressBar = findViewById(R.id.progressBar);

        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());


                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    editTextEmail.setError("Use email like exemple@vwxyz.ex");
                } else if (password.length() < 8) {
                    editTextPassword.setError("Invalid Password enter 8 character or more ");
                }else {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "Login successfully", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(LoginActivity.this, "Ther is no account with thin email", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }


            }
        });

    }






}