package com.example.app_tp1swi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class sign_up extends AppCompatActivity {
private TextView gotosignIn;
private Button btn;
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
        btn=findViewById(R.id.btnup);
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
        btn.setOnClickListener(v->{
            progressDialog.setMessage("Please wait.....!");
            progressDialog.show();
            if (validate()){
                Toast.makeText(this,"valid!",Toast.LENGTH_LONG).show();
                firebaseAuth.createUserWithEmailAndPassword(emails,psss).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(this,"Done!",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), sign_in.class));
                        progressDialog.dismiss();
                    }else {
                        Toast.makeText(this,"Failed!",Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();

                    }
                } );
            }
        });

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