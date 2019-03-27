package com.example.onur.oyuncubul;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class haberler extends AppCompatActivity {

   ViewPager viewPager;
   CustomSwipeAdapter adapter;
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    private String userID;
    private FirebaseDatabase mFirebasedatabase;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haberler);

        mAuth = FirebaseAuth.getInstance();
        mFirebasedatabase=FirebaseDatabase.getInstance();
        databaseReference=mFirebasedatabase.getReference();

        dl = findViewById(R.id.dl);
        viewPager = findViewById(R.id.view_pager);

        adapter = new CustomSwipeAdapter(this);
        viewPager.setAdapter(adapter);
    }

}
