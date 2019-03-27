package com.example.onur.oyuncubul;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onur.oyuncubul.Listeler.OyuncuList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class teklif extends AppCompatActivity {

    public static final String OYUNCU_AD = "oyunIsım";
    public static final String OYUNCU_ID = "oyunId";
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    EditText yas, saha_ismi;
    Spinner mevki, saat;
    Button teklif_yap;
    TextView kullanici, teklifresim,teklifphone;
    ImageView imageView3;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference, databaseReference2;
    private String userID;
    private FirebaseDatabase mFirebasedatabase;
    private StorageReference firebaseStorage;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    ListView listviewteklif;
    List<Oyuncu> oyuncuList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teklif);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Pompadour.ttf");

        kullanici = findViewById(R.id.kullanici);

        Bundle gelenveri = getIntent().getExtras();
        CharSequence gelenyazi = gelenveri.getCharSequence("anahtar");
        kullanici.setText(gelenyazi);


        mAuth = FirebaseAuth.getInstance();
        mFirebasedatabase = FirebaseDatabase.getInstance();
        databaseReference = mFirebasedatabase.getReference();
        final FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();


        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child("teklifler");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("Users").child(userID);


        teklifphone = findViewById(R.id.teklifphone);
        teklifresim = findViewById(R.id.teklifresim);
        mevki = findViewById(R.id.mevki);
        yas = findViewById(R.id.yas);
        saha_ismi = findViewById(R.id.saha_ismi);
        saat = findViewById(R.id.saat);
        teklif_yap = findViewById(R.id.teklif);
        listviewteklif = findViewById(R.id.listviewteklif);
        oyuncuList = new ArrayList<>();



        yas.setTypeface(font);
        teklif_yap.setTypeface(font);
        saha_ismi.setTypeface(font);



        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot2) {
              String  tresim = String.valueOf(dataSnapshot2.child("downloadUrl").getValue());
              String  phone = String.valueOf(dataSnapshot2.child("userTel").getValue());
              teklifresim.setText(tresim);
              teklifphone.setText(phone);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        teklif_yap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oyuncuekle();
                notification();
            }
        });

        listviewteklif.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Oyuncu oyuncu = oyuncuList.get(position);
                Intent intent = new Intent(getApplicationContext(), view_profile.class);

                intent.putExtra(OYUNCU_ID, oyuncu.getOyunId());
                intent.putExtra(OYUNCU_AD, oyuncu.getOyunIsım());
                startActivity(intent);
            }
        });

        teklifsilme();
    }

    private void teklifsilme() {

        listviewteklif.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                final OyuncuList adapter = new OyuncuList(teklif.this, oyuncuList);
                String itt2 = adapter.getItem(position).oyunId2;
                Boolean esit = userID.equals(itt2);

                if (esit) {

                    AlertDialog.Builder dialog = new AlertDialog.Builder(teklif.this);
                    dialog.setTitle("Seçiminiz");
                    dialog.setNegativeButton("İPTAL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });
                    dialog.setPositiveButton("SİL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String itt = adapter.getItem(position).oyunId;

                            databaseReference.child(itt).removeValue();
                        }
                    });

                    dialog.show();
                } else {

                    Toast.makeText(getApplicationContext(), "Yetkiniz yok", Toast.LENGTH_LONG).show();
                }

                return false;
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, ProfileActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                oyuncuList.clear();
                for (DataSnapshot oyuncuSnapshot : dataSnapshot.getChildren()) {
                    Oyuncu oyuncu = oyuncuSnapshot.getValue(Oyuncu.class);
                    oyuncuList.add(oyuncu);

                }
                OyuncuList adapter = new OyuncuList(teklif.this, oyuncuList);
                listviewteklif.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void oyuncuekle() {
        String name = kullanici.getText().toString();
        String position = mevki.getSelectedItem().toString();
        String age = yas.getText().toString();
        String field_name = saha_ismi.getText().toString().trim();
        String clock = saat.getSelectedItem().toString();


        String tphone = (String) teklifphone.getText();

        String timage = (String) teklifresim.getText();
        if (!TextUtils.isEmpty(age) && !TextUtils.isEmpty(field_name)) {

            String id2 = userID;
            String id = databaseReference.push().getKey();
            Oyuncu oyuncu = new Oyuncu(id, id2, name, position, age, field_name, clock, timage,tphone);

            databaseReference.child(id).setValue(oyuncu);

            Toast.makeText(getApplicationContext(), "Teklifiniz gönderildi", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getApplicationContext(), "Boş değer bırakamayınız.", Toast.LENGTH_LONG).show();
        }

    }

    public void notification() {

        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(R.drawable.app_logo)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.app_logo))
                .setContentTitle(kullanici.getText().toString() + " Teklif Yaptı");


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());
    }
}
