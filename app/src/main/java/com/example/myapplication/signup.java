package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

public class signup extends AppCompatActivity {
    EditText emailId_s, passsword_s,user_name, service  ,company,country;
    Button btnsignup;
    FirebaseUser mDatabase;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId_s = findViewById(R.id.e3);
        passsword_s = findViewById(R.id.e4);
        user_name = findViewById(R.id.e7);
        country= findViewById(R.id.e9);
        company= findViewById(R.id.e6);
        service= findViewById(R.id.e8);

        btnsignup =findViewById(R.id.button2);
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailId_s.getText().toString();
                String pass = passsword_s.getText().toString();
                String Country = country.getText().toString();
                String Company = company.getText().toString();
                final String User_name = user_name.getText().toString();

                String Service =service.getText().toString();
                if (email.isEmpty()){
                    emailId_s.setError("Please enter email");
                    emailId_s.requestFocus();
                }
                else  if (pass.isEmpty()){
                    passsword_s.setError("Please enter password");
                    passsword_s.requestFocus();
                }
                else if (email.isEmpty() && pass.isEmpty()){
                    Toast.makeText(signup.this,"Fields Are Empty!",Toast.LENGTH_LONG).show();
                }
                else if (!(email.isEmpty() && pass.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (! task.isSuccessful()){
                                FirebaseUser user = mFirebaseAuth.getCurrentUser();

                                Toast.makeText(signup.this,"Sign up unsuccessful, try again!",Toast.LENGTH_LONG).show();
                            }
                            else {
                                Intent i2 = new Intent(signup.this,Home.class);
                                startActivity(i2);
                                finish();

                            }
                        }
                    });
                }
                else {
                    Toast.makeText(signup.this,"Error Ocurred!",Toast.LENGTH_LONG).show();

                }
            }
        });

    }




}
