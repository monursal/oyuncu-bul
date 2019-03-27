package com.example.onur.oyuncubul;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.onur.oyuncubul.Listeler.BilgiList;
import com.example.onur.oyuncubul.Listeler.OyuncuList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class view_profile extends AppCompatActivity {

    private Context context;
    ImageView imageView2;
    TextView isim, deneme, viewtelefon, denemelik, kontrol;
    Button bildirim;
    FirebaseAuth mAuth;
    FirebaseStorage firebaseStorage;
    DatabaseReference databaseReference, databaseReference2, databaseReference3, databaseReference4, databaseReference5;
    private String userID;
    private FirebaseDatabase mFirebasedatabase;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    ListView bilgiListView;
    List<Message> bilgiList;
    List<Oyuncu> oyuncuList;
    List<String> glocations;

    public view_profile() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        glocations = new ArrayList<String>();


        mAuth = FirebaseAuth.getInstance();
        mFirebasedatabase = FirebaseDatabase.getInstance();
        databaseReference = mFirebasedatabase.getReference();
        final FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();


        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userID);
        databaseReference2 = FirebaseDatabase.getInstance().getReference("Users").child("Mesajlar").child(getIntent().getStringExtra(teklif.OYUNCU_ID));
        databaseReference3 = FirebaseDatabase.getInstance().getReference("Users").child("teklifler").child(getIntent().getStringExtra(teklif.OYUNCU_ID));
        databaseReference4 = FirebaseDatabase.getInstance().getReference("Users").child("Mesajlar").child(getIntent().getStringExtra(teklif.OYUNCU_ID));
        databaseReference5 = FirebaseDatabase.getInstance().getReference("Users").child("teklifler").child(getIntent().getStringExtra(teklif.OYUNCU_ID)).child("oyunId2");


        kontrol = findViewById(R.id.kontrol);
        denemelik = findViewById(R.id.denemelik);
        viewtelefon = findViewById(R.id.telefon);
        bilgiListView = findViewById(R.id.bilgiListView);
        imageView2 = findViewById(R.id.imageView2);
        isim = findViewById(R.id.isim);
        deneme = findViewById(R.id.deneme);
        bildirim = findViewById(R.id.button2);

        bilgiList = new ArrayList<>();

        loadUserInformation();

        Intent intent = getIntent();
        final String id = intent.getExtras().getString(teklif.OYUNCU_ID);
        String name = intent.getExtras().getString(teklif.OYUNCU_AD);

        isim.setText(name);


        onaylama();


        databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String childId = String.valueOf(dataSnapshot.child("oyunId2").getValue());
                denemelik.setText(childId);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        bildirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot2) {

                        String itt = String.valueOf(dataSnapshot2.child("oyunId2").getValue());
                        kontrol.setText(itt);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                String itt2 = (String) kontrol.getText();
                Boolean kontrol = itt2.equals(userID);

                if (kontrol) {
                    Toast.makeText(getApplicationContext(), "Kendiniz teklif veremezsiniz", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String childValueI = String.valueOf(dataSnapshot.child("userIsım").getValue());
                            String childValueY = String.valueOf(dataSnapshot.child("userYas").getValue());
                            String childValueT = String.valueOf(dataSnapshot.child("userTel").getValue());

                            String childId = databaseReference2.push().getKey();

                            String childId3 = (String) denemelik.getText();

                            String childId2 = userID;
                            String childValueO = "";
                            Message message = new Message(childId, childId2, childId3, childValueI, childValueY, childValueT, childValueO);

                            databaseReference2.child(childId).setValue(message);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

        });


        isteksilme();
    }




    /*private boolean kontrol(String usermesajId){
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot idSnapshot : dataSnapshot.getChildren())
                    idList.add(String.valueOf(idSnapshot.child("messageId2").getValue()));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        for ( int i=0; i< idList.size(); i++){
            if(idList.get(i).equals(usermesajId)){
                return true;
            }
        }


        return false;
    }*/

    private void mesajekle() {
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bilgiList.clear();

                for (DataSnapshot bilgiSnapshot : dataSnapshot.getChildren()) {
                    Message message = bilgiSnapshot.getValue(Message.class);
                    bilgiList.add(message);
                }
                BilgiList adapter = new BilgiList(view_profile.this, bilgiList);
                bilgiListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void onaylama() {

        bilgiListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, final long id) {


                BilgiList adapter2 = new BilgiList(view_profile.this, bilgiList);
                OyuncuList adapter = new OyuncuList(view_profile.this, oyuncuList);
                String itt3 = adapter2.getItem(position).messageId3;
                Boolean esit2 = userID.equals(itt3);


                if (esit2) {
                    for (int i = 0; i < parent.getChildCount(); i++) {
                        String itt;
                        if (i == position) {

                            adapter2.getItemId(i);
                            itt = adapter2.getItem(i).messageId;
                            deneme.setText(itt);
                            databaseReference4.child(itt).child("messageOnay").setValue("1");

                        } else {
                            itt = adapter2.getItem(i).messageId;

                            databaseReference4.child(itt).child("messageOnay").setValue("0");
                        }
                    }
                } else {

                }
            }
        });

    }

    private void isteksilme() {

        bilgiListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                final BilgiList adapter2 = new BilgiList(view_profile.this, bilgiList);
                String itt2 = adapter2.getItem(position).messageId2;
                Boolean esit = userID.equals(itt2);

                if (esit) {

                    AlertDialog.Builder dialog = new AlertDialog.Builder(view_profile.this);
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

                            String itt = adapter2.getItem(position).messageId;

                            databaseReference2.child(itt).removeValue();
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

    private void loadUserInformation() {
       /* databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String image = String.valueOf(dataSnapshot.child("oyunImage").getValue());
                Glide.with(view_profile.this).load(image).into(imageView2);
                String phone = String.valueOf(dataSnapshot.child("oyunTel").getValue());
                viewtelefon.setText(phone);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        databaseReference3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String image = String.valueOf(dataSnapshot.child("oyunImage").getValue());
                Glide.with(view_profile.this).load(image).into(imageView2);
                String phone = String.valueOf(dataSnapshot.child("oyunTel").getValue());
                viewtelefon.setText(phone);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        mesajekle();

    }
}
