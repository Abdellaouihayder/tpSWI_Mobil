package com.example.app_tp1swi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class sign_in extends AppCompatActivity {
private TextView forgot,signup;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        signup=(TextView) findViewById(R.id.gotosignUp);
        forgot=(TextView) findViewById(R.id.gotoforgetpas);
        signup.setOnClickListener(v ->{
            startActivity(new Intent(getApplicationContext(),sign_up.class));
            Toast.makeText(this,"Let's create account",Toast.LENGTH_LONG).show();
        });
        forgot.setOnClickListener(v ->{
            startActivity(new Intent(getApplicationContext(), forgot_pass.class));
            Toast.makeText(this,"Let's change password",Toast.LENGTH_LONG).show();
        });
    }
}