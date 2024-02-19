package com.example.app_tp1swi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class sign_in extends AppCompatActivity {
private TextView forgot,signup;
private EditText email,pwd;
private Button btnsignin;
private FirebaseAuth firebaseAuth;
private ProgressDialog progressDialog;
private static final String mail_regex="^[A-Za-z0-9+_.-]+@(.+)$";
private String emails,pwds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        signup=(TextView) findViewById(R.id.gotosignUp);
        forgot=(TextView) findViewById(R.id.gotoforgetpas);
        email=findViewById(R.id.emailSignIn);
        pwd=findViewById(R.id.passSignIn);
        btnsignin=findViewById(R.id.btnSignIn);

        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        signup.setOnClickListener(v ->{
            startActivity(new Intent(getApplicationContext(),sign_up.class));
            Toast.makeText(this,"Let's create account",Toast.LENGTH_LONG).show();
        });
        forgot.setOnClickListener(v ->{
            startActivity(new Intent(getApplicationContext(), forgot_pass.class));
            Toast.makeText(this,"Let's change password",Toast.LENGTH_LONG).show();
        });
        btnsignin.setOnClickListener(v->{
             emails=email.getText().toString().trim();
             pwds=pwd.getText().toString().trim();
             if (! isValidEmail(emails)){
                 email.setError("Email is invalid!");
             } else if (pwds.length()<=7) {
                 pwd.setError("Password is invalid!");
             }else {
                 login(emails,pwds);
             }
        });

    }

    private void login(String emails, String pwds) {
        progressDialog.setMessage("Please wait ...!");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(emails,pwds).addOnCompleteListener(task -> {
         if (task.isSuccessful())  {
            CheckEmailVerification();
         }else {
             Toast.makeText(this,"Sign in Failed",Toast.LENGTH_LONG).show();
             progressDialog.dismiss();
         }
        });
    }

    private void CheckEmailVerification() {
        FirebaseUser loggedUser=firebaseAuth.getCurrentUser();
        if (loggedUser!=null){
            if (loggedUser.isEmailVerified()){
                finish();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                progressDialog.dismiss();
            }else {
                Toast.makeText(this, "Please verify your Email", Toast.LENGTH_SHORT).show();
                firebaseAuth.signOut();
                progressDialog.dismiss();
            }
        }
    }

    private boolean isValidEmail(String email){
        Pattern pattern=Pattern.compile(mail_regex);
        Matcher matcher= pattern.matcher(email);
        return matcher.matches();
    }
}