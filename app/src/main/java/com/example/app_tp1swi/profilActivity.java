package com.example.app_tp1swi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profilActivity extends AppCompatActivity {
private EditText name,email,phone;
private Button BtnEdit,BtnLogout;
private FirebaseDatabase firebaseDatabase;
private FirebaseAuth firebaseAuth;
private FirebaseUser loggedUser;
private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        name=findViewById(R.id.nameProfil);
        email=findViewById(R.id.emailprofil);
        phone=findViewById(R.id.phoneProfil);
        BtnEdit=findViewById(R.id.btnEditProfil);
        BtnLogout=findViewById(R.id.btnLogout);
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        loggedUser=firebaseAuth.getCurrentUser();
        reference=firebaseDatabase.getReference().child("Users").child(loggedUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String names=snapshot.child("fullname").getValue().toString();
                String emails=snapshot.child("email").getValue().toString();
                String phones=snapshot.child("phone").getValue().toString();
                name.setText(names);
                email.setText(emails);
                phone.setText(phones);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(profilActivity.this, "Error !", Toast.LENGTH_SHORT).show();
            }
        });
        BtnLogout.setOnClickListener(v->{
            SharedPreferences preferences=getSharedPreferences("checkBox",MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putBoolean("remember",false);
            editor.apply();
            firebaseAuth.signOut();
            startActivity(new Intent(getApplicationContext(), sign_in.class));
            Toast.makeText(this, "Logout Successfully !", Toast.LENGTH_SHORT).show();
            finish();
        });
        BtnEdit.setOnClickListener(v->{
            String nameedit=name.getText().toString().trim();
            String phoneedit=phone.getText().toString().trim();
            reference.child("fullname").setValue(nameedit);
            reference.child("phone").setValue(phoneedit);
            Toast.makeText(this, "Your Data has been Changed successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), profilActivity.class));
        });
    }
}