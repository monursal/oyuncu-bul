package com.example.onur.oyuncubul;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class saha extends AppCompatActivity {

    ViewPager viewPagersaha;
    SahaSwipeAdapter adapter2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saha);


        viewPagersaha = findViewById(R.id.view_pagersaha);

        adapter2 = new SahaSwipeAdapter(this);
        viewPagersaha.setAdapter(adapter2);
    }
}
