package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Guide extends AppCompatActivity {
    Button show_mission;
    TextView logout ;
    TextView mission,worker,date,vehicle,company,phone;
    EditText name, company_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        show_mission=findViewById(R.id.b3);
        logout= findViewById(R.id.t2);
        company_id= findViewById(R.id.co);
        mission=findViewById(R.id.te8);
        name=findViewById(R.id.ed3);
        worker=findViewById(R.id.te3);
        date=findViewById(R.id.te6);
        vehicle=findViewById(R.id.te5);
        company=findViewById(R.id.te7);
        phone=findViewById(R.id.te4);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i5 = new Intent(Guide.this, Main2Activity.class);
                startActivity(i5);
                finish();
            }
        });
        show_mission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Company = company_id.getText().toString();
                String Name = name.getText().toString().toUpperCase();
                if (Name.isEmpty()){
                    name.setError("Please enter your name");
                    name.requestFocus();
                }
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child(Company).child("Missions").child(Name);

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                            String mission_ ="M: "+dataSnapshot.child("mission").getValue().toString();
                            mission.setText(mission_);
                            String company_ = "Destination: "+dataSnapshot.child("Destination").getValue().toString();
                            company.setText(company_);
                            String vehicle_ = "Vehicle: "+dataSnapshot.child("vehicle").getValue().toString();
                            vehicle.setText(vehicle_);
                            String date_ = "At: "+dataSnapshot.child("date").getValue().toString();
                            date.setText(date_);
                            String worker_ = dataSnapshot.child("_worker").getValue().toString();
                            worker.setText(worker_);
                            String phone_ ="Phone: "+ dataSnapshot.child("phone").getValue().toString();
                            phone.setText(phone_);


                        }Toast.makeText(Guide.this,"Showing your mission!",Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(Guide.this,"Wrong data!",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
