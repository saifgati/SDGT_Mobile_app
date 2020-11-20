package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main2Activity extends AppCompatActivity {
    EditText emailId, passsword ;
    Button btnLogin;
    TextView tvsignup;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthLis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mFirebaseAuth = FirebaseAuth.getInstance();

        emailId= findViewById(R.id.e1);
        passsword= findViewById(R.id.e2);
        btnLogin=findViewById(R.id.button1);
        tvsignup = findViewById(R.id.t1);
        mAuthLis = new FirebaseAuth.AuthStateListener() {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFireUser = mFirebaseAuth.getCurrentUser();
                if(mFireUser!= null){
                    Toast.makeText(Main2Activity.this,"You are in",Toast.LENGTH_SHORT).show();
                    Intent i4 = new Intent(Main2Activity.this , Home.class);
                    startActivity(i4);
                    finish();

                }
                else if (mFireUser== null){
                    Toast.makeText(Main2Activity.this,"Please sign_up!",Toast.LENGTH_SHORT).show();
                }
            }
        };
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String pass = passsword.getText().toString();
                if (email.isEmpty()){
                    emailId.setError("Please enter email");
                    emailId.requestFocus();
                }
                else  if (pass.isEmpty()){
                    passsword.setError("Please enter password");
                    passsword.requestFocus();
                }
                else if (email.isEmpty() && pass.isEmpty()){
                    Toast.makeText(Main2Activity.this,"Fields Are Empty!",Toast.LENGTH_LONG).show();
                }
                else if (!(email.isEmpty() && pass.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(Main2Activity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(Main2Activity.this,"Login Error",Toast.LENGTH_LONG).show();

                            }
                            else {
                                Intent i4 = new Intent(Main2Activity.this,Home.class);
                                startActivity(i4);
                                finish();
                            }

                        }
                    });

                }

                else {
                    Toast.makeText(Main2Activity.this,"Error Ocurred!",Toast.LENGTH_LONG).show();

                }
            }
        });

        tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3 = new Intent(Main2Activity.this,signup.class);
                startActivity(i3);
                overridePendingTransition(R.anim.slide_activity_left,R.anim.slide_activity_right);

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthLis);
        overridePendingTransition(R.anim.slide_activity_left,R.anim.slide_activity_right);

    }
}
