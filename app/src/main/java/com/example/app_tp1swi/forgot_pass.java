package com.example.app_tp1swi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class forgot_pass extends AppCompatActivity {
private Button backtologin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);
        backtologin=findViewById(R.id.backtologin);
        backtologin.setOnClickListener(v->{
            startActivity(new Intent(getApplicationContext(),sign_in.class));
            Toast.makeText(this, "Back sign in ", Toast.LENGTH_SHORT).show();
        });
    }
}