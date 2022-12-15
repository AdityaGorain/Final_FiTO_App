package com.example.fito;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.fito.helper.UserHelperClass;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PersonalDetailsActivity extends AppCompatActivity {

    String email;

    private EditText name;
    private EditText age;
    private EditText height;
    private EditText weight;
    private EditText target_calories;

    private AppCompatButton register;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);

        Intent intent = getIntent();
        email = intent.getExtras().getString("uid");

       name = findViewById(R.id.ageInput);
       age = findViewById(R.id.nameInput);
       height = findViewById(R.id.emailInput);
       weight = findViewById(R.id.passwordInput);
       target_calories = findViewById(R.id.contactInput);
       register = findViewById(R.id.button1);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_name = name.getText().toString();
                String txt_age = age.getText().toString();
                String txt_height = height.getText().toString();
                String txt_weight = weight.getText().toString();
                String txt_target_calories = target_calories.getText().toString();
                String txt_email = email;

                if (TextUtils.isEmpty(txt_name) || TextUtils.isEmpty(txt_age) || TextUtils.isEmpty(txt_height) || TextUtils.isEmpty(txt_weight) || TextUtils.isEmpty(txt_target_calories)) {
                    Toast.makeText(PersonalDetailsActivity.this, "Empty!!", Toast.LENGTH_SHORT).show();
                } else {
                    storeNewUsersData(txt_email,txt_name, txt_age, txt_height, txt_weight, txt_target_calories);
                    Toast.makeText(PersonalDetailsActivity.this," Details Acquired",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PersonalDetailsActivity.this, MainActivity.class));
                }
            }
        });



    }

    private void storeNewUsersData(String email, String name, String age, String height, String weight, String target_calories) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        System.out.println(reference);
        UserHelperClass addNewUser = new UserHelperClass(email,name,age,height,weight,target_calories);
        reference.push().setValue(addNewUser);

    }
}















