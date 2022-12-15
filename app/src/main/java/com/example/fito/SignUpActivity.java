package com.example.fito;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    String email;
    EditText inputEmail, inputPassword, inputConfirmPassword;
    AppCompatButton btnRegister;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        inputEmail = findViewById(R.id.emailInput);
        //inputName = findViewById(R.id.nameInput);
        inputConfirmPassword = findViewById(R.id.passwordInput2);
        inputPassword = findViewById(R.id.passwordInput);
        btnRegister = findViewById(R.id.signUpNextBtn);
        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performAuth();
            }
        });

    }

    private void performAuth() {
        email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmPassword = inputConfirmPassword.getText().toString();
//        String name = inputName.getText().toString();
//        String phone = phoneNumber.getText().toString();

        if(password.isEmpty() || password.length() < 6){
            inputPassword.setError("Enter proper Password");
        }
        else if(!password.equals(confirmPassword)){
            inputConfirmPassword.setError("Password not matched!!");
        }
//        else if(name.isEmpty()){
//            inputName.setError("Please Enter the full name");
//        }
//        else if(phone.isEmpty()){
//            phoneNumber.setError("Please enter the phone number");
//        }
        else{
            progressDialog.setMessage("Please Wait While Registration...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(SignUpActivity.this, "Registration Successfull", Toast.LENGTH_SHORT).show();
                    }else{
                        progressDialog.dismiss();
                        System.out.println(task.getException());
                        Toast.makeText(SignUpActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(SignUpActivity.this, PersonalDetailsActivity.class);
        intent.putExtra("uid", email);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}