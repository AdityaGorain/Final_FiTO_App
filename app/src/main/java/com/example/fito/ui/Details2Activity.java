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

import java.util.Calendar;
import java.util.Objects;

public class Details2Activity extends AppCompatActivity {

    private String email;
    private AppCompatButton w_save;
    private AppCompatButton s_save;
    private AppCompatButton we_save;
    private TextView wat;
    private TextView sle;
    private TextView wei;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details2);
        getSupportActionBar().setTitle("Enter Intake");

        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        w_save = findViewById(R.id.button_water);
        s_save = findViewById(R.id.button_sleep);
        we_save = findViewById(R.id.button_weight);
        sle = findViewById(R.id.sleep_d);
        wat = findViewById(R.id.water_d);
        wei = findViewById(R.id.weight_d);
        Calendar calendar = Calendar.getInstance();
        calendar.get(Calendar.YEAR);
        calendar.get(Calendar.MONTH);
        calendar.get(Calendar.DATE);

        CharSequence dateCharSequence = android.text.format.DateFormat.format("dd-MM-yyyy", calendar);
        //System.out.println(dateCharSequence);

        w_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        for(DataSnapshot snapshot : datasnapshot.getChildren()){
                            String ma = snapshot.child("email").getValue(String.class);
                            if(Objects.equals(email, ma)){
                                DatabaseReference r = snapshot.getRef();

                                r.child("Misc").child((String) dateCharSequence).child("Water_Intake").setValue(wat.getText().toString());
                                //String stp = snapshot.child("Steps").child((String) dateCharSequence).getValue(String.class);
                                //a.setText(stp);
                                //Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
                                Toast.makeText(Details2Activity.this,"Data Added",Toast.LENGTH_SHORT).show();
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



        s_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        for(DataSnapshot snapshot : datasnapshot.getChildren()){
                            String ma = snapshot.child("email").getValue(String.class);
                            if(Objects.equals(email, ma)){
                                DatabaseReference r = snapshot.getRef();

                                r.child("Misc").child((String) dateCharSequence).child("Sleep_Duration").setValue(sle.getText().toString());
                                //String stp = snapshot.child("Steps").child((String) dateCharSequence).getValue(String.class);
                                //a.setText(stp);
                                Toast.makeText(Details2Activity.this,"Data Added",Toast.LENGTH_SHORT).show();
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

        we_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        for(DataSnapshot snapshot : datasnapshot.getChildren()){
                            String ma = snapshot.child("email").getValue(String.class);
                            if(Objects.equals(email, ma)){
                                DatabaseReference r = snapshot.getRef();

                                r.child("Misc").child((String) dateCharSequence).child("weight").setValue(wei.getText().toString());
                                //String stp = snapshot.child("Steps").child((String) dateCharSequence).getValue(String.class);
                                //a.setText(stp);
                                //Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
                                Toast.makeText(Details2Activity.this,"Data Added",Toast.LENGTH_SHORT).show();
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
    }
}