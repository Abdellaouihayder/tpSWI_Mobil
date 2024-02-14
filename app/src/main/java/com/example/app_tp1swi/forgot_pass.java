package com.example.app_tp1swi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class forgot_pass extends AppCompatActivity {
private Button backtologin,btnforget;
private EditText email;
private FirebaseAuth firebaseAuth;
private ProgressDialog progressDialog;
private static final String mail_regex="^[A-Za-z0-9+_.-]+@(.+)$";
private String emails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        backtologin=findViewById(R.id.backtologin);
        backtologin.setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(),sign_in.class));
            Toast.makeText(this, "Back sign in ", Toast.LENGTH_SHORT).show();
        });
        btnforget=findViewById(R.id.btnforgot);
        email=findViewById(R.id.emailforgot);
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        btnforget.setOnClickListener(v -> {
            progressDialog.setMessage("Please wait.....!");
            progressDialog.show();
            emails=email.getText().toString().trim();
            if (!isValidEmail(emails)){
                email.setError("Invalid Email");
            }else {
                firebaseAuth.sendPasswordResetEmail(emails).addOnCompleteListener(task -> {
                   if (task.isSuccessful()){
                       Toast.makeText(this, "Passsword reset email sent", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(), sign_in.class));
                   }else {
                       Toast.makeText(this, "Failed !", Toast.LENGTH_SHORT).show();
                   }
                   progressDialog.dismiss();
                });
            }
        });
    }
    private boolean isValidEmail(String email){
        Pattern pattern=Pattern.compile(mail_regex);
        Matcher matcher= pattern.matcher(email);
        return matcher.matches();
    }
}