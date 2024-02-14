package com.example.app_tp1swi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_tp1swi.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class sign_up extends AppCompatActivity {
private TextView gotosignIn;
private Button btnSignup;
private EditText name,email,phone,pss,cpass;
private static final String mail_regex="^[A-Za-z0-9+_.-]+@(.+)$";
private String names,emails,phones,psss,cpasss;
private FirebaseAuth firebaseAuth;
private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        gotosignIn=findViewById(R.id.gotosignIn);
        btnSignup=findViewById(R.id.btnup);
        name=findViewById(R.id.textup);
        email=findViewById(R.id.emailup);
        phone=findViewById(R.id.phoneup);
        pss=findViewById(R.id.pswup);
        cpass=findViewById(R.id.confirmup);
        progressDialog=new ProgressDialog(this);
        firebaseAuth=firebaseAuth.getInstance();

        gotosignIn.setOnClickListener(v ->{
            startActivity(new Intent(getApplicationContext(),sign_in.class));
            Toast.makeText(this,"Let's sign in",Toast.LENGTH_LONG).show();
        });
        btnSignup.setOnClickListener(v->{
            if (validate()){
                progressDialog.setMessage("Please wait.....!");
                progressDialog.show();
                firebaseAuth.createUserWithEmailAndPassword(emails,psss).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                       sendEmailverification();
                    }else {
                        Toast.makeText(this,"Failed!",Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();

                    }
                } );
            }
        });

    }

    private void sendEmailverification() {
        FirebaseUser loggeduser= firebaseAuth.getCurrentUser();
        if (loggeduser !=null){
            loggeduser.sendEmailVerification().addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    sendUserData();
                    progressDialog.dismiss();
                    Toast.makeText(this,"Registration Done! Please Check your Email Address",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),sign_in.class));
                    finish();
                }else {
                    Toast.makeText(this,"Registration failed !",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
    }
//ajoute dans la base
    private void sendUserData() {
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference myRef=firebaseDatabase.getReference("Users");
        User user=new User(names,emails,phones,psss);
        myRef.child(""+firebaseAuth.getUid()).setValue(user);
    }

    private boolean validate() {

        boolean res=false;
        names=name.getText().toString().trim();
        emails=email.getText().toString().trim();
        phones=phone.getText().toString().trim();
        psss=pss.getText().toString().trim();
        cpasss=cpass.getText().toString().trim();
        if (names.isEmpty() || names.length()<4){
            name.setError("full name is invalid");
        } else if (!isValidEmail(emails)) {
            email.setError("email is invalid");
        }else if (phones.length() !=8) {
            phone.setError("phone is invalid");
        }else if (psss.length()<=7) {
            pss.setError("password is invalid");
        }else if (!cpasss.equals(psss)) {
            cpass.setError("confirm password is invalid");
        }else {
            res=true;
        }
        return res;

    }

    private boolean isValidEmail(String email){
        Pattern pattern=Pattern.compile(mail_regex);
        Matcher matcher= pattern.matcher(email);
        return matcher.matches();
    }
}