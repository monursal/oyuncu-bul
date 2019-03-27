package com.example.onur.oyuncubul;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Onur on 15.01.2018.
 */

public class kayit_ol extends AppCompatActivity implements View.OnClickListener {

    ProgressBar progressBar;
    EditText kayit_sifre, kayit_eposta;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);

        kayit_sifre = (EditText) findViewById(R.id.kayit_sifre);
        kayit_eposta = (EditText) findViewById(R.id.kayit_eposta);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);


        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.kayit_buton).setOnClickListener(this);
        findViewById(R.id.giris).setOnClickListener(this);


    }

    private void registerUser() {

        String password = kayit_sifre.getText().toString().trim();
        String email = kayit_eposta.getText().toString();


        if (email.isEmpty()) {
            kayit_eposta.setError("E-Posta giriniz.");
            kayit_eposta.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            kayit_eposta.setError("Lütfen geçerli bir değer giriniz");
            return;
        }

        if (password.isEmpty()) {
            kayit_sifre.setError("Şifre giriniz.");
            kayit_sifre.requestFocus();
            return;
        }
        if (password.length() < 6) {
            kayit_sifre.setError("Şifeniz minimum 6 haneli olmalıdır.");
            kayit_sifre.requestFocus();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);

       mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               progressBar.setVisibility(View.GONE);
               if(task.isSuccessful()){
                   finish();
                   startActivity(new Intent(kayit_ol.this,ProfileActivity.class));
               }else{

                   if(task.getException() instanceof FirebaseAuthUserCollisionException){
                       Toast.makeText(getApplicationContext(), "Zaten kayıtlısınız.", Toast.LENGTH_SHORT).show();

                   }else{
                       Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                   }
               }
           }
       });
}


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.kayit_buton:
                registerUser();
                break;

            case R.id.giris:
                finish();
                startActivity(new Intent(this,MainActivity.class));
                break;
        }
    }
}

