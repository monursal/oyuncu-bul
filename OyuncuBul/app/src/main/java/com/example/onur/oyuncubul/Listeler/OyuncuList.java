package com.example.onur.oyuncubul.Listeler;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.onur.oyuncubul.Oyuncu;
import com.example.onur.oyuncubul.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Onur on 8.02.2018.
 */

public class OyuncuList extends ArrayAdapter<Oyuncu> {

    ListView listViewOyuncu;
    private Activity context;
    private List<Oyuncu> oyuncuList;

    public OyuncuList(Activity context, List<Oyuncu> oyuncuList) {
        super(context, R.layout.list_layout, oyuncuList);
        this.context = context;
        this.oyuncuList = oyuncuList;

    }




    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();



        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);
        TextView textViewiisim = listViewItem.findViewById(R.id.textViewisim);
        TextView textViewMevki = (TextView) listViewItem.findViewById(R.id.textViewPozisyon);
        TextView textViewYas = (TextView) listViewItem.findViewById(R.id.textViewYas);
        TextView textViewSaha = (TextView) listViewItem.findViewById(R.id.textViewSaha);
        TextView textViewSaat = (TextView) listViewItem.findViewById(R.id.textViewSaat);


        Oyuncu oyuncu = oyuncuList.get(position);


        textViewiisim.setText(oyuncu.getOyunIsım());
        textViewMevki.setText(oyuncu.getOyunMevki());
        textViewYas.setText(oyuncu.getOyunYas());
        textViewSaha.setText(oyuncu.getOyunSaha());
        textViewSaat.setText(oyuncu.getOyunSaat());


        return listViewItem;

    }
}
