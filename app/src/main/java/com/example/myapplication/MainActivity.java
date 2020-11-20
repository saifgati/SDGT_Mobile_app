package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView e0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e0 = findViewById(R.id.e0);
        int SPLASH = 4000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i0 = new Intent(MainActivity.this ,Main2Activity.class);
                startActivity(i0);
                overridePendingTransition(R.anim.slide_activity_left,R.anim.slide_activity_right);
                finish();
            }
        }, SPLASH);
    }
}
