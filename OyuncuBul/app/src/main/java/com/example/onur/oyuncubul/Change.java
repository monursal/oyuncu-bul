package com.example.onur.oyuncubul;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Change extends AppCompatActivity {
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    EditText kullanici, tel, yas;
    Button buttonSave, geri;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    private String userID;
    private FirebaseDatabase mFirebasedatabase;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        Typeface font=Typeface.createFromAsset(getAssets(),"fonts/Pompadour.ttf");

        mAuth = FirebaseAuth.getInstance();
        mFirebasedatabase = FirebaseDatabase.getInstance();
        databaseReference = mFirebasedatabase.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
            }
        };

        kullanici = (EditText) findViewById(R.id.kullanici);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        yas = findViewById(R.id.yas);
        tel = findViewById(R.id.tel);




        kullanici.setTypeface(font);
        yas.setTypeface(font);
        tel.setTypeface(font);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kullaniciEkle();
                saveUserInformation();
            }
        });
    }

    private void kullaniciEkle() {
        String name = kullanici.getText().toString();
        String age = yas.getText().toString();
        String phone = tel.getText().toString();
        String id = userID;

        if (!TextUtils.isEmpty(age) && !TextUtils.isEmpty(phone)) {

            Users users = new Users(name, age, phone);
            databaseReference.child("Users").child(userID).setValue(users);

            Toast.makeText(getApplicationContext(), "Kaydınız Başarılı", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getApplicationContext(), "Boş değer bırakamayınız.", Toast.LENGTH_LONG).show();
        }

    }

    private void saveUserInformation() {
        String displayname = kullanici.getText().toString();
        if (displayname.isEmpty()) {
            kullanici.setError("İsim giriniz");
            kullanici.requestFocus();
            return;
        }
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayname)
                    .build();
            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Change.this, "Profiliniz Güncellendi", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

}
