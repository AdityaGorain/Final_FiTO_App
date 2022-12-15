package com.example.fito.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fito.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class DetailsActivity extends AppCompatActivity {
    private String email;
    private AppCompatButton save;

    private TextView weight;
    private TextView water;
    private TextView sleep;
    private TextView steps;
    private TextView cal;
    private TextView calb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setTitle("Set Goal");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        weight = findViewById(R.id.weight_g);
        water = findViewById(R.id.water_g);
        sleep = findViewById(R.id.sleep_g);
        steps = findViewById(R.id.step_g);
        cal = findViewById(R.id.calorie_intake_g);
        calb = findViewById(R.id.calorie_burn_g);



        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        for(DataSnapshot snapshot : datasnapshot.getChildren()){
                            String ma = snapshot.child("email").getValue(String.class);
                            if(Objects.equals(email, ma)){
                                DatabaseReference r = snapshot.getRef();
                                r.child("weight").setValue(weight.getText().toString());
                                r.child("water").setValue( water.getText().toString());
                                r.child("sleep").setValue( sleep.getText().toString());
                                r.child("steps").setValue( steps.getText().toString());
                                r.child("calorie_goal").setValue( cal.getText().toString());
                                r.child("calorie_burn_goal").setValue( calb.getText().toString());

                                //a.setText(stp);
                                Toast.makeText(DetailsActivity.this,"Data Saved",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for(DataSnapshot snapshot : datasnapshot.getChildren()){
                    String ma = snapshot.child("email").getValue(String.class);
                    if(Objects.equals(email, ma)){


                        //r.child("Steps").child((String) dateCharSequence).setValue(putSteps);
//                        weight.setText("Weight "+snapshot.child("weight").getValue(String.class)+" kg");
//                        water.setText("Water "+snapshot.child("water").getValue(String.class)+" L");
//                        sleep.setText("Sleep "+snapshot.child("sleep").getValue(String.class)+" hrs");
//                        steps.setText("Steps "+snapshot.child("steps").getValue(String.class));
//                        cal.setText("Calories "+snapshot.child("calorie_goal").getValue(String.class)+" KCal");
//                        calb.setText("Calories "+snapshot.child("calorie_burn_goal").getValue(String.class)+" KCal");

                        //a.setText(stp);
                        //Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}