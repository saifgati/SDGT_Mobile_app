package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class Home extends AppCompatActivity {
    TextView logout ;
    TextView humidity,temperature,speed_i;
    EditText redirect ;
    Button device_id,mission;
    FirebaseAuth mfirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthList;
    private RequestQueue mQueu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        logout= findViewById(R.id.t2);
        temperature = findViewById(R.id.textView1);
        humidity = findViewById(R.id.textView2);
        speed_i = findViewById(R.id.textView3);
        device_id= findViewById(R.id.bt1);
        redirect= findViewById(R.id.ed1);

        mission= findViewById(R.id.te2);
        mQueu = Volley.newRequestQueue(Home.this);
        mission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i6= new Intent(Home.this,Guide.class);
                startActivity(i6);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i5 = new Intent(Home.this, Main2Activity.class);
                startActivity(i5);
                finish();
            }
        });

        device_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    jsonParse();
                    Toast.makeText(Home.this,"Reading",Toast.LENGTH_LONG).show();

                } finally {
                    Toast.makeText(Home.this,"OK!",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    private void jsonParse(){
        String code= redirect.getText().toString();
        if (code.isEmpty()){
            redirect.setError("Please enter a valid ID");
            redirect.requestFocus();
        }
        String url="https://api.thingspeak.com/channels/"+code+"/feeds.json?results=2";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray= response.getJSONArray("feeds");
                    int i = 0;
                        JSONObject feeds = jsonArray.getJSONObject(i);
                        String temp = feeds.getString("field1");
                        String hum = feeds.getString("field2");
                        String speed = feeds.getString("field5");
                        Float hum1 = Float.parseFloat(hum);
                        DecimalFormat f = new DecimalFormat("##.##");
                        f.format(hum1);
                        Float temp1 = Float.parseFloat(temp);
                        f.format(temp1);
                        Float speed1= Float.parseFloat(speed);
                        f.format(speed1);
                        String hum1_ = "Humidity: "+hum1.toString();
                        String temp_ = "Temperature: "+temp1.toString();
                        String speed_ = "Speed: "+speed1.toString();
                        humidity.setText(hum1_);
                        temperature.setText(temp_);
                        speed_i.setText(speed_);


                } catch (JSONException e) {
                    e.printStackTrace();


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });
        mQueu.add(request);
    }


}
