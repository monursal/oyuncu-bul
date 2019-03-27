package com.example.onur.oyuncubul;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;

    Button giris;
    EditText kayit_eposta, kayit_sifre;
    ProgressBar progressBar;
    TextView kayit,sifreunutma;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        kayit_sifre = (EditText) findViewById(R.id.kayit_sifre);
        kayit_eposta = (EditText) findViewById(R.id.kayit_eposta);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        sifreunutma = findViewById(R.id.sifreunutma);
        giris = (Button) findViewById(R.id.giris);
        kayit = (TextView) findViewById(R.id.kayit);


        sifreunutma.setOnClickListener(this);
        kayit.setOnClickListener(this);
        giris.setOnClickListener(this);


    }

    private void userLogin() {

        String password = kayit_sifre.getText().toString().trim();
        String email = kayit_eposta.getText().toString();


        if (email.isEmpty()) {
            kayit_eposta.setError("E-Posta giriniz.");
            kayit_eposta.requestFocus();
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

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    finish();
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }


            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(this, ProfileActivity.class));
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.kayit:
                finish();

                startActivity(new Intent(this, kayit_ol.class));

                break;

            case R.id.giris:
                userLogin();
                break;

            case R.id.sifreunutma:
                startActivity(new Intent(this, change_password.class));
                break;

        }

    }
}
